package ng.edu.unn.unninfo;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ng.edu.unn.unninfo.News_UI.PicassoClient;

import static android.view.View.VISIBLE;


public class ViewNews extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private LinearLayout lin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.mr_group_expand);
        mDrawerLayout = findViewById(R.id.ViewNews);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        NavigationView navigationView = findViewById(R.id.Nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageView display = findViewById(R.id.display_Image);
        lin = findViewById(R.id.attach);
        TextView attach1 = findViewById(R.id.attach1);
        TextView attach2 = findViewById(R.id.attach2);
        TextView attach3 = findViewById(R.id.attach3);
        TextView attach4 = findViewById(R.id.attach4);
        TextView attach5 = findViewById(R.id.attach5);
        TextView attach6 = findViewById(R.id.attach6);
        TextView attach7 = findViewById(R.id.attach7);
        TextView attach8 = findViewById(R.id.attach8);
        TextView attach9 = findViewById(R.id.attach9);
        TextView attach10 = findViewById(R.id.attach10);
        TextView attach11 = findViewById(R.id.attach11);
        TextView attach12 = findViewById(R.id.attach12);
        TextView attach13 = findViewById(R.id.attach13);
        TextView attach14 = findViewById(R.id.attach14);

        TextView TitleView = findViewById(R.id.display_title);
        WebView bodyView = findViewById(R.id.body);
        TextView timeView = findViewById(R.id.News_TimeS);
        TextView dateView = findViewById(R.id.date);
        TextView posturlView = findViewById(R.id.postURL);
        String myTitle = getIntent().getExtras().getString("title");
        String POSTurl = getIntent().getExtras().getString("postURL");
        String body = getIntent().getExtras().getString("body");
        String time = getIntent().getExtras().getString("time");
        String date = getIntent().getExtras().getString("date");
        String [] attachment = getIntent().getExtras().getStringArray("attachment");
        String [] images = getIntent().getExtras().getStringArray("image");
        TextView [] array = {attach1,attach2,attach3,attach4,attach5,attach6,attach7};
        TextView [] image = {attach8,attach9,attach10,attach11,attach12,attach13,attach14};
        if (attachment != null && attachment.length>0){
            lin.setVisibility(VISIBLE);
            for (int i=0;i<attachment.length;i++){
                array[i].setVisibility(VISIBLE);
                array[i].setText(attachment[i]);
            }
        }
        if (images != null && images.length>0 && body.contains("<")){
            lin.setVisibility(VISIBLE);
            for (int i=0;i<images.length;i++){
                image[i].setVisibility(VISIBLE);
                image[i].setText(images[i]);
            }

        }else if (images != null && images.length>0 && !body.contains("<")){
            display.setVisibility(VISIBLE);
            PicassoClient.downloadImage(this,images[0],display);
        }else{
            display.setVisibility(VISIBLE);
            display.setImageResource(R.drawable.placeholder1);
        }
        timeView.setText(time);
        if (body.contains("<")){
            bodyView.loadData(body, "text/html", "UTF-8");
        }else{
            bodyView.loadData(body, "text/html", "UTF-8");
            TitleView.setVisibility(VISIBLE);
            TitleView.setText(myTitle);
        }
        dateView.setText(date);
        posturlView.setText(POSTurl);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.feed : Intent feed = new Intent (getApplicationContext(),MainActivity.class);
                startActivity(feed);finish();break;
            case R.id.settings:
                Intent settings = new Intent(getApplication(),SettingsActivity.class);
                startActivity(settings);
                finish();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openWebPage(View view){
           TextView t = view.findViewById(view.getId());
           String url = t.getText().toString();
           Uri webpage = Uri.parse(url);
           Intent intent = new Intent(Intent.ACTION_VIEW,webpage);
           if (intent.resolveActivity(getPackageManager()) != null ){
               startActivity(intent);
           }
    }
}
