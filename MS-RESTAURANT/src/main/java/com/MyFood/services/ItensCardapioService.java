package com.MyFood.services;

import com.MyFood.dto.OrderRestaurantDto;

import java.util.List;

public interface ItensCardapioService {
    List<OrderRestaurantDto> getListOfProducts(List<String> productsName);
}
