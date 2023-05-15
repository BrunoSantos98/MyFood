package com.MyFood.services.implementations;

import com.MyFood.dto.MenuDto;
import com.MyFood.dto.RestaurantMenuDto;
import com.MyFood.models.RestaurantModel;
import com.MyFood.repositories.RestaurantRepository;
import com.MyFood.services.RestaurantServices;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantServiceImplementation implements RestaurantServices {

    private final RestaurantRepository repository;

    public RestaurantServiceImplementation(RestaurantRepository repository) {
        this.repository = repository;
    }

    private String restaurantModelsGetName(RestaurantModel restaurant) {
        return restaurant.getName();
    }

    @Override
    public List<String> getAllRestaurants() {
        List<RestaurantModel> restaurantModels = repository.findAll();
        return restaurantModels.stream().map(this::restaurantModelsGetName).toList();
    }

    @Override
    public RestaurantMenuDto getRestaurantMenu(String name) {
        RestaurantModel restaurantModel = repository.findByName(name);
        List<MenuDto> menuDtoList = new ArrayList<>();
        restaurantModel.getMenu().forEach(menu -> menuDtoList.add(new MenuDto(menu.getName(), menu.getPrice(), menu.getDescription())));
        return new RestaurantMenuDto(restaurantModel.getName(),menuDtoList);
    }
}
