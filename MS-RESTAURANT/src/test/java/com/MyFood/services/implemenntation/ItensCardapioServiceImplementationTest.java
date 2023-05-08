package com.MyFood.services.implemenntation;

import com.MyFood.dto.OrderRestaurantDto;
import com.MyFood.model.ItensCardapio;
import com.MyFood.repository.ItensCardapioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ItensCardapioServiceImplementationTest{
    @Mock
    private ItensCardapioRepository repository;
    @InjectMocks
    private ItensCardapioServiceImplementation implementation;
    private ItensCardapio item01 = new ItensCardapio(UUID.randomUUID(), "Pizza", 50.00);
    private ItensCardapio item02 = new ItensCardapio(UUID.randomUUID(), "Picanha", 80.00);
    List<String> productsName = List.of(item01.getName(), item02.getName());
    @Test
    @DisplayName("Deve retornar lista de Pedidos")
    void shouldBeReturnOrderesList(){
        given(repository.findByName(item01.getName())).willReturn(item01);
        given(repository.findByName(item02.getName())).willReturn(item02);

        List<OrderRestaurantDto> listItens = implementation.getListOfProducts(productsName);

        verify(repository,times(1)).findByName(item01.getName());
        verify(repository,times(1)).findByName(item02.getName());
    }
}