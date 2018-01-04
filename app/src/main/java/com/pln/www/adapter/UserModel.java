package com.pln.www.adapter;

/**
 * Created by ACHI on 04/01/2018.
 */

public class UserModel {

    private String mNama, mEmail, mPassword;

    public UserModel(String nNama, String nEmail) {
        this.mNama = nNama;
        this.mEmail = nEmail;
    }

    public String getmNama() {return mNama;}

    public void setmNama(String mNama) {this.mNama = mNama;}

    public String getmEmail() {return mEmail;}

    public void setmEmail(String nEMail) {this.mEmail = mEmail;}

    public String getmPassword() {return mPassword;}

    public void setmPassword(String nEMail) {this.mPassword = mPassword;}
}
