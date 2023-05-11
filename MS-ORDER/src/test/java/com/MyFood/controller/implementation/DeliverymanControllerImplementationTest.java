package com.MyFood.controller.implementation;

import com.MyFood.dto.DeliverymanDto;
import com.MyFood.dto.DeliverymanToCustomerDto;
import com.MyFood.model.Deliveryman;
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

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeliverymanControllerImplementation.class)
class DeliverymanControllerImplementationTest {

    @MockBean
    private DeliveryManService service;
    @InjectMocks
    private DeliverymanControllerImplementation controller;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    private Deliveryman deliveryman = new Deliveryman(UUID.randomUUID(), "Deliveryman", "000.000.000-00", "(11)93255-6987","Honda Fan 150", "fee-600", "Vermelho");
    private DeliverymanDto deliverymanDto = new DeliverymanDto("Deliveryman", "000.000.000-00", "(11)93255-6987","Honda Fan 150", "fee-600", "Vermelho");
    private DeliverymanToCustomerDto deliverymanToCustomerDto = new DeliverymanToCustomerDto("Deliveryman", "(11)93255-6987","Honda Fan 150", "fee-600", "Vermelho");
    String url = "/api/v1/deliveryman";

    @Test
    @DisplayName("Post para cadastrar Deliveryman")
    void shouldBeCreateNewDeliveryMan() throws Exception {
        given(service.createNewDeliveryman(deliverymanDto)).willReturn(deliverymanDto);

        mockMvc.perform(post(url).content(mapper.writeValueAsString(deliverymanDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(mapper.writeValueAsString(deliverymanDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        verify(service,times(1)).createNewDeliveryman(deliverymanDto);
    }

    @Test
    @DisplayName("GET para encontrar um deliveryman pelo ID")
    void shouldBeFIndDeliveryManById() throws Exception {
        given(service.findDeliverymanById(deliveryman.getId())).willReturn(deliveryman);

        mockMvc.perform(get(url + "/admin/id/{id}",deliveryman.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(deliveryman)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        verify(service,times(1)).findDeliverymanById(deliveryman.getId());
    }

    @Test
    @DisplayName("GET para encontrar um deliveryman pelo CPF")
    void shouldBeFIndDeliveryManByCpf() throws Exception {
        given(service.findDeliverymanByCpf(deliverymanDto.cpf())).willReturn(deliveryman);

        mockMvc.perform(get(url + "/admin/cpf/{cpf}",deliverymanDto.cpf()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(deliveryman)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        verify(service,times(1)).findDeliverymanByCpf(deliverymanDto.cpf());
    }

    @Test
    @DisplayName("GET para encontrar um deliveryman pela placa do veiculo")
    void shouldBeFIndDeliveryManByLicensePlate() throws Exception {
        given(service.findDeliverymanByLicensePlate(deliverymanToCustomerDto.licensePlate())).willReturn(deliverymanToCustomerDto);

        mockMvc.perform(get(url + "/{licensePlate}",deliverymanToCustomerDto.licensePlate()).
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(deliverymanToCustomerDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        verify(service,times(1)).findDeliverymanByLicensePlate(deliverymanDto.licensePlate());
    }

    @Test
    @DisplayName("Deleta um deliveryman pelo Id")
    void shouldBeDeleteDeliveryManById() throws Exception {
        given(service.findDeliverymanById(deliveryman.getId())).willReturn(deliveryman);

        mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON)
                        .param("deliverymanInformation",  String.valueOf(deliveryman.getId()))
                        .param("typeInformation", "id"))
                .andExpect(status().isOk())
                .andExpect(content().string("Entregador deletado com sucesso"))
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andDo(print())
                .andReturn();

        verify(service,times(1)).deleteDeliveryman(String.valueOf(deliveryman.getId()), "id");
    }

    @Test
    @DisplayName("Deleta um deliveryman pelo Cpf")
    void shouldBeDeleteDeliveryManByCpf() throws Exception {
        given(service.findDeliverymanByCpf(deliverymanDto.cpf())).willReturn(deliveryman);

        mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON)
                    .param("deliverymanInformation",  deliverymanDto.cpf())
                    .param("typeInformation", "cpf"))
                .andExpect(status().isOk())
                .andExpect(content().string("Entregador deletado com sucesso"))
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andDo(print())
                .andReturn();

        verify(service,times(1)).deleteDeliveryman(deliverymanDto.cpf(), "cpf");
    }

    @Test
    @DisplayName("Deleta um deliveryman pelo LicensePlate")
    void shouldBeDeleteDeliveryManByLicensePlate() throws Exception {
        given(service.findDeliverymanByLicensePlate(deliverymanToCustomerDto.licensePlate())).willReturn(deliverymanToCustomerDto);

        mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON)
                        .param("deliverymanInformation",  deliverymanToCustomerDto.licensePlate())
                        .param("typeInformation", "license"))
                .andExpect(status().isOk())
                .andExpect(content().string("Entregador deletado com sucesso"))
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andDo(print())
                .andReturn();

        verify(service,times(1)).deleteDeliveryman(deliverymanToCustomerDto.licensePlate(), "license");
    }
}