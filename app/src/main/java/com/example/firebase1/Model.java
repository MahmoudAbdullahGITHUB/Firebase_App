package com.example.firebase1;

public class Model {
    private String name , age , phone;

    public Model(String name, String age, String phone) {
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public Model() {
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }
}
