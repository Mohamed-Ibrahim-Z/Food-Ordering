package com.food.ordering.controller;

import com.food.ordering.model.Food;
import com.food.ordering.model.Restaurant;
import com.food.ordering.model.User;
import com.food.ordering.request.CreateFoodRequest;
import com.food.ordering.services.FoodService;
import com.food.ordering.services.RestaurantService;
import com.food.ordering.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    private FoodService foodService;

    private UserService userService;

    private RestaurantService restaurantService;

    public FoodController(FoodService foodService, UserService userService, RestaurantService restaurantService) {
        this.foodService = foodService;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestHeader("Authorization") String jwt,
                                           @RequestParam String name) throws Exception{
        List<Food> food = foodService.searchFood(name);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(@RequestHeader("Authorization") String jwt,
                                                @RequestParam boolean vegetarian,
                                                @RequestParam boolean seasonal,
                                                @RequestParam boolean nonVeg,
                                                @RequestParam(required = false) String foodCategory,
                                                @PathVariable Long restaurantId
                                                ) throws Exception{
        List<Food> food = foodService.getRestaurantFoods(restaurantId, vegetarian, nonVeg, seasonal, foodCategory);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
