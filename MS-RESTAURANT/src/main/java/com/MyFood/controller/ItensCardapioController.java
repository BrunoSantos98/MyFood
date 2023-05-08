package com.MyFood.controller;

import com.MyFood.dto.OrderRestaurantDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItensCardapioController {
    ResponseEntity<List<OrderRestaurantDto>> getOrderedProductsList(List<String> list);
}
