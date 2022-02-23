package com.android.management.model;

public enum Validity {

    Manager("Manager"),
    Admin("Admin"),
    Wallet("Wallet"),
    Student("Student");

    private final String value;

    Validity(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
