package com.MyFood.services;

import com.MyFood.dto.OrderRestaurantDto;

import java.util.List;

public interface MenuServices {
    List<OrderRestaurantDto> getItenForOrder(List<String> names, String name);
}
