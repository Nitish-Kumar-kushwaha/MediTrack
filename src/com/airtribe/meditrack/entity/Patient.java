package com.airtribe.meditrack.entity;

public class Patient extends Person {

    private String disease;

    public Patient(int id, String name, int age, String disease) {
        super(id, name, age);  // call parent constructor
        this.disease = disease;
    }

    public String getDisease() {
        return disease;
    }
}
