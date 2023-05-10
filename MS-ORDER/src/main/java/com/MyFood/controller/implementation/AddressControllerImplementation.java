package com.MyFood.controller.implementation;

import com.MyFood.controller.AddressController;
import com.MyFood.dto.AddressDto;
import com.MyFood.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AddressControllerImplementation implements AddressController {
    
    private final AddressService service;

    public AddressControllerImplementation(AddressService service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<AddressDto> getNewAddress(@RequestBody AddressDto address) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.getNewAddress(address));
    }

    @Override
    @GetMapping
    public ResponseEntity<AddressDto> getAddress(@RequestParam String cep, @RequestParam String logradouro,
                                                 @RequestParam short number) {
        return ResponseEntity.ok(service.getAddressByInformations(cep, logradouro, number));
    }

    @Override
    @DeleteMapping
    public ResponseEntity<String> deleteAddress(@RequestBody AddressDto address) {
        service.deleteAddress(address);
        return ResponseEntity.ok("Endereço deletado com sucesso");
    }
}
