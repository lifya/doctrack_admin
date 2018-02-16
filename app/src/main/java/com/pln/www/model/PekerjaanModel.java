package com.pln.www.model;

/**
 * Created by User on 13/01/2018.
 */

public class PekerjaanModel {
    String idPekerjaan, idKontrak, idKonsultan, namaPekerjaan, tegangan, kms, provinsi, jenisPekerjaan, file;

    public PekerjaanModel(){}

    public PekerjaanModel(String idPekerjaan, String idKonsultan, String idKontrak, String namaPekerjaan, String tegangan, String kms, String provinsi, String jenisPekerjaan){
        this.idPekerjaan = idPekerjaan;
        this.idKonsultan = idKonsultan;
        this.idKontrak = idKontrak;
        this.namaPekerjaan = namaPekerjaan;
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

    public String getNamaPekerjaan() {
        return namaPekerjaan;
    }

    public void setNamaPekerjaan(String namaPekerjaan) {
        this.namaPekerjaan = namaPekerjaan;
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

    public void setIdKontrak (String idKontrak){
        this.idKontrak = idKontrak;
    }

    public String getIdKontrak (){
        return idKontrak;
    }

    public void setIdKonsultan (String idKonsultan){
        this.idKonsultan = idKonsultan;
    }

    public String getIdKonsultan (){
        return idKonsultan;
    }
}
