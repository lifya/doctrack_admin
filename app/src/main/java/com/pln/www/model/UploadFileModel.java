package com.pln.www.model;

/**
 * Created by User on 21/01/2018.
 */

public class UploadFileModel {
//    public String name;
    public String url;
    public String id_file;
    public String name;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)


    public UploadFileModel(String id_file, String name, String url) {
        this.id_file = id_file;
        this.name = name;
        this.url = url;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId_file() {
        return id_file;
    }

    public void setId_file(String id_file) {
        this.id_file = id_file;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
