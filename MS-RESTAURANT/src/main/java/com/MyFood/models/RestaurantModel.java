package com.MyFood.models;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "restaurants")
public class RestaurantModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 15)
    private String phone;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private Set<MenuModel> menu;

    public RestaurantModel(){}

    public RestaurantModel(UUID id, String name, String phone, Set<MenuModel> menu) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.menu = menu;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<MenuModel> getMenu() {
        return menu;
    }
}
