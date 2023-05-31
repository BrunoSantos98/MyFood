package com.MyFood.controller.implementation;

import com.MyFood.controller.CustomerController;
import com.MyFood.dto.AddressDto;
import com.MyFood.dto.CustomerDto;
import com.MyFood.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/customer")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class CustomerControllerImplementation implements CustomerController {

    private final CustomerService service;

    public CustomerControllerImplementation(CustomerService service) {
        this.service = service;
    }


    @Override
    @PostMapping
    public ResponseEntity<CustomerDto> createnewCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createnewCustomer(customerDto));
    }

    @Override
    @GetMapping("/findByCpf/{cpf}")
    public ResponseEntity<CustomerDto> getCustomerByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(service.getCustomerByCpf(cpf));
    }

    @Override
    @GetMapping("/findByEmail")
    public ResponseEntity<CustomerDto> getCustomerByEmail(@RequestParam String email) {
        return ResponseEntity.ok(service.getCustomerByEmail(email));
    }

    @Override
    @GetMapping("/findAddressess/{cpf}")
    public ResponseEntity<Set<AddressDto>> getCustomerAddresses(@PathVariable String cpf) {
        return ResponseEntity.ok(service.getCustomerAddresses(cpf));
    }

    @Override
    @PatchMapping("/updateData/{cpf}")
    public ResponseEntity<CustomerDto> updateUserData(@RequestBody CustomerDto customerDto, @PathVariable String cpf) {
        return ResponseEntity.ok(service.updateUserData(customerDto, cpf));
    }

    @Override
    @PatchMapping("/updateAddress/{cpf}")
    public ResponseEntity<CustomerDto> updateUserAddress(@RequestBody AddressDto address, @PathVariable String cpf) {
        return ResponseEntity.ok(service.updateUserAddress(address, cpf));
    }

    @Override
    @DeleteMapping
    public ResponseEntity<String> deleteCustomer(String cpf) {
        return ResponseEntity.ok("Usuario deletado com sucesso");
    }
}
