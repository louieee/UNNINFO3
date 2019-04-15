package ng.edu.unn.unninfo.News_UI;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import ng.edu.unn.unninfo.News_Object.Database_List;
import ng.edu.unn.unninfo.R;

public class CustomAdapter extends RecyclerView.Adapter<News_Holder> {
    private ArrayList<Database_List> newsObjects;
    private Context C ;

    public CustomAdapter(ArrayList<Database_List> news_objects,Context c){
        this.newsObjects = news_objects;
        this.C = c;
    }

    @NonNull
    @Override
    public News_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model,parent,false);
        return new News_Holder(v,newsObjects);
    }

    @Override
    public void onBindViewHolder(@NonNull News_Holder holder, int position) {
        Database_List news_object = newsObjects.get(position);
        holder.title.setText(news_object.getTitle());
        if (news_object.getImg().length > 0) {
            PicassoClient.downloadImage(C,news_object.getImg()[0],holder.img);
        }
        holder.time.setText(news_object.getTime());
    }



    @Override
    public int getItemCount() {
        return newsObjects.size();
    }
}
