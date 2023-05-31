package com.MyFood.controller.implementation;

import com.MyFood.dto.AddressDto;
import com.MyFood.dto.CustomerDto;
import com.MyFood.service.AddressService;
import com.MyFood.service.CustomerService;
import com.MyFood.service.DeliveryManService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CustomerControllerImplementationTest {

    @MockBean
    private CustomerService service;
    @MockBean
    private AddressService addressService;
    @MockBean
    private DeliveryManService deliveryManService;
    @InjectMocks
    private CustomerControllerImplementation controller;
    @Autowired
    MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    private CustomerDto customerDto = new CustomerDto("name", "000.000.000-00", "email@email.com", "(11) 93255-6987", new HashSet<>(), new HashSet<>());
    private  String url = "/api/v1/customer";

    @Test
    @DisplayName("Criação de novo Customer")
    void shouldBeCreateNewCustomer() throws Exception {
        given(service.createnewCustomer(customerDto)).willReturn(customerDto);

        mockMvc.perform(post(url).content(mapper.writeValueAsString(customerDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(mapper.writeValueAsString(customerDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("Encontra um customer pelo cpf")
    void shouldBeFindCustomerByCpf() throws Exception {
        given(service.getCustomerByCpf(customerDto.cpf())).willReturn(customerDto);

        mockMvc.perform(get(url + "/findByCpf/{cpf}",customerDto.cpf()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(customerDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("Encontra um customer pelo email")
    void shouldBeFindCustomerByEmail() throws Exception {
        given(service.getCustomerByEmail(customerDto.email())).willReturn(customerDto);

        mockMvc.perform(get(url + "/findByEmail").contentType(MediaType.APPLICATION_JSON).param("email", customerDto.email()))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(customerDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("Encontra o endereço de um customer pelo cpf")
    void shouldBeFindCustomerAddressessByCpf() throws Exception {
        AddressDto address = new AddressDto("01010-001", "rua 1", (short) 2, "Complemento", "bairro", "SP");
        AddressDto address02 = new AddressDto("01010-001", "rua 2", (short) 4, "Complemento", "bairro", "SP");
        Set<AddressDto> addressDtos = new HashSet<>();
        addressDtos.add(address);
        addressDtos.add(address02);

        given(service.getCustomerAddresses(customerDto.cpf())).willReturn(addressDtos);

        mockMvc.perform(get(url + "/findAddressess/{cpf}",customerDto.cpf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(addressDtos)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("Atualiza os dados de um cliente")
    void shouldBeUpdateCustomerData() throws Exception {
        CustomerDto customerDto02 = new CustomerDto("name 02", "000.000.000-01", "email02@email.com", "(11) 93255-0000", new HashSet<>(), new HashSet<>());

        given(service.updateUserData(customerDto02, customerDto.cpf())).willReturn(customerDto02);

        mockMvc.perform(patch(url + "/updateData/{cpf}",customerDto.cpf())
                        .content(mapper.writeValueAsString(customerDto02)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(customerDto02)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("Atualiza o Endereço de um cliente")
    void shouldBeUpdateCustomerAddress() throws Exception {
        AddressDto address02 = new AddressDto("01010-001", "rua 2", (short) 4, "Complemento", "bairro", "SP");
        customerDto.address().add(address02);

        given(service.updateUserAddress(address02, customerDto.cpf())).willReturn(customerDto);

        mockMvc.perform(patch(url + "/updateAddress/{cpf}",customerDto.cpf())
                        .content(mapper.writeValueAsString(address02)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(customerDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("Deleta um usuario")
    void shouldBeDeleteCustomer() throws Exception {

        mockMvc.perform(delete(url).content(mapper.writeValueAsString(customerDto.cpf())).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario deletado com sucesso"))
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andDo(print())
                .andReturn();
    }
}