package com.food.ordering.controller;

import com.food.ordering.model.Restaurant;
import com.food.ordering.model.User;
import com.food.ordering.response.MessageResponse;
import com.food.ordering.request.CreateRestaurantRequest;
import com.food.ordering.services.RestaurantService;
import com.food.ordering.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    private RestaurantService restaurantService;

    private UserService userService;

    public AdminRestaurantController(RestaurantService restaurantService, UserService userService) {
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt
            )throws Exception{
        User user = userService.findUserByToken(jwt);

        System.out.println(req.getAddress());
        System.out.println(req.getCuisineType());

        Restaurant restaurant = restaurantService.createRestaurantRequest(req, user);

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);

    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(
            @RequestHeader("Authorization") String jwt
    )throws Exception{
        User user = userService.findUserByToken(jwt);

        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    )throws Exception{
        User user = userService.findUserByToken(jwt);

        Restaurant restaurant = restaurantService.updateRestaurant(id, req);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    )throws Exception{
        restaurantService.deleteRestaurant(id);
        MessageResponse res = new MessageResponse();
        res.setMessage("Restaurant deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    )throws Exception{

        Restaurant restaurant= restaurantService.updateRestaurantStatus(id);


        MessageResponse res = new MessageResponse();
        res.setMessage("Restaurant deleted successfully");
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


}
