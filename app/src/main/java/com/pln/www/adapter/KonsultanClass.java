package com.pln.www.adapter;

/**
 * Created by User on 13/01/2018.
 */

public class KonsultanClass {
    String namaKonsultan, idKonsultan;

    public KonsultanClass(String idKonsultan, String namaKonsultan){
        this.idKonsultan = idKonsultan;
        this.namaKonsultan = namaKonsultan;
    }

    public void setNamaKonsultan(String namaKonsultan) {
        this.namaKonsultan = namaKonsultan;
    }

    public String getNamaKonsultan() {
        return namaKonsultan;
    }

    public String getIdKonsultan() {
        return idKonsultan;
    }

    public void setIdKonsultan(String idKonsultan) {
        this.idKonsultan = idKonsultan;
    }
}
