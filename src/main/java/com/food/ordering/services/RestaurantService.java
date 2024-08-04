package com.food.ordering.services;

import com.food.ordering.dto.RestaurantDto;
import com.food.ordering.model.Restaurant;
import com.food.ordering.model.User;

import java.util.List;

public interface RestaurantService {

    public Restaurant createRestaurantRequest(CreateRestaurantRequest restaurantRequest, User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRes) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurants();

    public List<Restaurant> searchRestaurants(String keyword) throws Exception;

    public Restaurant findRestaurantById(Long restaurantId) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addFavourite(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception;
}
