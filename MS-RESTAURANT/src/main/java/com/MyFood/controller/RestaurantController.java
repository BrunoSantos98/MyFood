package com.MyFood.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public interface RestaurantController {

}
