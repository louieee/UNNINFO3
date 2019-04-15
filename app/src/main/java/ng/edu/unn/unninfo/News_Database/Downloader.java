package ng.edu.unn.unninfo.News_Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Downloader extends AsyncTask<Void,Void,String> {
    @SuppressLint("StaticFieldLeak")
    private Context c; @SuppressLint("StaticFieldLeak")
    private RecyclerView rv;

    public Downloader(Context c, RecyclerView rv){
        this.c = c;
        this.rv = rv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.downloadData();
    }

    @Override
    protected void onPostExecute(String JsonData) {
        super.onPostExecute(JsonData);
        if (JsonData == null){
            new Database_Reader(c, rv).execute();
            Toast.makeText(c,"Check your Data Connection",Toast.LENGTH_SHORT).show();
        }else{
            //PARSER
            Data_Parser dataParser = new Data_Parser(c,JsonData,rv);
            dataParser.execute();
        }
    }
    private String downloadData(){
        DataBaseHelper database = new DataBaseHelper(c);
        int max = database.maxPostID();
        String urlAddress =  "https://igbonekwukelechi.com/wordpress/api.php?APIKEY=kc&last_post="+max;
        HttpURLConnection conn = Connector.connect(urlAddress);
        if (conn == null){
            return null;
        }
        try{
            InputStream is = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            StringBuilder jsonData = new StringBuilder();

            while ((line = br.readLine())!= null){
                jsonData.append(line).append("\n");

            }
            br.close();
            is.close();
            return jsonData.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
