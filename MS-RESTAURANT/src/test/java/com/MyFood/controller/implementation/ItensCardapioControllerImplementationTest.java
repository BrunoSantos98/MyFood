package com.MyFood.controller.implementation;

import com.MyFood.dto.MenuDto;
import com.MyFood.services.ItensCardapioService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItensCardapioControllerImplementation.class)
class ItensCardapioControllerImplementationTest {
    @MockBean
    private ItensCardapioService service;
    @InjectMocks
    private ItensCardapioControllerImplementation implementation;
    @Autowired
    MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    private MenuDto item01 = new MenuDto("Pizza", 50.00);
    private MenuDto item02 = new MenuDto("Picanha", 80.00);
    List<String> productsName = List.of(item01.name(), item02.name());
    List<MenuDto> orderedProducList = List.of(item01, item02);
    String url = "/menu/v1";
    @Test
    @DisplayName("Retorna lista de produstos em m response entity")
    void shoudlBeReturnListOfProductsSelected() throws Exception {
        given(service.getListOfProducts(productsName)).willReturn(orderedProducList);

        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(productsName)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(orderedProducList)))
                .andDo(print())
                .andReturn();
    }

}