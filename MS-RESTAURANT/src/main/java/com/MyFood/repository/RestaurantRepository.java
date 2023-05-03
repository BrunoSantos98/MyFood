package com.MyFood.repository;

import com.MyFood.model.RestaurantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantModel, UUID> {

    RestaurantModel findByName(String name);
}
