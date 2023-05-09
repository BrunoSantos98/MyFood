package com.MyFood.services;

import com.MyFood.dto.RestaurantMenuDto;
import com.MyFood.dto.RestaurantsNameDto;
import com.MyFood.model.ItensCardapio;
import com.MyFood.model.RestaurantModel;
import com.MyFood.repository.RestaurantRepository;
import com.MyFood.services.implemenntation.RestaurantServicesImplementation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class RestaurantServicesTest {

    @Mock
    private RestaurantRepository repository;
    @InjectMocks
    private RestaurantServicesImplementation service;
    private ItensCardapio item01 = new ItensCardapio(UUID.randomUUID(), "Pizza", 50.00, new RestaurantModel(UUID.randomUUID(), "Tsc", new ArrayList<>()));
    private ItensCardapio item02 = new ItensCardapio(UUID.randomUUID(), "Picanha", 80.00, new RestaurantModel(UUID.randomUUID(), "Tsc", new ArrayList<>()));
    private RestaurantModel restaurante = new RestaurantModel(UUID.randomUUID(), "Maciota", List.of(item01, item02));
    private RestaurantModel restaurante02 = new RestaurantModel(UUID.randomUUID(), "Kevelin", List.of(item01, item02));
    @Test
    @DisplayName("Retorna somente os restaurantes cadastrados no BD")
    void shouldBeReturnRestaurants(){
        given(repository.findAll()).willReturn(List.of(restaurante, restaurante02));

        List<RestaurantsNameDto> restaurantsNameDtoList = service.getAllRestaurantsName();

        verify(repository,times(1)).findAll();
        assertEquals("Maciota", restaurantsNameDtoList.get(0).name());
        assertEquals("Kevelin", restaurantsNameDtoList.get(1).name());
    }

    @Test
    @DisplayName("Retorna um restaurante e seu cardapio")
    void shouldBeReturnRestaurantMenu(){
        given(repository.findByName(restaurante.getName())).willReturn(restaurante);

        RestaurantMenuDto response = service.getRestaurantMenu(restaurante.getName());

        verify(repository,times(1)).findByName(restaurante.getName());
        assertEquals(response.name(), restaurante.getName());
        assertEquals(response.cardapio().get(0).name(), restaurante.getCardapio().get(0).getName());
    }
}