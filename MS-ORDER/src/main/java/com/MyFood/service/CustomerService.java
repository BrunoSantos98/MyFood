package com.MyFood.service;

import com.MyFood.dto.AddressDto;
import com.MyFood.dto.CustomerDto;
import com.MyFood.model.OrderModel;

import java.util.Set;

public interface CustomerService {
    CustomerDto createnewCustomer(CustomerDto customerDto);
    CustomerDto getCustomerByCpf(String cpf);
    CustomerDto getCustomerByEmail(String email);
    Set<AddressDto> getCustomerAddresses(String cpf);
    CustomerDto updateUserData(CustomerDto customerDto, String cpf);
    CustomerDto updateUserAddress(AddressDto address, String cpf);
    CustomerDto addOrderInCustomer(OrderModel orders, String cpf);
    void deleteCustomer(String cpf);
}
