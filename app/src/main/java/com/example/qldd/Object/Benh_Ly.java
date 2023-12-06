package com.example.qldd.Object;

public class Benh_Ly {
    String BenhLy_Id;
    String TenBenhLy;
    String MucDo;

    public Benh_Ly() {
    }

    public Benh_Ly(String benhLy_Id, String tenBenhLy, String mucDo) {
        BenhLy_Id = benhLy_Id;
        TenBenhLy = tenBenhLy;
        MucDo = mucDo;
    }

    public String getBenhLy_Id() {
        return BenhLy_Id;
    }

    public void setBenhLy_Id(String benhLy_Id) {
        BenhLy_Id = benhLy_Id;
    }

    public String getTenBenhLy() {
        return TenBenhLy;
    }

    public void setTenBenhLy(String tenBenhLy) {
        TenBenhLy = tenBenhLy;
    }

    public String getMucDo() {
        return MucDo;
    }

    public void setMucDo(String mucDo) {
        MucDo = mucDo;
    }

    @Override
    public String toString() {
        return "Benh_Ly{" +
                "BenhLy_Id='" + BenhLy_Id + '\'' +
                ", TenBenhLy='" + TenBenhLy + '\'' +
                ", MucDo='" + MucDo + '\'' +
                '}';
    }
}
