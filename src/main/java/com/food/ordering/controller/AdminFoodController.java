package com.food.ordering.controller;

import com.food.ordering.model.Food;
import com.food.ordering.model.Restaurant;
import com.food.ordering.model.User;
import com.food.ordering.request.CreateFoodRequest;
import com.food.ordering.response.MessageResponse;
import com.food.ordering.services.FoodService;
import com.food.ordering.services.RestaurantService;
import com.food.ordering.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    private FoodService foodService;

    private UserService userService;

    private RestaurantService restaurantService;

    public AdminFoodController(FoodService foodService, UserService userService, RestaurantService restaurantService) {
        this.foodService = foodService;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestHeader("Authorization") String jwt,
                                           @RequestBody CreateFoodRequest req) throws Exception{
        User user = userService.findUserByToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        Food food = foodService.createFood(req, req.getCategory(), restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@RequestHeader("Authorization") String jwt,
                                           @RequestBody Long id) throws Exception{
        foodService.deleteFood(id);

        MessageResponse messageResponse= new MessageResponse();

        messageResponse.setMessage("Food Deleted Successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailability(@RequestHeader("Authorization") String jwt,
                                                      @RequestBody Long id) throws Exception{
        Food food = foodService.updateAvailabilityStatus(id);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
