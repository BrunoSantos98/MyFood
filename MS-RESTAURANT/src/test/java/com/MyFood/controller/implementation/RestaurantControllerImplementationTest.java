package com.MyFood.controller.implementation;

import com.MyFood.dto.MenuDto;
import com.MyFood.dto.RestaurantMenuDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class RestaurantControllerImplementationTest {

    @MockBean
    private RestaurantServices services;
    @InjectMocks
    private RestaurantControllerImplementation controller;
    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();

    private String url = "/restaurants/v1";

    @Test
    @DisplayName("Retorna o nome de todos os restaurantes")
    void shouldBeReturnALlRestaurantsName() throws Exception {
        given(services.getAllRestaurants()).willReturn(List.of("Restaurante 01", "Restaurante 02"));

        mockMvc.perform(get(url + "/all").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string(mapper.writeValueAsString(List.of("Restaurante 01", "Restaurante 02"))))
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andReturn();


        verify(services,times(1)).getAllRestaurants();
    }

    @Test
    @DisplayName("Retorna um restaurante pelo nome")
    void shouldBeReturnOneRestauranteByName() throws Exception {
        RestaurantMenuDto restaurantMenuDto = new RestaurantMenuDto("Restaurante 01",
                List.of(new MenuDto("Prato 01", 10.0, "Descricao 01"),
                        new MenuDto("Prato 02", 20.0, "Descricao 02")));

        given(services.getRestaurantMenu("Restaurante 01")).willReturn(restaurantMenuDto);

        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON).param("name", "Restaurante 01"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(restaurantMenuDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();


        verify(services,times(1)).getRestaurantMenu("Restaurante 01");
    }
}