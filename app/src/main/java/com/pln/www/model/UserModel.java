package com.pln.www.model;

import android.widget.ArrayAdapter;

/**
 * Created by ACHI on 04/01/2018.
 */

public class UserModel {

    private String user_id, nama, email;

    public UserModel(){}

    public UserModel(String user_id, String nama, String email) {
        this.user_id = user_id;
        this.nama = nama;
        this.email= email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
