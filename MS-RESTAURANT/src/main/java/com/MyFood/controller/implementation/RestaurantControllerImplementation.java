package com.MyFood.controller.implementation;

import com.MyFood.controller.RestaurantController;
import com.MyFood.dto.RestaurantMenuDto;
import com.MyFood.services.RestaurantServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/restaurants/v1")
public class RestaurantControllerImplementation implements RestaurantController {

    private final RestaurantServices services;

    public RestaurantControllerImplementation(RestaurantServices services) {
        this.services = services;
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<String>> getRestaurantNames() {
        return ResponseEntity.ok().body(services.getAllRestaurants());
    }

    @Override
    @GetMapping
    public ResponseEntity<RestaurantMenuDto> getRestaurantMenu(@RequestParam String name) {
        return ResponseEntity.ok().body(services.getRestaurantMenu(name));
    }
}
