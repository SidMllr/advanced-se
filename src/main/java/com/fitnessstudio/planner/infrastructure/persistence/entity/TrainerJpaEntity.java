package com.fitnessstudio.planner.infrastructure.persistence.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "trainers")
public class TrainerJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "trainer_qualifications", joinColumns = @JoinColumn(name = "trainer_id"))
    @Column(name = "qualification")
    private List<String> qualifications = new ArrayList<>();

    @Column(name = "contact_email")
    private String contactEmail;

    protected TrainerJpaEntity() {}

    public TrainerJpaEntity(UUID id, String name, List<String> qualifications, String contactEmail) {
        this.id = id;
        this.name = name;
        this.qualifications = new ArrayList<>(qualifications);
        this.contactEmail = contactEmail;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public List<String> getQualifications() { return qualifications; }
    public String getContactEmail() { return contactEmail; }

    public void setName(String name) { this.name = name; }
    public void setQualifications(List<String> qualifications) { this.qualifications = qualifications; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
}
