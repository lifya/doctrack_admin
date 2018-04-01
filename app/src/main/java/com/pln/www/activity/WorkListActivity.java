package com.pln.www.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import android.widget.ImageView;

import com.pln.www.R;
import com.pln.www.fragment.AmdalFragment;
import com.pln.www.fragment.UklUplFragment;

public class WorkListActivity extends AppCompatActivity implements View.OnClickListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ImageView image1;
    private ImageView addDoc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        addDoc = (ImageView) findViewById(R.id.addDoc);
        addDoc.setOnClickListener(this);


        image1 = (ImageView) findViewById(R.id.back);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_document_tracking, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

        if (v == addDoc){
            sentoAdd();
        }
    }

    @Override
    public void onBackPressed() {
        sentoHome();
    }

    public void sentoAdd() {
        Intent startIntent = new Intent(WorkListActivity.this, AddWorkActivity.class);
        startActivity(startIntent);
        finish();
    }

    public void sentoHome() {
        Intent startIntent = new Intent(WorkListActivity.this, MainActivity.class);
        startActivity(startIntent);
        finish();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0 :
                    AmdalFragment amdalFragment = new AmdalFragment();
                    return amdalFragment;
                case 1 :
                    UklUplFragment uklFragment = new UklUplFragment();
                    return uklFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
