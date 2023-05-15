package com.MyFood.controller.implementation;

import com.MyFood.dto.OrderRestaurantDto;
import com.MyFood.services.MenuServices;
import com.MyFood.services.RestaurantServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class MenuControllerImplementationTest {

    @MockBean
    private MenuServices services;
    @MockBean
    private RestaurantServices restaurantServices;
    @InjectMocks
    private MenuControllerImplementation controller;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    private List<String> listaNames = List.of("Hamburguer", "X-burguer", "Mega-burguer");
    private OrderRestaurantDto order01 = new OrderRestaurantDto("Restaurante 01", "Hamburguer", 25.00);
    private OrderRestaurantDto order02 = new OrderRestaurantDto("Restaurante 01", "X-burguer", 35.00);
    private OrderRestaurantDto order03 = new OrderRestaurantDto("Restaurante 01", "Mega-burguer", 50.00);
    private List<OrderRestaurantDto> listProducts = List.of(order01, order02, order03);
    private String url = "/menu/v1";
    @Test
    @DisplayName("Retorna a lista de produtos desejados")
    void shoudlBeReturnItensSolicited() throws Exception {
        given(services.getItenForOrder(listaNames, "Restaurante 01")).willReturn(listProducts);

        mockMvc.perform(post(url + "/{restaurant}", "Restaurante 01").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(listaNames)))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(listProducts)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }
}