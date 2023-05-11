package com.MyFood.service.implementation;

import com.MyFood.dto.DeliverymanDto;
import com.MyFood.exceptions.ObjectConflictException;
import com.MyFood.exceptions.ObjectRequiredNotFoundException;
import com.MyFood.model.Deliveryman;
import com.MyFood.repository.DeliverymanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
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

    @Test
    @DisplayName("Encontra um entregador pelo ID")
    void shouldBeFindDeliverymanById(){
        given(repository.findById(deliveryman.getId())).willReturn(Optional.of(deliveryman));

        Deliveryman deliverymanResult = service.findDeliverymanById(deliveryman.getId());

        verify(repository,times(1)).findById(deliveryman.getId());
        assertEquals(deliveryman,deliverymanResult);
    }

    @Test
    @DisplayName("Lança exception ao tentar encontrar entregador pelo ID")
    void shouldBeThrowExceptionInFindDeliverymanById(){
        given(repository.findById(deliveryman.getId())).willReturn(Optional.empty());

        ObjectRequiredNotFoundException e = assertThrows(ObjectRequiredNotFoundException.class,
                () -> service.findDeliverymanById(deliveryman.getId()));

        verify(repository,times(1)).findById(deliveryman.getId());
        assertEquals("Entregador nao encontrado através do ID informado", e.getMessage());
    }


    @Test
    @DisplayName("Encontra um entregador pelo CPF")
    void shouldBeFindDeliverymanByCpf(){
        given(repository.existsByCpf(deliverymanDto.cpf())).willReturn(true);
        given(repository.findByCpf(deliverymanDto.cpf())).willReturn(deliveryman);

         service.findDeliverymanByCpf(deliverymanDto.cpf());

        verify(repository,times(1)).findByCpf(deliverymanDto.cpf());
        verify(repository,times(1)).existsByCpf(deliverymanDto.cpf());
    }

    @Test
    @DisplayName("Lança exception ao tentar encontrar entregador pelo CPF")
    void shouldBeThrowExceptionInFindDeliverymanByCpf(){
        given(repository.existsByCpf(deliverymanDto.cpf())).willReturn(false);

        ObjectRequiredNotFoundException e = assertThrows(ObjectRequiredNotFoundException.class,
                () -> service.findDeliverymanByCpf(deliverymanDto.cpf()));

        verify(repository,times(1)).existsByCpf(deliverymanDto.cpf());
        verify(repository,times(0)).findByCpf(deliverymanDto.cpf());
        assertEquals("Entregador nao encontrado através do CPF informado", e.getMessage());
    }

    @Test
    @DisplayName("Encontra um entregador pelo LicensePlate")
    void shouldBeFindDeliverymanByLicensePlate(){
        given(repository.existsByLicensePlate(deliverymanDto.licensePlate())).willReturn(true);
        given(repository.findByLicensePlate(deliverymanDto.licensePlate())).willReturn(deliveryman);

        service.findDeliverymanByLicensePlate(deliverymanDto.licensePlate());

        verify(repository,times(1)).existsByLicensePlate(deliverymanDto.licensePlate());
        verify(repository,times(1)).findByLicensePlate(deliverymanDto.licensePlate());
    }

    @Test
    @DisplayName("Lança exception ao tentar encontrar entregador pela placa de veiculo")
    void shouldBeThrowExceptionInFindDeliverymanByLicensePlate(){
        given(repository.existsByLicensePlate(deliverymanDto.licensePlate())).willReturn(false);

        ObjectRequiredNotFoundException e = assertThrows(ObjectRequiredNotFoundException.class,
                () -> service.findDeliverymanByLicensePlate(deliverymanDto.licensePlate()));

        verify(repository,times(1)).existsByLicensePlate(deliverymanDto.licensePlate());
        verify(repository,times(0)).findByLicensePlate(deliverymanDto.licensePlate());
        assertEquals("Entregador nao encontrado através da placa informada", e.getMessage());
    }

    @Test
    @DisplayName("Deleta um entregador pelo ID")
    void shouldBeDeleteDeliverymanById(){
        given(repository.findById(deliveryman.getId())).willReturn(Optional.of(deliveryman));

        service.deleteDeliveryman(String.valueOf(deliveryman.getId()), "id");

        verify(repository,times(1)).delete(deliveryman);
    }

    @Test
    @DisplayName("Deleta um entregador pelo CPF")
    void shouldBeDeleteDeliverymanByCpf(){
        given(repository.existsByCpf(deliverymanDto.cpf())).willReturn(true);
        given(repository.findByCpf(deliverymanDto.cpf())).willReturn(deliveryman);

        service.deleteDeliveryman(deliverymanDto.cpf(), "cpf");

        verify(repository,times(1)).existsByCpf(deliverymanDto.cpf());
        verify(repository,times(1)).delete(deliveryman);
    }

    @Test
    @DisplayName("Deleta um entregador pelo ID")
    void shouldBeDeleteDeliverymanByLicensePlate(){
        given(repository.existsByLicensePlate(deliverymanDto.licensePlate())).willReturn(true);
        given(repository.findByLicensePlate(deliverymanDto.licensePlate())).willReturn(deliveryman);

        service.deleteDeliveryman(deliverymanDto.licensePlate(), "license");

        verify(repository,times(1)).existsByLicensePlate(deliverymanDto.licensePlate());
        verify(repository,times(1)).deleteByLicensePlate(deliverymanDto.licensePlate());
    }

    @Test
    @DisplayName("Erro ao tentar deletar entregador pelo ID")
    void shouldBeThrowExceptionInDeleteDeliverymanById(){

        ObjectRequiredNotFoundException e = assertThrows(ObjectRequiredNotFoundException.class,
                () -> service.deleteDeliveryman(deliverymanDto.licensePlate(), "Erradin"));

        assertEquals("Tipo de informacao invalida" ,e.getMessage());
    }
}