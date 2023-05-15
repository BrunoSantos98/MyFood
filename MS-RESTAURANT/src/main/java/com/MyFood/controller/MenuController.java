package com.MyFood.controller;

import com.MyFood.dto.OrderRestaurantDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MenuController {

    ResponseEntity<List<OrderRestaurantDto>> getOrder(List<String> names, String restaurant);
}
