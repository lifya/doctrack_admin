package com.pln.www.model;

/**
 * Created by User on 13/01/2018.
 */

public class PekerjaanModel {
    String idPekerjaan, idKontrak, namaJalur, tegangan, kms, provinsi, jenisPekerjaan, file;

    public PekerjaanModel(){}

    public PekerjaanModel(String idPekerjaan, String idKontrak, String namaJalur, String tegangan, String kms, String provinsi, String jenisPekerjaan){
        this.idPekerjaan = idPekerjaan;
        this.idKontrak = idKontrak;
        this.namaJalur = namaJalur;
        this.tegangan = tegangan;
        this.kms = kms;
        this.provinsi = provinsi;
        this.jenisPekerjaan = jenisPekerjaan;
    }

    public String getIdPekerjaan() {
        return idPekerjaan;
    }

    public void setIdPekerjaan(String idPekerjaan) {
        this.idPekerjaan = idPekerjaan;
    }

    public String getNamaJalur() {
        return namaJalur;
    }

    public void setNamaJalur(String namaJalur) {
        this.namaJalur = namaJalur;
    }

    public String getTegangan() { return tegangan; }

    public void setTegangan(String tegangan) { this.tegangan = tegangan; }

    public String getKms() { return kms; }

    public void setKms(String kms) { this.kms = kms; }

    public String getProvinsi() { return provinsi; }

    public void setProvinsi(String provinsi) { this.provinsi = provinsi; }

    public String getJenisPekerjaan() { return jenisPekerjaan; }

    public void setJenisPekerjaan(String jenisPekerjaan) { this.jenisPekerjaan = jenisPekerjaan; }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setIdKontrak (String idKontrak){ this.idKontrak = idKontrak; }

    public String getIdKontrak (){ return idKontrak; }
}
