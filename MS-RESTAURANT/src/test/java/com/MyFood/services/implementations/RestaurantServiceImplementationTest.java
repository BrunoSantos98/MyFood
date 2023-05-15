package com.MyFood.services.implementations;

import com.MyFood.dto.RestaurantNames;
import com.MyFood.models.RestaurantModel;
import com.MyFood.repositories.RestaurantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplementationTest {

    @Mock
    private RestaurantRepository repository;
    @InjectMocks
    private RestaurantServiceImplementation service;

    private final RestaurantNames res01 = new RestaurantNames("restaurante 01");
    private final RestaurantModel restaurantModel = new RestaurantModel(UUID.randomUUID(), res01.name(), "(11) 3346-1575", new HashSet<>());

    @Test
    @DisplayName("Retorna a lista de Restaurantes Cadastrados")
    void shouldBeReturnRestaurants(){
        RestaurantNames res02 = new RestaurantNames("restaurante 02");
        RestaurantNames res03 = new RestaurantNames("restaurante 03");
        RestaurantModel restaurantModel02 = new RestaurantModel(UUID.randomUUID(), res02.name(), "(11) 3346-1575", new HashSet<>());
        RestaurantModel restaurantModel03 = new RestaurantModel(UUID.randomUUID(), res03.name(), "(11) 3346-1575", new HashSet<>());

        given(repository.findAll()).willReturn(List.of(restaurantModel, restaurantModel02, restaurantModel03));

        service.getAllRestaurants();

        verify(repository,times(1)).findAll();
    }

    @Test
    @DisplayName("Retorna o cardapio de um restaurante")
    void shouldBeReturnRestaurantMenu(){
        given(repository.findByName(res01.name())).willReturn(restaurantModel);

        service.getRestaurantMenu(res01.name());

        verify(repository,times(1)).findByName(res01.name());
    }
}