package com.MyFood.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "DELIVERYMAN_TABLE")
public class Deliveryman {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false,length = 50)
    private String name;
    @Column(nullable = false,length = 50)
    private String automobile;
    @Column(nullable = false,length = 8)
    private String licensePlate;
    @Column(nullable = false,length = 10)
    private String colorMobile;

    public Deliveryman() {}

    public Deliveryman(UUID id, String name, String automobile, String licensePlate, String colorMobile) {
        this.id = id;
        this.name = name;
        this.automobile = automobile;
        this.licensePlate = licensePlate;
        this.colorMobile = colorMobile;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAutomobile() {
        return automobile;
    }

    public void setAutomobile(String automobile) {
        this.automobile = automobile;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getColorMobile() {
        return colorMobile;
    }

    public void setColorMobile(String colorMobile) {
        this.colorMobile = colorMobile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deliveryman that)) return false;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
