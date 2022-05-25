package com.example.pregbe.Model;

import com.example.pregbe.R;

import java.util.ArrayList;

public class Danhsach {
    String Tieude, description, img;
    public Danhsach(){};

    public Danhsach(String tieude, String description, String sophut) {
        Tieude = tieude;
        this.description = description;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTieude() {
        return Tieude;
    }

    public void setTieude(String tieude) {
        Tieude = tieude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
