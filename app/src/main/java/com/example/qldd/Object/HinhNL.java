package com.example.qldd.Object;

public class HinhNL {
    String imageUrl;

    public HinhNL() {
    }

    public HinhNL(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "HinhNL{" +
                "imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
