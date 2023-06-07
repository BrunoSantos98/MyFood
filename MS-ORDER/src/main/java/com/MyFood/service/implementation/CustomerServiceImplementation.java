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
import com.MyFood.service.CustomerService;
import jakarta.transaction.Transactional;
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

    private void verifyDuplicateInformations(CustomerDto customerDto){
        if(repository.existsByCpf(customerDto.cpf())){
            throw new ObjectConflictException("Usuario ja cadastrado na base de dados pelo cpf informado");
        }else if(repository.existsByEmail(customerDto.email())){
            throw new ObjectConflictException("Usuario ja cadastrado na base de dados pelo email informado");
        }
    }

    private CustomerModel transformCustomerDtoInCustomerModel(CustomerDto customerDto) {
        CustomerModel model = new CustomerModel(null, customerDto.name(), customerDto.cpf(), customerDto.email(),
                customerDto.phone(), new HashSet<>(), new HashSet<>());

        if(!customerDto.address().isEmpty()){
            model.setAddress(customerDto.address().stream().map(
                    addressDto -> addressDtoToAddressModel(addressDto)).collect(Collectors.toSet()));
        }
        if(!customerDto.orders().isEmpty()){
            model.setOrders(customerDto.orders());
        }
        return model;
    }

    private AddressModel addressDtoToAddressModel(AddressDto addressDto) {
        addressService.getNewAddress(addressDto);
        return addressService.getAddressModelByInformations(addressDto.cep(), addressDto.logradouro(), addressDto.number());
    }

    private CustomerDto transformCustomerModelinCustumerDto(CustomerModel customerModel) {
        CustomerDto dto = new CustomerDto(customerModel.getName(), customerModel.getCpf(), customerModel.getEmail(),
                customerModel.getPhone(), new HashSet<>(), customerModel.getOrders());

        Object nulltest = customerModel.getAddress();
        if(nulltest != null){
            dto.address().addAll(customerModel.getAddress().stream().map(
                    addressModel -> addressService.getAddressByInformations(addressModel.getCep(),
                            addressModel.getLogradouro(), addressModel.getNumber())).collect(Collectors.toSet()));
        }
        return dto;
    }

    private boolean verifyCustomerExistsByCpf(String cpf){
        return repository.existsByCpf(cpf);
    }

    private boolean verifyCustomerExistsByEmail(String email){
        return repository.existsByEmail(email);
    }

    @Override
    @Transactional
    public CustomerDto createnewCustomer(CustomerDto customerDto) {
        verifyDuplicateInformations(customerDto);
        CustomerModel customerModel = repository.save(transformCustomerDtoInCustomerModel(customerDto));
        return transformCustomerModelinCustumerDto(customerModel);
    }

    @Override
    public CustomerDto getCustomerByCpf(String cpf) {
        if(verifyCustomerExistsByCpf(cpf)){
            return transformCustomerModelinCustumerDto(repository.findByCpf(cpf));
        }else{
            throw new ObjectRequiredNotFoundException("O CPF informado não esta cadastrado na base de dados");
        }
    }

    @Override
    public CustomerDto getCustomerByEmail(String email) {
        if(verifyCustomerExistsByEmail(email)){
            return transformCustomerModelinCustumerDto(repository.findByEmail(email));
        }else{
            throw new ObjectRequiredNotFoundException("O email informado não esta cadastrado na base de dados");
        }
    }

    @Override
    public Set<AddressDto> getCustomerAddresses(String cpf) {
        return getCustomerByCpf(cpf).address();
    }

    @Override
    @Transactional
    public CustomerDto updateUserData(CustomerDto customerDto, String cpf) {
        verifyDuplicateInformations(customerDto);
        if(verifyCustomerExistsByCpf(cpf)){
            CustomerModel model = repository.findByCpf(cpf);
            model.setName(customerDto.name());
            model.setCpf(customerDto.cpf());
            model.setEmail(customerDto.email());
            model.setPhone(customerDto.phone());
            return transformCustomerModelinCustumerDto(repository.save(model));
        }else{
            throw new ObjectRequiredNotFoundException("Cpf informado para busca não encontrado na base de dados");
        }
    }

    @Override
    @Transactional
    public CustomerDto updateUserAddress(AddressDto address, String cpf) {
        if(verifyCustomerExistsByCpf(cpf)){
            CustomerModel model = repository.findByCpf(cpf);
            addressService.getNewAddress(address);
            model.getAddress().add(
                    addressService.getAddressModelByInformations(
                            address.cep(), address.logradouro(), address.number()));
            return transformCustomerModelinCustumerDto(repository.save(model));
        }else{
            throw new ObjectRequiredNotFoundException("Cpf informado para busca não encontrado na base de dados");
        }
    }

    @Override
    public CustomerDto addOrderInCustomer(OrderModel order, String cpf) {
        if(verifyCustomerExistsByCpf(cpf)){
            CustomerModel model = repository.findByCpf(cpf);
            model.getOrders().add(order);
            return transformCustomerModelinCustumerDto(repository.save(model));
        }else{
            throw new ObjectRequiredNotFoundException("Cpf informado para busca não encontrado na base de dados");
        }
    }

    @Override
    @Transactional
    public void deleteCustomer(String cpf) {
        if(verifyCustomerExistsByCpf(cpf)){
            repository.delete(repository.findByCpf(cpf));
        }else{
            throw new ObjectRequiredNotFoundException("Cpf informado para busca não encontrado na base de dados");
        }
    }
}
