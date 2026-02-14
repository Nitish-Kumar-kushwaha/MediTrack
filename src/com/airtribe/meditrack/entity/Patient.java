package com.airtribe.meditrack.entity;

/**
 * Represents a patient in the system.
 * <p>
 * This class implements {@link Cloneable} and provides a {@link #clone()} method
 * that returns a deep copy of the instance. Immutable fields from the
 * superclass are preserved and mutable or reference fields are defensively
 * copied to ensure callers receive an independent object.
 */
public class Patient extends Person implements Cloneable {

    private String disease;

    public Patient(int id, String name, int age, String disease) {
        super(id, name, age);  // call parent constructor
        this.disease = disease;
    }

    public String getDisease() {
        return disease;
    }

    /**
     * Creates and returns a deep copy of this Patient.
     * <p>
     * The implementation attempts to use {@code super.clone()} for efficiency
     * and falls back to manual construction if cloning is not supported.
     *
     * @return a deep copy of this {@code Patient}
     */
    @Override
    public Patient clone() {
        try {
            Patient cloned = (Patient) super.clone();
            // Strings are immutable, but create defensive copies if callers expect distinct instances
            cloned.disease = this.disease == null ? null : new String(this.disease);
            return cloned;
        } catch (CloneNotSupportedException e) {
            // Fallback: manual deep copy
            return new Patient(this.getId(), this.getName(), this.getAge(),
                    this.disease == null ? null : new String(this.disease));
        }
    }
}
