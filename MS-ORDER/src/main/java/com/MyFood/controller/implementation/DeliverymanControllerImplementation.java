package com.MyFood.controller.implementation;

import com.MyFood.controller.DeliverymanController;
import com.MyFood.dto.DeliverymanDto;
import com.MyFood.dto.DeliverymanToCustomerDto;
import com.MyFood.model.Deliveryman;
import com.MyFood.service.DeliveryManService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deliveryman")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class DeliverymanControllerImplementation implements DeliverymanController {

    private final DeliveryManService service;

    public DeliverymanControllerImplementation(DeliveryManService service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<DeliverymanDto> createNewDeliveryman(@RequestBody DeliverymanDto deliverymanDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createNewDeliveryman(deliverymanDto));
    }

    @Override
    @GetMapping("/admin/id/{id}")
    public ResponseEntity<Deliveryman> getDeliverymanById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findDeliverymanById(id));
    }

    @Override
    @GetMapping("/admin/cpf/{cpf}")
    public ResponseEntity<Deliveryman> getDeliverymanByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(service.findDeliverymanByCpf(cpf));
    }

    @Override
    @GetMapping("/{licensePlate}")
    public ResponseEntity<DeliverymanToCustomerDto> getDeliverymanByLicensePlate(@PathVariable String licensePlate) {
        return ResponseEntity.ok(service.findDeliverymanByLicensePlate(licensePlate));
    }

    @Override
    @DeleteMapping
    public ResponseEntity<String> deleteDeliveryman(@RequestParam String deliverymanInformation, @RequestParam String typeInformation) {
        service.deleteDeliveryman(deliverymanInformation, typeInformation);
        return ResponseEntity.ok("Entregador deletado com sucesso");
    }
}
