package com.MyFood.services;

import com.MyFood.dto.RestaurantMenuDto;

import java.util.List;

public interface RestaurantServices {
    List<String> getAllRestaurants();

    RestaurantMenuDto getRestaurantMenu(String name);
}
