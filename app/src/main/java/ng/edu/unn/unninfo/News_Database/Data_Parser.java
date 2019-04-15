package ng.edu.unn.unninfo.News_Database;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

import ng.edu.unn.unninfo.MainActivity;
import ng.edu.unn.unninfo.News_Object.News_Object;
import ng.edu.unn.unninfo.R;

import static java.net.URLDecoder.*;

public class Data_Parser extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c; private String JsonData; @SuppressLint("StaticFieldLeak")
    private RecyclerView rv;
     private ArrayList<News_Object>News = new ArrayList<>();

    Data_Parser(Context c, String JsonData, RecyclerView rv){
        this.c = c;
        this.JsonData = JsonData;
        this.rv = rv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Void... voids) {
            return this.parseData();
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if (result == 0){
            Toast.makeText(c,"No new post",Toast.LENGTH_SHORT).show();
            new Database_Reader(c,rv).execute();
        }else{
            //BIND DATA TO RECYCLER VIEW
            Database_Loader DI = new Database_Loader(c,News,rv);
            show_my_notification(c);
            DI.execute();
        }
    }
    private void show_my_notification(Context c){
        Intent resultIntent = new Intent(c, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(c);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(c, "my_channel_id");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("UNN Info");
        builder.setContentTitle("You have new posts to read");
        builder.setContentIntent(resultPendingIntent);
        builder.setSmallIcon(R.drawable.ic_speaker_notes_green_900_18dp);
        builder.setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);
        notificationManager.notify(0, builder.build());
    }

    private int parseData (){
        DataBaseHelper database = new DataBaseHelper(c);
        String image = "IMAGE";
        String text = "TEXT";
        JSONArray jh;
        try{
            JSONObject j = new JSONObject(JsonData);
            jh = j.getJSONArray("content");
            JSONObject jo;
            News.clear();
            News_Object news_object;

            String empty = "";
            for (int i = 0; i<jh.length(); i++){

                news_object = new News_Object();
                jo = jh.getJSONObject(i);
                String postID = jo.getString("postID");
                String title = decode(jo.getString("title"), "UTF-8");
                String PostURL = decode(jo.getString("url"), "UTF-8");
                String body = decode(jo.getString("body"), "UTF-8");
                String date = decode(jo.getString("date"), "UTF-8");
                if (jo.has("attachment")) {
                    JSONArray att = jo.getJSONArray("attachment");
                    String[] b = new String[att.length()];
                    for (int k=0;k<att.length();k++){
                        String p = decode(att.getString(k), "UTF-8");
                        b[k] = p;
                    }
                    database.deleteAttach(postID);
                    for (String n:b){
                        if (is_img(n)) {
                            database.insertData(n, empty, postID, empty, image, empty, empty, empty);
                        } else if (is_doc(n)){
                            database.insertData(empty, empty, postID, empty, text, empty, n, empty);
                        }
                    }
                }
                news_object.setpostID(postID);
                news_object.setTitle(title);
                news_object.setPostURL(PostURL);
                news_object.setBody(body);
                news_object.setDate(date);
                News.add(news_object);
            }
            return jh.length();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private boolean is_doc(String file){
        String[] doc_ext = {"doc","htm","odt","pdf","xls","ods","ppt","html","xlsx","txt","pptx","docx"};
        String type = file.substring(file.length()-3,file.length());
        String type2 = file.substring(file.length()-4,file.length());
        for (String aDoc_ext : doc_ext) {
            if ((type.equalsIgnoreCase(aDoc_ext)) || (type2.equalsIgnoreCase(aDoc_ext))) {
                return true;
            }
        }
        return false;
    }
    private boolean is_img(String file){
        String[] img_ext = {"jpg","tif","tiff","jpeg","png","gif"};
        String type = file.substring(file.length()-3,file.length());
        String type2 = file.substring(file.length()-4,file.length());
        for (String aDoc_ext : img_ext) {
            if ((type.equalsIgnoreCase(aDoc_ext)) || (type2.equalsIgnoreCase(aDoc_ext))) {
                return true;
            }
        }
        return false;
    }
}
