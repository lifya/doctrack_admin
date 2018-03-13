package com.pln.www.model;

/**
 * Created by User on 12/03/2018.
 */

public class DetailProses {
    private String idDetailProses, statusProses, tanggal, file, keterangan;

    public DetailProses(){}

    public DetailProses(String idDetailProses, String statusProses, String tanggal, String keterangan){
        this.idDetailProses = idDetailProses;
        this.statusProses = statusProses;
        this.tanggal = tanggal;
        this.keterangan = keterangan;
        //this.file = file;
    }

    public String getIdDeatailProses() {
        return idDetailProses;
    }

    public void setIdDeatailProses(String idDetailProses) {
        this.idDetailProses = idDetailProses;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatusProses() {
        return statusProses;
    }

    public void setStatusProses(String statusProses) {
        this.statusProses = statusProses;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
