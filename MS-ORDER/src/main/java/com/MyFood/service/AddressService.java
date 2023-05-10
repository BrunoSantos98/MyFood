package com.MyFood.service;

import com.MyFood.dto.AddressDto;

import java.util.UUID;

public interface AddressService {
    AddressDto getNewAddress(AddressDto addressDto);
    AddressDto getAddressByInformations(String cep, String logradouro, short number);

    void deleteAddress(AddressDto addressDto);
}
