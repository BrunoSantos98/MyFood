package com.MyFood.controller.implementation;

import com.MyFood.controller.MenuController;
import com.MyFood.dto.OrderRestaurantDto;
import com.MyFood.services.MenuServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping("/menu/v1")
public class MenuControllerImplementation implements MenuController {

    private final MenuServices services;

    public MenuControllerImplementation(MenuServices services) {
        this.services = services;
    }

    @Override
    @PostMapping("/{restaurant}")
    public ResponseEntity<List<OrderRestaurantDto>> getOrder(@RequestBody List<String> names, @PathVariable String restaurant) {
        return ResponseEntity.ok(services.getItenForOrder(names, restaurant));
    }
}
