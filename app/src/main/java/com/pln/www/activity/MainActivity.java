package com.pln.www.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.pln.www.R;
import com.pln.www.ViewPageAdapter;
import com.pln.www.adapter.SectionsPagerAdapter;
import com.pln.www.fragment.AboutFragment;
import com.pln.www.fragment.ArchiveFragment;
import com.pln.www.fragment.UserFragment;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AboutFragment.OnFragmentInteractionListener, ViewPager.OnClickListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager viewPager;
    ImageView imagev1;
    ImageView imagev2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagev1 = (ImageView) findViewById(R.id.imagev1);
        imagev1.setOnClickListener(this);

        imagev2 = (ImageView) findViewById(R.id.materi);
        imagev2.setOnClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.contain);

        ViewPageAdapter viewPagerAdapter = new ViewPageAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            MainActivity main = new MainActivity();
            sentoBeranda();
        }
        else if (id ==  R.id.nav_archive) {
            ArchiveFragment uFrag = new ArchiveFragment();
            setFragment(uFrag);
        }
        else if (id == R.id.nav_about) {
            UserFragment aFrag = new UserFragment();
            setFragment(aFrag);
        }
        else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            sendtoStart();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void sendtoStart(){
        Intent startIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

    public void sentoBeranda() {
        Intent startIntent = new Intent(MainActivity.this , MainActivity.class);
        startActivity(startIntent);
        finish();
    }

    public void  sentoStartDoc() {
        Intent startIntent = new Intent(MainActivity.this, DocumentTrackingActivity.class);
        startActivity(startIntent);
        finish();
    }

    public void setFragment(android.support.v4.app.Fragment frag) {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.main_content, frag);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {
        if(v == imagev1){
            DocumentTrackingActivity home = new DocumentTrackingActivity();
            sentoStartDoc();
        }
        else if (v == imagev2) {
            UserFragment uFrag = new UserFragment();
            setFragment(uFrag);
        }
    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    }
                    else if (viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    }
                    else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}
