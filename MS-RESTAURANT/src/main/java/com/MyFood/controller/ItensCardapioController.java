package com.MyFood.controller;

import com.MyFood.dto.MenuDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItensCardapioController {
    ResponseEntity<List<MenuDto>> getOrderedProductsList(List<String> list);
}
