package com.example.gdprapp.data.model;

public class Company {
    String name;
    String email;

    public Company(String name, String email) {
        this.name = name;
        this.email = email;
    }


    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
