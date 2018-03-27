package com.pln.www.model;

import com.google.firebase.database.Exclude;

/**
 * Created by ACHI on 17/02/2018.
 */

public class DetailProsesModel {
    String idPekerjaan, namaProses, tanggal, status, keterangan, idFile, namaFile;

    public DetailProsesModel() {
    }

    public DetailProsesModel(String idPekerjaan, String namaProses, String status, String tanggal, String keterangan, String idFile, String namaFile) {
        this.idPekerjaan = idPekerjaan;
        this.namaProses = namaProses;
        this.tanggal = tanggal;
        this.status = status;
        this.keterangan = keterangan;
        this.idFile = idFile;
        this.namaFile = namaFile;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) { this.status = status; }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getIdFile() { return idFile; }

    public void setIdFile(String idFile) { this.idFile = idFile; }

    public String getNamaFile() { return namaFile; }

    public void setNamaFile(String namaFile) { this.namaFile = namaFile; }
}