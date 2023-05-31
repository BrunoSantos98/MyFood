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
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
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

    private boolean checkIfEmailDoesNotExist(String email) {
        if(verifyEmailExists(email)){
            throw new ObjectConflictException("Usuario ja cadastrado na base de dados pelo email informado");
        }else{
            return true;
        }
    }

    private boolean checkIfCpfDoesNotExist(String cpf) {
        if(verifyCpfExists(cpf)){
            throw new ObjectConflictException("Usuario ja cadastrado na base de dados pelo cpf informado");
        }else{
            return true;
        }
    }

    private CustomerDto getCustomerDtoByCustomerModel(CustomerModel customerModel){
        Set<AddressDto> addressDto = new HashSet<>();
        addressDto.addAll(customerModel.getAddress().stream().map(this::addressModelToAddressDto).collect(Collectors.toList()));
        return new CustomerDto(customerModel.getName(), customerModel.getCpf(), customerModel.getEmail(),
                customerModel.getPhone(), addressDto, customerModel.getOrders());
    }

    private AddressDto addressModelToAddressDto(AddressModel addressModel) {
        return new AddressDto(addressModel.getCep(), addressModel.getLogradouro(), addressModel.getNumber(),
                addressModel.getComplemento(), addressModel.getBairro(), addressModel.getUf());
    }

    private CustomerModel getCustomerModelByCpf(String cpf){
        if(verifyCpfExists(cpf)){
            return repository.findByCpf(cpf);
        }else{
            throw new ObjectRequiredNotFoundException("Cpf informado para busca não encontrado na base de dados");
        }
    }

    @Transactional
    @Override
    public CustomerDto createnewCustomer(CustomerDto customerDto) {
        checkIfCpfDoesNotExist(customerDto.cpf());
        checkIfEmailDoesNotExist(customerDto.email());

        return getCustomerDtoByCustomerModel(repository.save(new CustomerModel(null, customerDto.name(),
                customerDto.cpf(), customerDto.email(), customerDto.phone(), null,
                customerDto.orders())));
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
        return customerDto.address();
    }

    @Transactional
    @Override
    public CustomerDto updateUserData(CustomerDto customerDto, String cpf) {
        checkIfCpfDoesNotExist(customerDto.cpf());
        checkIfEmailDoesNotExist(customerDto.email());

        CustomerModel model = getCustomerModelByCpf(cpf);
        BeanUtils.copyProperties(customerDto, model);
        return getCustomerDtoByCustomerModel(repository.save(model));
    }

    @Override
    public CustomerDto updateUserAddress(AddressDto address, String cpf) {
            CustomerModel customer = getCustomerModelByCpf(cpf);
            addressService.getNewAddress(address);
            AddressModel addressModel = addressService.getAddressModelByInformations(address.cep(), address.logradouro(), address.number());
            customer.getAddress().add(addressModel);
            return getCustomerDtoByCustomerModel(repository.save(customer));
    }

    @Override
    public void deleteCustomer(String cpf) {
        repository.delete(getCustomerModelByCpf(cpf));
    }
}
