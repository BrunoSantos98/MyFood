package com.MyFood.service.implementation;

import com.MyFood.dto.DeliverymanDto;
import com.MyFood.exceptions.ObjectConflictException;
import com.MyFood.model.Deliveryman;
import com.MyFood.repository.DeliverymanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeliverymanServiceImplementationTest {

    @Mock
    private DeliverymanRepository repository;
    @InjectMocks
    private DeliverymanServiceImplementation service;

    private Deliveryman deliveryman = new Deliveryman(UUID.randomUUID(), "Deliveryman", "000.000.000-00", "(11)93255-6987","Honda Fan 150", "fee-600", "Vermelho");
    private DeliverymanDto deliverymanDto = new DeliverymanDto("Deliveryman", "000.000.000-00", "(11)93255-6987","Honda Fan 150", "fee-600", "Vermelho");

    @Test
    @DisplayName("Cria um novo Deliveryman")
    void shouldBeCreateNewDeliveryman(){
        given(repository.existsByCpf(deliverymanDto.cpf())).willReturn(false);
        given(repository.existsByLicensePlate(deliverymanDto.licensePlate())).willReturn(false);
        given(repository.save(any(Deliveryman.class))).willReturn(deliveryman);

        service.createNewDeliveryman(deliverymanDto);

        verify(repository,times(1)).existsByCpf(deliverymanDto.cpf());
        verify(repository,times(1)).existsByLicensePlate(deliverymanDto.licensePlate());
        verify(repository,times(1)).save(any(Deliveryman.class));
    }

    @Test
    @DisplayName("Ao criar novo entregador CPF ja existe")
    void shouldBeThrowCpfExceptionInCreateNewDeliveryman(){
        given(repository.existsByCpf(deliverymanDto.cpf())).willReturn(true);

        ObjectConflictException e = assertThrows(ObjectConflictException.class,
                () ->service.createNewDeliveryman(deliverymanDto));

        verify(repository,times(1)).existsByCpf(deliverymanDto.cpf());
        verify(repository,times(0)).existsByLicensePlate(deliverymanDto.licensePlate());
        verify(repository,times(0)).save(any(Deliveryman.class));
        assertEquals("Cpf ja cadastrado para outro Entregador",e.getMessage());
    }

    @Test
    @DisplayName("Ao criar novo entregador placa de veiculo ja existe")
    void shouldBeThrowLicensePlateExceptionInCreateNewDeliveryman(){
        given(repository.existsByCpf(deliverymanDto.cpf())).willReturn(false);
        given(repository.existsByLicensePlate(deliverymanDto.licensePlate())).willReturn(true);

        ObjectConflictException e = assertThrows(ObjectConflictException.class,
                () ->service.createNewDeliveryman(deliverymanDto));

        verify(repository,times(1)).existsByCpf(deliverymanDto.cpf());
        verify(repository,times(1)).existsByLicensePlate(deliverymanDto.licensePlate());
        verify(repository,times(0)).save(any(Deliveryman.class));
        assertEquals("Placa de veiculo ja cadastrada para outro entregador",e.getMessage());
    }
}