package com.MyFood.controller.implementation;

import com.MyFood.dto.AddressDto;
import com.MyFood.service.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressControllerImplementation.class)
class AddressControllerImplementationTest {

    @MockBean
    private AddressService service;
    @InjectMocks
    private AddressControllerImplementation controller;
    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();
    String url = "/api/v1/address";
    private AddressDto addressDto = new AddressDto("01.001-000", "Passagem da fe", (short)12, "Lado Impar", "Sao francisco", "SP");

    @Test
    @DisplayName("Cria / retorna um endereço existente")
    void shouldBeCreateNewAddressOrReturnAddressExistent() throws Exception {
        given(service.getNewAddress(addressDto)).willReturn(addressDto);

        mockMvc.perform(post(url).content(mapper.writeValueAsString(addressDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(mapper.writeValueAsString(addressDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        verify(service,times(1)).getNewAddress(addressDto);
    }

    @Test
    @DisplayName("Busca um endereço existente")
    void shouldBeFindAddressExistent() throws Exception {
        given(service.getAddressByInformations(addressDto.cep(), addressDto.logradouro(), addressDto.number())).willReturn(addressDto);

        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON)
                        .param("cep", addressDto.cep())
                        .param("logradouro", addressDto.logradouro())
                        .param("number", String.valueOf(addressDto.number())))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(addressDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        verify(service,times(1)).getAddressByInformations(addressDto.cep(), addressDto.logradouro(), addressDto.number());
    }

    @Test
    @DisplayName("Deleta um endereço existente")
    void shouldBeDeleteAddressExistent() throws Exception {

        mockMvc.perform(delete(url).content(mapper.writeValueAsString(addressDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Endereço deletado com sucesso"))
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andDo(print())
                .andReturn();
    }
}