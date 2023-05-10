package com.MyFood.controller;

import com.MyFood.dto.AddressDto;
import org.springframework.http.ResponseEntity;

public interface AddressController {

    ResponseEntity<AddressDto> getNewAddress(AddressDto address);
    ResponseEntity<AddressDto> getAddress(String cep, String logradouro, short number);
    ResponseEntity<String> deleteAddress(AddressDto address);
}
