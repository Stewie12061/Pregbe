package com.example.pregbe.Model;

public class TenBe {
    public String soTuan, tenBaby;

    public TenBe(){

    }

    public TenBe(String soTuan, String tenBaby) {
        this.soTuan = soTuan;
        this.tenBaby = tenBaby;
    }

    public String getSoTuan() {
        return soTuan;
    }

    public void setSoTuan(String soTuan) {
        this.soTuan = soTuan;
    }

    public String getTenBaby() {
        return tenBaby;
    }

    public void setTenBaby(String tenBaby) {
        this.tenBaby = tenBaby;
    }
}
