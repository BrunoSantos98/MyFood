package com.MyFood.controller;

import com.MyFood.dto.AddressDto;
import com.MyFood.dto.CustomerDto;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface CustomerController {
    ResponseEntity<CustomerDto> createnewCustomer(CustomerDto customerDto);
    ResponseEntity<CustomerDto> getCustomerByCpf(String cpf);
    ResponseEntity<CustomerDto> getCustomerByEmail(String email);
    ResponseEntity<Set<AddressDto>> getCustomerAddresses(String cpf);
    ResponseEntity<CustomerDto> updateUserData(CustomerDto customerDto, String cpf);
    ResponseEntity<CustomerDto> updateUserAddress(AddressDto address, String cpf);
    ResponseEntity<String> deleteCustomer(String cpf);
}
