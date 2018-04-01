package com.pln.www.model;

import com.google.firebase.database.Exclude;

/**
 * Created by ACHI on 17/02/2018.
 */

public class DocumentModel {
    String idPekerjaan, namaDokumen, tanggal, status, keterangan, namaFile, uriFile;

    public DocumentModel() {
    }

    public DocumentModel(String idPekerjaan, String namaDokumen, String status, String tanggal, String keterangan, String namaFile, String uriFile) {
        this.idPekerjaan = idPekerjaan;
        this.namaDokumen = namaDokumen;
        this.tanggal = tanggal;
        this.status = status;
        this.keterangan = keterangan;
        this.namaFile = namaFile;
        this.uriFile = uriFile;
    }

    public String getNamaDokumen() {
        return namaDokumen;
    }

    public void setNamaDokumen(String namaDokumen) {
        this.namaDokumen = namaDokumen;
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

    public String getNamaFile() { return namaFile; }

    public void setNamaFile(String namaFile) { this.namaFile = namaFile; }

    public String getUriFile() {
        return uriFile;
    }

    public void setUriFile(String uriFile) {
        this.uriFile = uriFile;
    }
}