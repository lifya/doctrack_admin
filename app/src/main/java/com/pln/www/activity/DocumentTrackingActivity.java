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

public class DocumentTrackingActivity extends AppCompatActivity implements View.OnClickListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ImageView image1;
    private ImageView addDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_tracking);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_document_tracking, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

        if (v == addDoc){
            AddDocumentActivity addDocActivity = new AddDocumentActivity();
            sentoAdd();
        }
    }

    @Override
    public void onBackPressed() {
        DocumentTrackingActivity doc = new DocumentTrackingActivity();
        sentoHome();
    }

    public void sentoAdd() {
        Intent startIntent = new Intent(DocumentTrackingActivity.this, AddDocumentActivity.class);
        startActivity(startIntent);
        finish();
    }

    public void sentoHome() {
        Intent startIntent = new Intent(DocumentTrackingActivity.this, MainActivity.class);
        startActivity(startIntent);
        finish();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0 :
                    AmdalFragment amdalFragment = new AmdalFragment();
                    return amdalFragment;
                case 1 :
                    AmdalFragment uklFragment = new AmdalFragment();
                    return uklFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }
}
