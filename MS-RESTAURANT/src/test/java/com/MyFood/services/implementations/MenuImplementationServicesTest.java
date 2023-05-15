package com.MyFood.services.implementations;

import com.MyFood.dto.OrderRestaurantDto;
import com.MyFood.models.MenuModel;
import com.MyFood.models.RestaurantModel;
import com.MyFood.repositories.MenuRepository;
import com.MyFood.repositories.RestaurantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MenuImplementationServicesTest {

    @Mock
    private MenuRepository repository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @InjectMocks
    private MenuImplementationServices services;

    private MenuModel menuModel01 = new MenuModel(UUID.randomUUID(), "Burgao", 25.00, "DESCRICAO 01", new RestaurantModel());
    private MenuModel menuModel02 = new MenuModel(UUID.randomUUID(), "X-Burgao", 35.00, "DESCRICAO 02", new RestaurantModel());
    private MenuModel menuModel03 = new MenuModel(UUID.randomUUID(), "Mega-Burgao", 50.00, "DESCRICAO 03", new RestaurantModel());
    private RestaurantModel restaurant = new RestaurantModel(UUID.randomUUID(), "Bart", "(11)3325-6987", Set.of(menuModel01, menuModel02, menuModel03));

    @Test
    @DisplayName("Teste de Envio dos itens solicitado")
    void shouldBeReturnItensSolicited(){
        given(restaurantRepository.findByName(restaurant.getName())).willReturn(restaurant);
        given(repository.findByNameAndRestaurant(menuModel01.getName(), restaurant)).willReturn(menuModel01);
        given(repository.findByNameAndRestaurant(menuModel02.getName(), restaurant)).willReturn(menuModel01);
        given(repository.findByNameAndRestaurant(menuModel03.getName(), restaurant)).willReturn(menuModel01);

        List<OrderRestaurantDto> itens = services.getItenForOrder(List.of(menuModel01.getName(), menuModel02.getName(), menuModel03.getName()), restaurant.getName());

        verify(restaurantRepository,times(1)).findByName(restaurant.getName());
        verify(repository,times(1)).findByNameAndRestaurant(menuModel01.getName(), restaurant);
        verify(repository,times(1)).findByNameAndRestaurant(menuModel02.getName(), restaurant);
        verify(repository,times(1)).findByNameAndRestaurant(menuModel03.getName(), restaurant);
    }

}