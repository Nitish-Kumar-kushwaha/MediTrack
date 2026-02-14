package com.airtribe.meditrack.test;

import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.Validator;


public class ValidatorTest {

    public static void main(String[] args) {
        testValidInputs();
        testInvalidName();
        testInvalidAge();
        testInvalidFee();
        testInvalidDisease();
        System.out.println("ValidatorTest completed");
    }

    private static void testValidInputs() {
        try {
            Validator.validateName("Alice");
            Validator.validateAge(30);
            Validator.validateConsultationFee(50.0);
            Validator.validateDisease("Flu");
            System.out.println("PASS: valid inputs");
        } catch (InvalidDataException ex) {
            System.out.println("FAIL: valid inputs raised: " + ex.getMessage());
        }
    }

    private static void testInvalidName() {
        try {
            Validator.validateName("  ");
            System.out.println("FAIL: invalid name did not throw");
        } catch (InvalidDataException ex) {
            System.out.println("PASS: invalid name threw: " + ex.getMessage());
        }
    }

    private static void testInvalidAge() {
        try {
            Validator.validateAge(0);
            System.out.println("FAIL: invalid age did not throw");
        } catch (InvalidDataException ex) {
            System.out.println("PASS: invalid age threw: " + ex.getMessage());
        }
    }

    private static void testInvalidFee() {
        try {
            Validator.validateConsultationFee(-10.0);
            System.out.println("FAIL: invalid fee did not throw");
        } catch (InvalidDataException ex) {
            System.out.println("PASS: invalid fee threw: " + ex.getMessage());
        }
    }

    private static void testInvalidDisease() {
        try {
            Validator.validateDisease("");
            System.out.println("FAIL: invalid disease did not throw");
        } catch (InvalidDataException ex) {
            System.out.println("PASS: invalid disease threw: " + ex.getMessage());
        }
    }
}
