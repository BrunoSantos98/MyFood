package com.MyFood.dto;

import java.util.List;

public record RestaurantMenuDto(String name, List<MenuDto> menu) {
}
