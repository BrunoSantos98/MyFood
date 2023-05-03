package com.MyFood.dto;

import com.MyFood.model.ItensCardapio;

import java.util.List;

public record RestaurantMenuDto(String name, List<MenuDto> cardapio) {
}
