package com.MyFood.controller;

import com.MyFood.dto.RestaurantMenuDto;
import com.MyFood.dto.RestaurantsNameDto;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface RestaurantController {

    ResponseEntity<List<RestaurantsNameDto>> getAllRestaurantsName();
    ResponseEntity<RestaurantMenuDto> getRestaurantMenu(String name);
}
