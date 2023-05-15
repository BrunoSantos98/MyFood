package com.MyFood.services.implementations;

import com.MyFood.dto.OrderRestaurantDto;
import com.MyFood.models.MenuModel;
import com.MyFood.models.RestaurantModel;
import com.MyFood.repositories.MenuRepository;
import com.MyFood.repositories.RestaurantRepository;
import com.MyFood.services.MenuServices;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuImplementationServices implements MenuServices {

    private final MenuRepository repository;
    private final RestaurantRepository restaurantRepository;

    public MenuImplementationServices(MenuRepository repository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<OrderRestaurantDto> getItenForOrder(List<String> names, String name) {

        RestaurantModel  restaurantModel = restaurantRepository.findByName(name);
        List<OrderRestaurantDto> orderList = new ArrayList<>();

        for(int i =0; i < names.size(); i++){
            MenuModel menuModel = repository.findByNameAndRestaurant(names.get(i), restaurantModel);
            orderList.add(new OrderRestaurantDto(name, menuModel.getName(), menuModel.getPrice()));
        }
        return orderList;
    }
}
