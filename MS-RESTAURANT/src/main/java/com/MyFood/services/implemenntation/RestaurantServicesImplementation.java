package com.MyFood.services.implemenntation;

import com.MyFood.dto.MenuDto;
import com.MyFood.dto.RestaurantMenuDto;
import com.MyFood.dto.RestaurantsNameDto;
import com.MyFood.model.ItensCardapio;
import com.MyFood.model.RestaurantModel;
import com.MyFood.repository.RestaurantRepository;
import com.MyFood.services.RestaurantServices;

import java.util.List;

public class RestaurantServicesImplementation implements RestaurantServices {

    private final RestaurantRepository repository;

    public RestaurantServicesImplementation(RestaurantRepository repository) {
        this.repository = repository;
    }

    private RestaurantsNameDto restaurantsModelToDto(RestaurantModel model){
        return new RestaurantsNameDto(model.getName());
    }

    private MenuDto menuDto(ItensCardapio itens){
        return new MenuDto(itens.getName(), itens.getValue());
    }

    private RestaurantMenuDto restaurantMenuDto(RestaurantModel model){
        return new RestaurantMenuDto(model.getName(),model.getCardapio().stream().map(this::menuDto).toList());
    }

    @Override
    public List<RestaurantsNameDto> getAllRestaurantsName() {
        return repository.findAll().stream().map(this::restaurantsModelToDto).toList();
    }

    @Override
    public RestaurantMenuDto getRestaurantMenu(String name) {
        return restaurantMenuDto(repository.findByName(name));
    }
}
