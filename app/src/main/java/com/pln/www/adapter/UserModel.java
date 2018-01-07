package com.pln.www.adapter;

import android.widget.ArrayAdapter;

/**
 * Created by ACHI on 04/01/2018.
 */

public class UserModel {

    private String Nama, Email;

    public UserModel(){}

    public UserModel(String nama, String email) {
        Nama = nama;
        Email = email;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
