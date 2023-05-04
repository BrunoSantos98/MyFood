package com.MyFood.controller.implementation;

import com.MyFood.controller.RestaurantController;
import com.MyFood.dto.RestaurantMenuDto;
import com.MyFood.dto.RestaurantsNameDto;
import com.MyFood.services.RestaurantServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RestaurantControllerImplementation implements RestaurantController {

    private final RestaurantServices services;

    public RestaurantControllerImplementation(RestaurantServices services) {
        this.services = services;
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<RestaurantsNameDto>> getAllRestaurantsName() {
        return ResponseEntity.ok(services.getAllRestaurantsName());
    }

    @Override
    @GetMapping
    public ResponseEntity<RestaurantMenuDto> getRestaurantMenu(@RequestParam String name) {
        return ResponseEntity.ok(services.getRestaurantMenu(name));
    }
}
