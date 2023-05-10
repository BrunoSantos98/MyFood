package com.MyFood.service.implementation;

import com.MyFood.dto.AddressDto;
import com.MyFood.exceptions.ObjectRequiredNotFoundException;
import com.MyFood.model.AddressModel;
import com.MyFood.repository.AddressRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplementationsTest {

    @Mock
    private AddressRepository repository;
    @InjectMocks
    private AddressServiceImplementations service;

    private AddressDto addressDto = new AddressDto("01.001-000", "Passagem da fé", (short)12, "Lado Impar", "São francisco", "SP");
    private AddressModel address = new AddressModel(UUID.randomUUID(), "01.001-000", "Passagem da fé", (short)12, "Lado Impar", "São francisco", "SP");

    @Test
    @DisplayName("Cadastra um endereço novo")
    void shouldBeCreateNewAddress(){
        given(repository.existsByCepAndLogradouroAndNumber(addressDto.cep(), addressDto.logradouro(), addressDto.number())).willReturn(false);
        given(repository.save(any(AddressModel.class))).willReturn(address);

        service.getNewAddress(addressDto);

        verify(repository,times(1)).existsByCepAndLogradouroAndNumber(addressDto.cep(), addressDto.logradouro(), addressDto.number());
        verify(repository,times(1)).save(any(AddressModel.class));
    }

    @Test
    @DisplayName("Retorna endereço existente como um endereço novo")
    void shouldBeReturnAdrressAsNew(){
        given(repository.existsByCepAndLogradouroAndNumber(addressDto.cep(), addressDto.logradouro(), addressDto.number())).willReturn(true);

        service.getNewAddress(addressDto);

        verify(repository,times(1)).existsByCepAndLogradouroAndNumber(addressDto.cep(), addressDto.logradouro(), addressDto.number());
        verify(repository,times(0)).save(any(AddressModel.class));
    }

    @Test
    @DisplayName("Retorna um endedreço atraves das informações")
    void shouldBeGetAddressByInformations(){
        given(repository.existsByCepAndLogradouroAndNumber(addressDto.cep(), addressDto.logradouro(), addressDto.number())).willReturn(true);
        given(repository.findByCepAndLogradouroAndNumber(addressDto.cep(), addressDto.logradouro(), addressDto.number())).willReturn(address);

        service.getAddressByInformations(addressDto.cep(), addressDto.logradouro(), addressDto.number());

        verify(repository,times(1)).existsByCepAndLogradouroAndNumber(addressDto.cep(), addressDto.logradouro(), addressDto.number());
        verify(repository,times(1)).findByCepAndLogradouroAndNumber(addressDto.cep(), addressDto.logradouro(), addressDto.number());
    }

    @Test
    @DisplayName("Retorna uma exception ao tentar dar get em um endereço")
    void shouldBeThrowExceptionGetAddressByInformations(){
        given(repository.existsByCepAndLogradouroAndNumber(addressDto.cep(), addressDto.logradouro(), addressDto.number())).willReturn(false);

        ObjectRequiredNotFoundException e = assertThrows(ObjectRequiredNotFoundException.class,
                () ->service.getAddressByInformations(addressDto.cep(), addressDto.logradouro(), addressDto.number()));

        verify(repository,times(1)).existsByCepAndLogradouroAndNumber(addressDto.cep(), addressDto.logradouro(), addressDto.number());
        assertEquals("Endereço não encontrado com base nas informações solicitadas.",e.getMessage());
    }

    @Test
    @DisplayName("Deleta um Endereço")
    void shouldBeDeleteAddress(){
        given(repository.existsByCepAndLogradouroAndNumber(addressDto.cep(), addressDto.logradouro(), addressDto.number())).willReturn(true);
        given(repository.findByCepAndLogradouroAndNumber(addressDto.cep(), addressDto.logradouro(), addressDto.number())).willReturn(address);

        service.deleteAddress(addressDto);

        verify(repository,times(1)).existsByCepAndLogradouroAndNumber(addressDto.cep(), addressDto.logradouro(), addressDto.number());
        verify(repository,times(1)).delete(address);
    }
}