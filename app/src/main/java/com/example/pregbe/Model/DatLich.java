package com.example.pregbe.Model;

public class DatLich {
    String ngayDat,tgDat,tieuDe,moTa;

    public DatLich(){}

    public DatLich(String ngayDat, String tgDat, String tieuDe, String moTa) {
        this.ngayDat = ngayDat;
        this.tgDat = tgDat;
        this.tieuDe = tieuDe;
        this.moTa = moTa;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }

    public String getTgDat() {
        return tgDat;
    }

    public void setTgDat(String tgDat) {
        this.tgDat = tgDat;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
