package com.pln.www.model;

/**
 * Created by ACHI on 17/02/2018.
 */

public class ProsesModel {
    String idProses, namaProses;

    public ProsesModel(String idProses, String namaProses) {
        this.idProses = idProses;
        this.namaProses = namaProses;
    }

    public String getIdProses() {
        return idProses;
    }

    public void setIdProses(String idProses) {
        this.idProses = idProses;
    }

    public String getNamaProses() {
        return namaProses;
    }

    public void setNamaProses(String namaProses) {
        this.namaProses = namaProses;
    }
}
