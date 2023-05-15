package com.MyFood.repositories;

import com.MyFood.models.RestaurantModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<RestaurantModel, UUID> {
    RestaurantModel findByName(String name);
}
