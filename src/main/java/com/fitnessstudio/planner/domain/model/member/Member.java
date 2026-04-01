package com.fitnessstudio.planner.domain.model.member;

import java.util.Objects;

public class Member {

    private final MemberId id;
    private String name;
    private ContactInfo contactInfo;

    public Member(MemberId id, String name, ContactInfo contactInfo) {
        this.id = Objects.requireNonNull(id, "MemberId must not be null");
        this.name = requireNonBlank(name, "Member name");
        this.contactInfo = Objects.requireNonNull(contactInfo, "ContactInfo must not be null");
    }

    public static Member create(String name, ContactInfo contactInfo) {
        return new Member(MemberId.generate(), name, contactInfo);
    }

    public void updateName(String name) {
        this.name = requireNonBlank(name, "Member name");
    }

    public void updateContactInfo(ContactInfo contactInfo) {
        this.contactInfo = Objects.requireNonNull(contactInfo, "ContactInfo must not be null");
    }

    private static String requireNonBlank(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " must not be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value.trim();
    }

    public MemberId getId() { return id; }
    public String getName() { return name; }
    public ContactInfo getContactInfo() { return contactInfo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Member{id=" + id + ", name='" + name + "'}";
    }
}
