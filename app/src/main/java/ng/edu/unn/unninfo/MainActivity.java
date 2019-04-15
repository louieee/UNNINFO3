package ng.edu.unn.unninfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
import ng.edu.unn.unninfo.News_Database.Database_Reader;
import ng.edu.unn.unninfo.News_Database.DeleteTime;
import ng.edu.unn.unninfo.News_Database.Downloader;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        SharedPreferences.OnSharedPreferenceChangeListener,SwipeRefreshLayout.OnRefreshListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private Boolean exit = false;
    RecyclerView rv;
//whenever the code restarts, a background task begins
    @Override
    protected void onRestart() {
        super.onRestart();
        // get news objects from database and render onto recycler view
        new Database_Reader(MainActivity.this,rv).execute();
        // check for new news from the web server
        new Downloader(MainActivity.this, rv).execute();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get main layout
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_group_collapse_00);
        mDrawerLayout = findViewById(R.id.newsrecycler);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        NavigationView navigationView = findViewById(R.id.Nav_view4);
        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        setupSharedPreferences();
        new Database_Reader(MainActivity.this,rv).execute();
        new Downloader(MainActivity.this, rv).execute();
        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
    }




// navigation toggle button behaviour
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }
// what happens when you press back button
    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Press Back Again to Exit", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }

// disconnects from the setting
    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
// what happens when each navigation menu item is clicked
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this,  R.style.MyDialogTheme)
                        .setTitle("Exit")
                        .setMessage("Are you sure you want to exit this App? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(0);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                break;
            case R.id.settings:
                Intent settings = new Intent(getApplication(),SettingsActivity.class);
                startActivity(settings);
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    //background task to delete all news that are old based on specified time in settings
    public void sharedPreferenceTime(SharedPreferences sharedPreferences) {
        new DeleteTime(this).execute((sharedPreferences.getString(getString(R.string.pref_time_key), getString(R.string.pref_time_4m_value))));
    }
    // registers an updated change in settings
    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferenceTime(sharedPreferences);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }
//updates changes in settings
        @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_time_key))){
            sharedPreferenceTime(sharedPreferences);
        }
    }
    //this method calls the background task that downloads new news on refresh
    private void refreshContent(){
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                new Downloader(MainActivity.this, rv).execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        },10000);
    }


    @Override
    public void onRefresh() {
        refreshContent();
    }
}
