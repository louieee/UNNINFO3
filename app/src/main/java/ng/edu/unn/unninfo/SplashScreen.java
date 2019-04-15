package ng.edu.unn.unninfo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loads splashscreem activity layout
        setContentView(R.layout.activity_splash_screen);
        ImageView appIcon = findViewById(R.id.iconDisplay);
        TextView appName = findViewById(R.id.iconName);
        // animate icon for 7 seconds
        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        appIcon.startAnimation(fadeIn);
        appName.startAnimation(fadeIn);
        int splashTimeOut = 7000;
        // open main activity
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Toast.makeText(getApplicationContext(),"Welcome to UNN Info",Toast.LENGTH_SHORT).show();
                Intent start = new Intent (SplashScreen.this,MainActivity.class);
                startActivity(start);
                finish();
            }
        }, splashTimeOut);
    }



}
