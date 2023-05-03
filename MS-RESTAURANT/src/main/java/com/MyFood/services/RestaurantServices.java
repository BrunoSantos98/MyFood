package com.MyFood.services;

import com.MyFood.dto.RestaurantMenuDto;
import com.MyFood.dto.RestaurantsNameDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantServices {
    List<RestaurantsNameDto> getAllRestaurantsName();

    RestaurantMenuDto getRestaurantMenu(String name);
}
