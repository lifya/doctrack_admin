package com.pln.www;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.pln.www.fragment.ChatFragment;


/**
 * Created by ACHI on 27/08/2017.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private String[] titles ={"A","B"};
    public int[] icon = {R.mipmap.ic_launcher,R.drawable.ic_chat_black_24dp};
    private int heightIcon;

    public SectionsPagerAdapter(FragmentManager fm, Context c){
        super(fm);
        mContext = c;
        double scale = c.getResources().getDisplayMetrics().density;
        heightIcon=(int)(24*scale+0.5f);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ChatFragment fragment0=new ChatFragment();
                return fragment0;
            case 1:
                ChatFragment fragment1=new ChatFragment();
                return fragment1;
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Drawable d = mContext.getResources().getDrawable(icon[position]);
        d.setBounds(0,0,heightIcon,heightIcon);
        ImageSpan is = new ImageSpan(d);
        if (is == null){
            System.out.println("tidak ada");
        } else {
            System.out.println("ada");
        }

        SpannableString sp = new SpannableString(" ");
        sp.setSpan(is,0,sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        System.out.println(position);
        return sp;

    }


}