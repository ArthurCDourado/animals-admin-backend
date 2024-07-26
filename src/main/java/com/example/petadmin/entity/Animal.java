package com.example.petadmin.entity;

import com.example.petadmin.enums.AnimalStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String imageUrl;

    @NotBlank
    private String category;

    private OffsetDateTime birthdate;

    @Enumerated(EnumType.STRING)
    private AnimalStatus status = AnimalStatus.AVAILABLE;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public OffsetDateTime getBirthdate() {
        return birthdate;
    }

    @Transient
    public long getAge() {
        return getBirthdate().until(OffsetDateTime.now(), ChronoUnit.YEARS);
    }

    public AnimalStatus getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBirthdate(OffsetDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public void setStatus(AnimalStatus status) {
        this.status = status;
    }
}
