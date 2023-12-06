package com.example.qldd.Object;

public class Material {
    String nguyenLieu_id;
    String tenNguyenLieu;
    String hinhNguyenLieu;
    String danhMuc_id;

    public Material() {
    }

    public Material(String nguyenLieu_id, String tenNguyenLieu, String hinhNguyenLieu, String danhMuc_id) {
        this.nguyenLieu_id = nguyenLieu_id;
        this.tenNguyenLieu = tenNguyenLieu;
        this.hinhNguyenLieu = hinhNguyenLieu;
        this.danhMuc_id = danhMuc_id;
    }

    public String getNguyenLieu_id() {
        return nguyenLieu_id;
    }

    public void setNguyenLieu_id(String nguyenLieu_id) {
        this.nguyenLieu_id = nguyenLieu_id;
    }

    public String getTenNguyenLieu() {
        return tenNguyenLieu;
    }

    public void setTenNguyenLieu(String tenNguyenLieu) {
        this.tenNguyenLieu = tenNguyenLieu;
    }


    public String getHinhNguyenLieu() {
        return hinhNguyenLieu;
    }

    public void setHinhNguyenLieu(String hinhNguyenLieu) {
        this.hinhNguyenLieu = hinhNguyenLieu;
    }

    public String getDanhMuc_id() {
        return danhMuc_id;
    }

    public void setDanhMuc_id(String danhMuc_id) {
        this.danhMuc_id = danhMuc_id;
    }

    @Override
    public String toString() {
        return "Material{" +
                "nguyenLieu_id='" + nguyenLieu_id + '\'' +
                ", tenNguyenLieu='" + tenNguyenLieu + '\'' +
                ", hinhNguyenLieu='" + hinhNguyenLieu + '\'' +
                ", danhMuc_id='" + danhMuc_id + '\'' +
                '}';
    }
}
