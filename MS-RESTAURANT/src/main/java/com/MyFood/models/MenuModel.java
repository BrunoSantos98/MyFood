package com.MyFood.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "menu")
public class MenuModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private String description;
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private RestaurantModel restaurant;

    public MenuModel(){

    }

    public MenuModel(UUID id, String name, double price, String description, RestaurantModel restaurant) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.restaurant = restaurant;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RestaurantModel getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantModel restaurant) {
        this.restaurant = restaurant;
    }
}
