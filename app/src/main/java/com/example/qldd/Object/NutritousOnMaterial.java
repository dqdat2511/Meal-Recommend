package com.example.qldd.Object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NutritousOnMaterial {
    public String id;
    public String idMaterial;
    String idNutritous ;
    String value;

    public NutritousOnMaterial() {
    }

    public NutritousOnMaterial(String id, String idMaterial, String idNutritous, String value) {
        this.id = id;
        this.idMaterial = idMaterial;
        this.idNutritous = idNutritous;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(String idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getIdNutritous() {
        return idNutritous;
    }

    public void setIdNutritous(String idNutritous) {
        this.idNutritous = idNutritous;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "NutritousOnMaterial{" +
                "id='" + id + '\'' +
                ", idMaterial='" + idMaterial + '\'' +
                ", idNutritous=" + idNutritous +
                ", value='" + value + '\'' +
                '}';
    }
}
