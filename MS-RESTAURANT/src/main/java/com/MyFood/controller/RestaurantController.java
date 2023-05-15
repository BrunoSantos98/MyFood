package com.MyFood.controller;

import com.MyFood.dto.RestaurantMenuDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantController {

    ResponseEntity<List<String>> getRestaurantNames();
    ResponseEntity<RestaurantMenuDto> getRestaurantMenu(String restaurantName);
}
