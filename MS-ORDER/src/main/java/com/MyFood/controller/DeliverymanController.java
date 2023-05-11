package com.MyFood.controller;

import com.MyFood.dto.DeliverymanDto;
import com.MyFood.dto.DeliverymanToCustomerDto;
import com.MyFood.model.Deliveryman;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface DeliverymanController {

    ResponseEntity<DeliverymanDto> createNewDeliveryman(DeliverymanDto deliverymanDto);
    ResponseEntity<Deliveryman> getDeliverymanById(UUID id);
    ResponseEntity<Deliveryman> getDeliverymanByCpf(String cpf);
    ResponseEntity<DeliverymanToCustomerDto> getDeliverymanByLicensePlate(String licensePlate);
    ResponseEntity<String> deleteDeliveryman(String deliverymanInformation, String typeInformation);
}
