package com.MyFood.service.implementation;

import com.MyFood.dto.AddressDto;
import com.MyFood.dto.CustomerDto;
import com.MyFood.exceptions.ObjectConflictException;
import com.MyFood.exceptions.ObjectRequiredNotFoundException;
import com.MyFood.model.AddressModel;
import com.MyFood.model.CustomerModel;
import com.MyFood.repository.CustomerRepository;
import com.MyFood.service.AddressService;
import com.MyFood.service.CustomerService;

import java.util.Set;
import java.util.stream.Collectors;

public class CustomerServiceImplementation implements CustomerService {

    private final CustomerRepository repository;
    private final AddressService addressService;

    public CustomerServiceImplementation(CustomerRepository repository, AddressService addressService) {
        this.repository = repository;
        this.addressService = addressService;
    }

    private boolean verifyEmailExists(String email) {
        return repository.existsByEmail(email);
    }

    private boolean verifyCpfExists(String cpf) {
        return repository.existsByCpf(cpf);
    }

    private CustomerDto getCustomerDtoByCustomerModel(CustomerModel customerModel){
        return new CustomerDto(customerModel.getName(), customerModel.getCpf(), customerModel.getEmail(),
                customerModel.getPhone(), customerModel.getAddress(), customerModel.getOrders());
    }

    private AddressDto addressModelToAddressDto(AddressModel addressModel) {
        return new AddressDto(addressModel.getCep(), addressModel.getLogradouro(), addressModel.getNumber(),
                addressModel.getComplemento(), addressModel.getBairro(), addressModel.getUf());
    }

    @Override
    public CustomerDto createnewCustomer(CustomerDto customerDto) {
        if(verifyCpfExists(customerDto.cpf())){
            throw new ObjectConflictException("Usuario ja cadastrado na base de dados pelo cpf informado");
        }else if(verifyEmailExists(customerDto.email())){
            throw new ObjectConflictException("Usuario ja cadastrado na base de dados pelo email informado");
        }else{
            return getCustomerDtoByCustomerModel(repository.save(new CustomerModel(null, customerDto.name(),
                    customerDto.cpf(), customerDto.email(), customerDto.phone(),
                    customerDto.address(), customerDto.orders())));
        }
    }

    @Override
    public CustomerDto getCustomerByCpf(String cpf) {
        if(verifyCpfExists(cpf)){
            return getCustomerDtoByCustomerModel(repository.findByCpf(cpf));
        }else{
            throw new ObjectRequiredNotFoundException("O CPF informado não esta cadastrado na base de dados");
        }
    }

    @Override
    public CustomerDto getCustomerByEmail(String email) {
        if(verifyEmailExists(email)){
            return getCustomerDtoByCustomerModel(repository.findByEmail(email));
        }else{
            throw new ObjectRequiredNotFoundException("O email informado não esta cadastrado na base de dados");
        }
    }

    @Override
    public Set<AddressDto> getCustomerAddresses(String cpf) {
        CustomerDto customerDto = getCustomerByCpf(cpf);
        return customerDto.address().stream().map(this::addressModelToAddressDto).collect(Collectors.toSet());
    }
}
