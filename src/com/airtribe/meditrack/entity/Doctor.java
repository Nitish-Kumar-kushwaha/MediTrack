package com.airtribe.meditrack.entity;

public class Doctor extends Person {

    private Specialization specialization;
    private double consultationFee;

    public Doctor(int id, String name, int age,
                  Specialization specialization,
                  double consultationFee) {

        super(id, name, age);  // calling parent constructor
        this.specialization = specialization;
        this.consultationFee = consultationFee;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public double getConsultationFee() {
        return consultationFee;
    }
}
