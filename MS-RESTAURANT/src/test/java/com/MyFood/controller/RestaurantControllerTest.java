package com.MyFood.controller;

import com.MyFood.controller.implementation.RestaurantControllerImplementation;
import com.MyFood.dto.MenuDto;
import com.MyFood.dto.OrderRestaurantDto;
import com.MyFood.dto.RestaurantMenuDto;
import com.MyFood.dto.RestaurantsNameDto;
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

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @MockBean
    private RestaurantServices services;
    @InjectMocks
    private RestaurantControllerImplementation controller;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    private String url = "/restaurants/v1";
    private MenuDto item01 = new MenuDto("Pizza", 50.00);
    private MenuDto item02 = new MenuDto("Picanha", 80.00);
    private RestaurantsNameDto restaurante = new RestaurantsNameDto("Maciota");
    private RestaurantsNameDto restaurante02 = new RestaurantsNameDto("Kevelin");
    private RestaurantMenuDto restaurante03 = new RestaurantMenuDto("Maciota", List.of(item01, item02));

    @Test
    @DisplayName("Rota que retorna o nome dos restaurante scadastrados")
    public void shouldBeReturnRestaurantsName() throws Exception {
        given(services.getAllRestaurantsName()).willReturn(List.of(restaurante, restaurante02));

        mockMvc.perform(get(url + "/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(List.of(restaurante, restaurante02))))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        verify(services,times(1)).getAllRestaurantsName();
    }

    @Test
    @DisplayName("Rota que retorna o cardapio de um restaurante especifico")
    public void shouldBeReturnMenu() throws Exception {
        given(services.getRestaurantMenu(restaurante.name())).willReturn(restaurante03);

        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON)
                        .queryParam("name",restaurante.name()))
                            .andExpect(status().isOk())
                            .andExpect(content().string(mapper.writeValueAsString(restaurante03)))
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andDo(print())
                            .andReturn();

        verify(services,times(1)).getRestaurantMenu(restaurante.name());
    }
}