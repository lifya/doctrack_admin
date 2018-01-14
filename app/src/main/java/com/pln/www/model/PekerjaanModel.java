package com.pln.www.model;

/**
 * Created by User on 13/01/2018.
 */

public class PekerjaanModel {
    String idPekerjaan, idKontrak, idKonsultan, namaPekerjaan, jenisPekerjaan, file;

    public PekerjaanModel(String idPekerjaan, String idKonsultan, String idKontrak, String namaPekerjaan, String jenisPekerjaan){
        this.idPekerjaan = idPekerjaan;
        this.idKonsultan = idKonsultan;
        this.idKontrak = idKontrak;
        this.namaPekerjaan = namaPekerjaan;
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

    public String getJenisPekerjaan() {
        return jenisPekerjaan;
    }

    public void setJenisPekerjaan(String jenisPekerjaan) {
        this.jenisPekerjaan = jenisPekerjaan;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
