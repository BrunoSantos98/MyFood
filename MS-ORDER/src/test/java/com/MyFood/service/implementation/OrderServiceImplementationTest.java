package com.MyFood.service.implementation;

import com.MyFood.model.OrderModel;
import com.MyFood.repository.DeliverymanRepository;
import com.MyFood.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplementationTest {

    @Mock
    private OrderRepository orderRepositoryepository;
    @Mock
    private DeliverymanRepository deliverymanRepository;
    @InjectMocks
    private OrderServiceImplementation implementation;

    @Test
    @DisplayName("Cria um pedido")
    void shouldBeCreateAOrder(){
    //TODO    given(repository.save(TODO)).willReturn(TODO)
    }

}