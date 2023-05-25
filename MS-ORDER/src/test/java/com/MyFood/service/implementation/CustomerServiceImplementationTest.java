package com.MyFood.service.implementation;

import com.MyFood.dto.CustomerDto;
import com.MyFood.exceptions.ObjectConflictException;
import com.MyFood.exceptions.ObjectRequiredNotFoundException;
import com.MyFood.model.CustomerModel;
import com.MyFood.repository.CustomerRepository;
import com.MyFood.service.AddressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplementationTest {

    @Mock
    private CustomerRepository repository;
//    @Mock
//    private AddressService AddressService;
    @InjectMocks
    private CustomerServiceImplementation implementation;

    //private AddressModel address = new AddressModel(UUID.randomUUID(), "01010-001", "rua 1", (short) 2, "Complemento", "bairro", "SP");
    private CustomerModel customer = new CustomerModel(UUID.randomUUID(), "name", "000.000.000-00", "email@email.com", "(11) 93255-6987", new HashSet<>(), new HashSet<>());
    private CustomerDto customerDto = new CustomerDto("name", "000.000.000-00", "email@email.com", "(11) 93255-6987", new HashSet<>(), new HashSet<>());

    @Test
    @DisplayName("Cria um Novo cliente")
    void shouldBeCreateNewCustomer(){
        given(repository.existsByCpf(customerDto.cpf())).willReturn(false);
        given(repository.existsByEmail(customerDto.email())).willReturn(false);
        given(repository.save(any(CustomerModel.class))).willReturn(customer);

        implementation.createnewCustomer(customerDto);

        verify(repository,times(1)).existsByCpf(customerDto.cpf());
        verify(repository,times(1)).existsByEmail(customerDto.email());
        verify(repository,times(1)).save(any(CustomerModel.class));
    }

    @Test
    @DisplayName("Erro de CPF ao criar novo cliente")
    void shouldBeThrowCpfExceptionByCreateNewCustomer(){
        given(repository.existsByCpf(customerDto.cpf())).willReturn(true);

        ObjectConflictException e = assertThrows(ObjectConflictException.class,
                () -> implementation.createnewCustomer(customerDto));

        assertEquals("Usuario ja cadastrado na base de dados pelo cpf informado", e.getMessage());
        verify(repository,times(1)).existsByCpf(customerDto.cpf());
        verify(repository,times(0)).existsByEmail(customerDto.email());
        verify(repository,times(0)).save(any(CustomerModel.class));
    }

    @Test
    @DisplayName("Erro de Email ao criar novo cliente")
    void shouldBeThrowEmailExceptionByCreateNewCustomer(){
        given(repository.existsByCpf(customerDto.cpf())).willReturn(false);
        given(repository.existsByEmail(customerDto.email())).willReturn(true);

        ObjectConflictException e = assertThrows(ObjectConflictException.class,
                () -> implementation.createnewCustomer(customerDto));

        assertEquals("Usuario ja cadastrado na base de dados pelo email informado", e.getMessage());
        verify(repository,times(1)).existsByCpf(customerDto.cpf());
        verify(repository,times(1)).existsByEmail(customerDto.email());
        verify(repository,times(0)).save(any(CustomerModel.class));
    }

    @Test
    @DisplayName("Encontra cliente atraves do CPF")
    void ShouldBeFindCustomerByCpf(){
        given(repository.existsByCpf(customerDto.cpf())).willReturn(true);
        given(repository.findByCpf(customerDto.cpf())).willReturn(customer);

        implementation.getCustomerByCpf(customerDto.cpf());

        verify(repository,times(1)).existsByCpf(customerDto.cpf());
        verify(repository,times(1)).findByCpf(customerDto.cpf());
    }

    @Test
    @DisplayName("Lança Exception ao tentar encontrar cliente atraves do CPF")
    void ShouldBeThrowExceptionByFindCustomerByCpf(){
        given(repository.existsByCpf(customerDto.cpf())).willReturn(false);

        ObjectRequiredNotFoundException e = assertThrows(ObjectRequiredNotFoundException.class,
                () -> implementation.getCustomerByCpf(customerDto.cpf()));

        assertEquals("O CPF informado não esta cadastrado na base de dados" ,e.getMessage());
        verify(repository,times(1)).existsByCpf(customerDto.cpf());
    }

    @Test
    @DisplayName("Encontra cliente atraves do Email")
    void ShouldBeFindCustomerByEmail(){
        given(repository.existsByEmail(customerDto.email())).willReturn(true);
        given(repository.findByEmail(customerDto.email())).willReturn(customer);

        implementation.getCustomerByEmail(customerDto.email());

        verify(repository,times(1)).existsByEmail(customerDto.email());
        verify(repository,times(1)).findByEmail(customerDto.email());
    }

    @Test
    @DisplayName("Lança Exception ao tentar encontrar cliente atraves do Email")
    void ShouldBeThrowExceptionByFindCustomerByEmail(){
        given(repository.existsByEmail(customerDto.email())).willReturn(false);

        ObjectRequiredNotFoundException e = assertThrows(ObjectRequiredNotFoundException.class,
                () -> implementation.getCustomerByEmail(customerDto.email()));

        assertEquals("O email informado não esta cadastrado na base de dados" ,e.getMessage());
        verify(repository,times(1)).existsByEmail(customerDto.email());
    }

    @Test
    @DisplayName("Retorna todos os endereços de um usuario")
    void shouldBeReturnAllAddressesCadastred(){
        given(repository.existsByCpf(customerDto.cpf())).willReturn(true);
        given(repository.findByCpf(customerDto.cpf())).willReturn(customer);

        implementation.getCustomerAddresses(customerDto.cpf());

        verify(repository,times(1)).existsByCpf(customerDto.cpf());
        verify(repository,times(1)).findByCpf(customerDto.cpf());
    }
}