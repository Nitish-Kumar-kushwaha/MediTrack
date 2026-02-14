package com.airtribe.meditrack.entity;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return this.getId() == doctor.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
