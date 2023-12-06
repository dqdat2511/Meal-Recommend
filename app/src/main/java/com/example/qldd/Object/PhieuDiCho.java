package com.example.qldd.Object;

public class PhieuDiCho {
    String id_user;
    String id_Phieu;
    String id_nguyenLieu;
    int so_luong;

    public PhieuDiCho() {
    }

    public PhieuDiCho(String id_user, String id_Phieu, String id_nguyenLieu, int so_luong) {
        this.id_user = id_user;
        this.id_Phieu = id_Phieu;
        this.id_nguyenLieu = id_nguyenLieu;
        this.so_luong = so_luong;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_Phieu() {
        return id_Phieu;
    }

    public void setId_Phieu(String id_Phieu) {
        this.id_Phieu = id_Phieu;
    }

    public String getId_nguyenLieu() {
        return id_nguyenLieu;
    }

    public void setId_nguyenLieu(String id_nguyenLieu) {
        this.id_nguyenLieu = id_nguyenLieu;
    }

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }

    @Override
    public String toString() {
        return "PhieuDiCho{" +
                "id_user='" + id_user + '\'' +
                ", id_Phieu='" + id_Phieu + '\'' +
                ", id_nguyenLieu='" + id_nguyenLieu + '\'' +
                ", so_luong=" + so_luong +
                '}';
    }
}
