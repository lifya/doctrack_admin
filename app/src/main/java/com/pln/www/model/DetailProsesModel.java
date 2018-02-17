package com.pln.www.model;

/**
 * Created by ACHI on 17/02/2018.
 */

public class DetailProsesModel {
    String idDetailProses, idProses, idPekerjaan, tanggal, file, status, keterangan;

    public DetailProsesModel(String idDetailProses, String idProses, String idPekerjaan, String tanggal, String file, String status, String keterangan) {
        this.idDetailProses = idDetailProses;
        this.idProses = idProses;
        this.idPekerjaan = idPekerjaan;
        this.tanggal = tanggal;
        this.file = file;
        this.status = status;
        this.keterangan = keterangan;
    }

    public String getIdDetailProses() {
        return idDetailProses;
    }

    public void setIdDetailProses(String idDetailProses) {
        this.idDetailProses = idDetailProses;
    }

    public String getIdProses() {
        return idProses;
    }

    public void setIdProses(String idProses) {
        this.idProses = idProses;
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
