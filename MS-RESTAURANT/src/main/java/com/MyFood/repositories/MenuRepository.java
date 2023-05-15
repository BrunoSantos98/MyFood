package com.MyFood.repositories;

import com.MyFood.models.MenuModel;
import com.MyFood.models.RestaurantModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MenuRepository extends JpaRepository<MenuModel, UUID> {
    MenuModel findByNameAndRestaurant(String name, RestaurantModel restaurant);
}
