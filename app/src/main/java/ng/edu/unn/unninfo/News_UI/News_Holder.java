package ng.edu.unn.unninfo.News_UI;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import ng.edu.unn.unninfo.MainActivity;
import ng.edu.unn.unninfo.News_Object.Database_List;
import ng.edu.unn.unninfo.R;
import ng.edu.unn.unninfo.ViewNews;

public class News_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView title,time;
    ImageView img;
    private ArrayList<Database_List> NewsList;

    News_Holder(View itemView,ArrayList<Database_List> list){
        super(itemView);
        itemView.setOnClickListener(this);
        title = itemView.findViewById(R.id.News_Title);
        img = itemView.findViewById(R.id.News_Image);
        time = itemView.findViewById(R.id.News_Time);
        this.NewsList = list;
    }
    @Override
    public void onClick(View v) {
        int index = getAdapterPosition();
        Database_List e = NewsList.get(index);
        String myTitle = e.getTitle();
        Intent DisplayNews = new Intent(v.getContext(),ViewNews.class);
        String date = e.getDate();
        String time = e.getTime();
        String body = e.getBody();
        String postURL = e.getPostUrl();
        String[] attachment = e.getAttachment();
        String []image = e.getImg();
        DisplayNews.putExtra("time",time);
        DisplayNews.putExtra("image",image);
        DisplayNews.putExtra("title",myTitle);
        DisplayNews.putExtra("postURL",postURL);
        DisplayNews.putExtra("body",body);
        DisplayNews.putExtra("date",date);
        DisplayNews.putExtra("attachment",attachment);
        v.getContext().startActivity(DisplayNews);
    }
}