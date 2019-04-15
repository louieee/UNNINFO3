package ng.edu.unn.unninfo;

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loads the settings layout
        setContentView(R.layout.activity_settings);
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

        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

// navigation button behaviour
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }

// what happens when each navigation menu item is clicked
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.feed : Intent feed = new Intent (getApplicationContext(),MainActivity.class);
                startActivity(feed);finish();break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}