package com.example.pregbe.Model;

public class Baiviet {
    private int pic;
    private String Tuade, Gioithieu;

    public Baiviet(){};

    public Baiviet(int pic, String tuade, String gioithieu, String id) {
        this.pic = pic;
        this.Tuade = tuade;
        this.Gioithieu = gioithieu;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getTuade() {
        return Tuade;
    }

    public void setTuade(String tuade) {
        Tuade = tuade;
    }


    public String getGioithieu() {
        return Gioithieu;
    }

    public void setGioithieu(String gioithieu) {
        Gioithieu = gioithieu;
    }
}
