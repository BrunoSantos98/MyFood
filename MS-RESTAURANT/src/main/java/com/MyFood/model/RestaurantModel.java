package com.MyFood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "RESTAURANTS")
public class RestaurantModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID restaurantId;
    @Column(nullable = false, length = 75)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "restaurant")
    @JsonIgnore
    private List<ItensCardapio> cardapio;

    public RestaurantModel() {
    }

    public RestaurantModel(UUID restaurantId, String name, List<ItensCardapio> cardapio) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.cardapio = cardapio;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<ItensCardapio> getCardapio() {
        return cardapio;
    }
    public void setCardapio(List<ItensCardapio> cardapio) {
        this.cardapio = cardapio;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestaurantModel that)) return false;
        return Objects.equals(restaurantId, that.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId);
    }
}
