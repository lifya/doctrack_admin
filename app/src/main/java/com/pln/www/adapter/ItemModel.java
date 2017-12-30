package com.pln.www.adapter;

import java.util.Date;

/**
 * Created by ACHI on 01/09/2017.
 */

public class ItemModel {
    private String mJudul, mKonsultan, mTanggal, mWaktu;
    private int mStatus;

    public ItemModel(String mJudul, String mKonsultan, String mTanggal, String mWaktu, int mStatus) {
        this.mJudul = mJudul;
        this.mKonsultan = mKonsultan;
        this.mTanggal = mTanggal;
        this.mWaktu = mWaktu;
        this.mStatus = mStatus;
    }

    public String getmJudul() {
        return mJudul;
    }

    public void setmJudul(String mName) {
        this.mJudul = mJudul;
    }

    public String getmKonsultan() {
        return mKonsultan;
    }

    public void setmKonsultan(String mKonsultan) { this.mKonsultan = mKonsultan; }

    public String getmTanggal() {
        return mTanggal;
    }

    public void setmTanggal(String mTanggal) {
        this.mTanggal = mTanggal;
    }

    public String getmWaktu() {
        return mWaktu;
    }

    public void setmWaktu(String mWakty) {
        this.mWaktu = mWaktu;
    }

    public int getmStatus() {
        return mStatus;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }
}
