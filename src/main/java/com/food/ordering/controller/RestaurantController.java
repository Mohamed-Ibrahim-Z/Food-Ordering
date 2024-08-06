package com.food.ordering.controller;

import com.food.ordering.dto.RestaurantDto;
import com.food.ordering.model.Restaurant;
import com.food.ordering.model.User;
import com.food.ordering.services.RestaurantService;
import com.food.ordering.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private RestaurantService restaurantService;

    private UserService userService;

    public RestaurantController(RestaurantService restaurantService, UserService userService) {
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String keyword
    ) throws Exception{

        List<Restaurant> restaurants = restaurantService.searchRestaurants(keyword);

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurants(
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        List<Restaurant> restaurants = restaurantService.getAllRestaurants();

        return new ResponseEntity<>(restaurants,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception{

        Restaurant restaurant = restaurantService.findRestaurantById(id);

        return new ResponseEntity<>(restaurant,HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDto> addToFavorites(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception{
        User user= userService.findUserByToken(jwt);

        RestaurantDto restaurant = restaurantService.addFavourite(id,user);

        return new ResponseEntity<>(restaurant,HttpStatus.OK);
    }
}
