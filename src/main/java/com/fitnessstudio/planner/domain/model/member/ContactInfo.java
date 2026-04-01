package com.fitnessstudio.planner.domain.model.member;

import java.util.Objects;

public record ContactInfo(String email, String phoneNumber) {

    public ContactInfo {
        Objects.requireNonNull(email, "Email must not be null");
        email = email.trim().toLowerCase();
        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }
        if (phoneNumber != null) {
            phoneNumber = phoneNumber.trim();
        }
    }

    public static ContactInfo of(String email) {
        return new ContactInfo(email, null);
    }

    public static ContactInfo of(String email, String phoneNumber) {
        return new ContactInfo(email, phoneNumber);
    }

    public boolean hasPhoneNumber() {
        return phoneNumber != null && !phoneNumber.isBlank();
    }
}
