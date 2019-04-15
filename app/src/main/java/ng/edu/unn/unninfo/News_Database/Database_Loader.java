package ng.edu.unn.unninfo.News_Database;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import java.util.ArrayList;

import ng.edu.unn.unninfo.News_Object.News_Object;

public class Database_Loader extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView rv;
    private ArrayList<News_Object> News;

    Database_Loader(Context c, ArrayList<News_Object> news, RecyclerView rv) {
        this.c = c;
        this.News = news;
        this.rv = rv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.LoadData();
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        if (result == 0) {

        } else {
            Database_Reader reader = new Database_Reader(c, rv);
            reader.execute();
        }
    }

    private int LoadData() {
            DataBaseHelper database = new DataBaseHelper(c);
            News_Object N;
            int count1;
            for (int i = 0; i < News.size(); i++) {
                N = News.get(i);
                String empty = "";
                String post = "POST";
                String postURL = N.getPostURL();
                String PostID = N.getpostID();
                String Title = N.getTitle();
                String body = N.getBody();
                String date = N.getDate();
                if (!database.inDatabase(PostID)) {
                    database.insertData(empty, Title, PostID, postURL, post, body, empty, date);
                }
            }
            count1 = database.tableExist();
            return count1;
    }
}
