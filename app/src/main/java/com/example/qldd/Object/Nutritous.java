package com.example.qldd.Object;

public class Nutritous {
    public String id;
    public String name;

    public Nutritous() {
    }

    public Nutritous(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Nutritous{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
