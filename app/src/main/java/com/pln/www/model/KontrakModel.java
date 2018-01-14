package com.pln.www.model;

import java.util.Date;

/**
 * Created by User on 13/01/2018.
 */

public class KontrakModel {
    String idKontrak, noKontrak, tglMulai, tglAkhir;

    public KontrakModel(String idKontrak, String noKontrak, String tglMulai, String tglAkhir) {
        this.idKontrak = idKontrak;
        this.noKontrak = noKontrak;
        this.tglMulai = tglMulai;
        this.tglAkhir = tglAkhir;
    }

    public String getIdKontrak() {
        return idKontrak;
    }

    public void setIdKontrak(String idKontrak) {
        this.idKontrak = idKontrak;
    }

    public String getNoKontrak() {
        return noKontrak;
    }

    public void setNoKontrak(String noKontrak) {
        this.noKontrak = noKontrak;
    }

    public String getTglMulai() {
        return tglMulai;
    }

    public void setTglMulai(String tglMulai) {
        this.tglMulai = tglMulai;
    }

    public String getTglAkhir() {
        return tglAkhir;
    }

    public void setTglAkhir(String tglAkhir) {
        this.tglAkhir = tglAkhir;
    }
}
