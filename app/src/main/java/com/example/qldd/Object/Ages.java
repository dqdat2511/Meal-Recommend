package com.example.qldd.Object;

public class Ages {
    String age_id;
    String age;

    public Ages() {
    }

    public Ages(String age_id, String age) {
        this.age_id = age_id;
        this.age = age;
    }

    public String getAge_id() {
        return age_id;
    }

    public void setAge_id(String age_id) {
        this.age_id = age_id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Ages{" +
                "age_id='" + age_id + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
