package com.MyFood.services;

import com.MyFood.dto.MenuDto;

import java.util.List;

public interface ItensCardapioService {
    List<MenuDto> getListOfProducts(List<String> productsName);
}
