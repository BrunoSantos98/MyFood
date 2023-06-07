package com.MyFood.service.implementation;

import com.MyFood.dto.AddressDto;
import com.MyFood.dto.CustomerDto;
import com.MyFood.exceptions.ObjectConflictException;
import com.MyFood.exceptions.ObjectRequiredNotFoundException;
import com.MyFood.model.AddressModel;
import com.MyFood.model.CustomerModel;
import com.MyFood.model.OrderModel;
import com.MyFood.repository.CustomerRepository;
import com.MyFood.service.AddressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
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
    @Mock
    private AddressService addressService;
    @InjectMocks
    private CustomerServiceImplementation implementation;
    private AddressModel addressModel = new AddressModel(UUID.randomUUID(), "01010-001", "rua 1", (short) 2, "Complemento", "bairro", "SP");
    private AddressDto address = new AddressDto("01010-001", "rua 1", (short) 2, "Complemento", "bairro", "SP");
    private CustomerModel customer = new CustomerModel(UUID.randomUUID(), "name", "000.000.000-00", "email@email.com", "(11) 93255-6987", Set.of(addressModel), new HashSet<>());
    private CustomerDto customerDto = new CustomerDto("name", "000.000.000-00", "email@email.com", "(11) 93255-6987", Set.of(address), new HashSet<>());

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

    @Test
    @DisplayName("Atualiza os dados de um usuario")
    void shouldBeUpdateCustomerData(){
        CustomerModel customer002 = new CustomerModel(UUID.randomUUID(), "nome de verdade", "000.000.000-01", "emailteste@email.com", "(11) 93255-0000", new HashSet<>(), null);
        CustomerDto customerDto002 = new CustomerDto("nome de verdade", "000.000.000-01", "emailteste@email.com", "(11) 93255-0000", new HashSet<>(), null);

        given(repository.existsByCpf(customerDto.cpf())).willReturn(true);
        given(repository.existsByCpf(customerDto002.cpf())).willReturn(false);
        given(repository.existsByEmail(customerDto002.email())).willReturn(false);
        given(repository.findByCpf(customerDto.cpf())).willReturn(customer);
        given(repository.save(any(CustomerModel.class))).willReturn(customer002);

        CustomerDto dtoTest = implementation.updateUserData(customerDto002, customerDto.cpf());

        verify(repository,times(1)).existsByCpf(customerDto.cpf());
        verify(repository,times(1)).existsByCpf(customerDto002.cpf());
        verify(repository,times(1)).existsByEmail(customerDto002.email());
        verify(repository,times(1)).findByCpf(customerDto.cpf());
        verify(repository,times(1)).save(any(CustomerModel.class));
        assertEquals(dtoTest.name(), customerDto002.name());
        assertEquals(dtoTest.email(), customerDto002.email());
        assertEquals(dtoTest.phone(), customerDto002.phone());
        assertEquals(dtoTest.cpf(), customerDto002.cpf());
    }

    @Test
    @DisplayName("Lança excessão ao atualizar os dados de um usuario")
    void shouldBeThrowExceptionWhenUpdateCustomerData(){
        CustomerModel customer002 = new CustomerModel(UUID.randomUUID(), "nome de verdade", "000.000.000-01", "emailteste@email.com", "(11) 93255-0000", null, null);
        CustomerDto customerDto002 = new CustomerDto("nome de verdade", "000.000.000-01", "emailteste@email.com", "(11) 93255-0000", null, null);

        given(repository.existsByCpf(customerDto.cpf())).willReturn(false);
        given(repository.existsByCpf(customerDto002.cpf())).willReturn(false);
        given(repository.existsByEmail(customerDto002.email())).willReturn(false);

        ObjectRequiredNotFoundException e = assertThrows(ObjectRequiredNotFoundException.class,
                () -> implementation.updateUserData(customerDto002, customerDto.cpf()));

        verify(repository,times(1)).existsByCpf(customerDto.cpf());
        verify(repository,times(1)).existsByCpf(customerDto002.cpf());
        verify(repository,times(1)).existsByEmail(customerDto002.email());
        verify(repository,times(0)).findByCpf(customerDto.cpf());
        verify(repository,times(0)).save(customer002);
        assertEquals("Cpf informado para busca não encontrado na base de dados" ,e.getMessage());
    }

    @Test
    @DisplayName("Atualiza endereço do cliente")
    void shouldBeUpdateCustomerAddress(){
        given(repository.existsByCpf(customerDto.cpf())).willReturn(true);
        given(repository.findByCpf(customerDto.cpf())).willReturn(customer);
        given(addressService.getNewAddress(address)).willReturn(address);
        given(addressService.getAddressModelByInformations(address.cep(), address.logradouro(), address.number())).willReturn(addressModel);
        given(repository.save(any(CustomerModel.class))).willReturn(customer);

        implementation.updateUserAddress(address, customerDto.cpf());

        verify(repository,times(1)).existsByCpf(customerDto.cpf());
        verify(repository,times(1)).findByCpf(customerDto.cpf());
        verify(repository,times(1)).save(any(CustomerModel.class));
        verify(addressService,times(1)).getNewAddress(address);
        verify(addressService,times(1)).getAddressModelByInformations(address.cep(), address.logradouro(), address.number());
    }

    @Test
    @DisplayName("Lança excessão ao tentar atualizar  o endereço do cliente")
    void shouldBeThrowExceptionOnUpdateCustomerAddress(){
        AddressDto address = new AddressDto("01010-001", "rua 1", (short) 2, "Complemento", "bairro", "SP");
        given(repository.existsByCpf(customerDto.cpf())).willReturn(false);

        ObjectRequiredNotFoundException e = assertThrows(ObjectRequiredNotFoundException.class,
                () -> implementation.updateUserAddress(address, customerDto.cpf()));

        verify(repository,times(1)).existsByCpf(customerDto.cpf());
        verify(repository,times(0)).findByCpf(customerDto.cpf());
        verify(addressService,times(0)).getNewAddress(address);
        assertEquals("Cpf informado para busca não encontrado na base de dados" ,e.getMessage());
    }

    @Test
    @DisplayName("Adiicona uma nova order no Customer")
    void shouldBeAddOrderInCustomer(){
        given(repository.existsByCpf(customerDto.cpf())).willReturn(true);
        given(repository.findByCpf(customerDto.cpf())).willReturn(customer);

        implementation.addOrderInCustomer(new OrderModel(),customerDto.cpf());

        verify(repository,times(1)).existsByCpf(customerDto.cpf());
        verify(repository,times(1)).findByCpf(customerDto.cpf());
    }

    @Test
    @DisplayName("Adiicona uma nova order no Customer")
    void shouldBeThrowExceptionWhenTryAddOrderInCustomer(){
        given(repository.existsByCpf(customerDto.cpf())).willReturn(false);

        ObjectRequiredNotFoundException e = assertThrows(ObjectRequiredNotFoundException.class,
                () -> implementation.addOrderInCustomer(new OrderModel(),customerDto.cpf()));

        verify(repository,times(1)).existsByCpf(customerDto.cpf());
        verify(repository,times(0)).findByCpf(customerDto.cpf());
        assertEquals("Cpf informado para busca não encontrado na base de dados", e.getMessage());
    }

    @Test
    @DisplayName("Deleta um usuario")
    void shouldBeDeleteACustomer(){
        given(repository.existsByCpf(customerDto.cpf())).willReturn(true);
        given(repository.findByCpf(customerDto.cpf())).willReturn(customer);

        implementation.deleteCustomer(customerDto.cpf());

        verify(repository,times(1)).existsByCpf(customerDto.cpf());
        verify(repository,times(1)).findByCpf(customerDto.cpf());
        verify(repository,times(1)).delete(customer);
    }

    @Test
    @DisplayName("Lança exception ao deletar um usuario")
    void shouldBeThrowExceptionToDeleteACustomer(){
        given(repository.existsByCpf(customerDto.cpf())).willReturn(false);

        ObjectRequiredNotFoundException e = assertThrows(ObjectRequiredNotFoundException.class,
                () -> implementation.deleteCustomer(customerDto.cpf()));

        verify(repository,times(1)).existsByCpf(customerDto.cpf());
        verify(repository,times(0)).findByCpf(customerDto.cpf());
        verify(repository,times(0)).delete(customer);
        assertEquals("Cpf informado para busca não encontrado na base de dados" ,e.getMessage());
    }
}