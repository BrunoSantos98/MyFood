package com.MyFood.dto;

import com.MyFood.model.AddressModel;
import com.MyFood.model.OrderModel;

import java.util.Set;

public record CustomerDto(String name, String cpf, String email, String phone, Set<AddressDto> address, Set<OrderModel> orders) {
}
