package com.MyFood.service;

import com.MyFood.dto.DeliverymanDto;
import com.MyFood.dto.DeliverymanToCustomerDto;
import com.MyFood.model.Deliveryman;

import java.util.UUID;

public interface DeliveryManService {
    DeliverymanDto createNewDeliveryman(DeliverymanDto deliveryman);
    Deliveryman findDeliverymanById(UUID id);
    Deliveryman findDeliverymanByCpf(String cpf);
    DeliverymanToCustomerDto findDeliverymanByLicensePlate(String licensePlate);
    void deleteDeliveryman(String deliverymanInformation, String typeInformation);
}
