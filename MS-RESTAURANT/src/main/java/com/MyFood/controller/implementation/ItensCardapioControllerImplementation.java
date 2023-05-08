package com.MyFood.controller.implementation;

import com.MyFood.controller.ItensCardapioController;
import com.MyFood.dto.OrderRestaurantDto;
import com.MyFood.services.ItensCardapioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ItensCardapioControllerImplementation implements ItensCardapioController {

    private final ItensCardapioService service;

    public ItensCardapioControllerImplementation(ItensCardapioService service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<List<OrderRestaurantDto>> getOrderedProductsList(@RequestBody List<String> list) {
        return ResponseEntity.ok(service.getListOfProducts(list));
    }
}
