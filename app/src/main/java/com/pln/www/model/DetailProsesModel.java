package com.pln.www.model;

import com.google.firebase.database.Exclude;

/**
 * Created by ACHI on 17/02/2018.
 */

public class DetailProsesModel {
    String idPekerjaan, namaProses, tanggal, status, keterangan;
    @Exclude
    String file;

    public DetailProsesModel() {
    }

    public DetailProsesModel(String idPekerjaan, String namaProses, String status, String tanggal, String keterangan) {
        this.idPekerjaan = idPekerjaan;
        this.namaProses = namaProses;
        this.tanggal = tanggal;
        //this.file = file;
        this.status = status;
        this.keterangan = keterangan;
    }

    public String getNamaProses() {
        return namaProses;
    }

    public void setNamaProses(String namaProses) {
        this.namaProses = namaProses;
    }

    public String getIdPekerjaan() {
        return idPekerjaan;
    }

    public void setIdPekerjaan(String idPekerjaan) {
        this.idPekerjaan = idPekerjaan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}