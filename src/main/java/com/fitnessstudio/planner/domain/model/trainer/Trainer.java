package com.fitnessstudio.planner.domain.model.trainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Trainer {

    private final TrainerId id;
    private String name;
    private final List<TrainerQualification> qualifications;
    private String contactEmail;

    public Trainer(TrainerId id,
                   String name,
                   List<TrainerQualification> qualifications,
                   String contactEmail) {
        this.id = Objects.requireNonNull(id, "TrainerId must not be null");
        this.name = requireNonBlank(name, "Trainer name");
        this.qualifications = new ArrayList<>(
                Objects.requireNonNullElseGet(qualifications, ArrayList::new));
        this.contactEmail = contactEmail;
    }

    public static Trainer create(String name, List<TrainerQualification> qualifications, String contactEmail) {
        return new Trainer(TrainerId.generate(), name, qualifications, contactEmail);
    }

    public void addQualification(TrainerQualification qualification) {
        Objects.requireNonNull(qualification, "Qualification must not be null");
        if (!qualifications.contains(qualification)) {
            qualifications.add(qualification);
        }
    }

    public void removeQualification(TrainerQualification qualification) {
        qualifications.remove(qualification);
    }

    public void updateName(String name) {
        this.name = requireNonBlank(name, "Trainer name");
    }

    public void updateContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    private static String requireNonBlank(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " must not be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value.trim();
    }

    public TrainerId getId() { return id; }
    public String getName() { return name; }
    public List<TrainerQualification> getQualifications() {
        return Collections.unmodifiableList(qualifications);
    }
    public String getContactEmail() { return contactEmail; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trainer that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Trainer{id=" + id + ", name='" + name + "'}";
    }
}
