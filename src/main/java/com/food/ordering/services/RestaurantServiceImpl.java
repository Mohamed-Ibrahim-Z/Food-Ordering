package com.food.ordering.services;

import com.food.ordering.dto.RestaurantDto;
import com.food.ordering.model.Address;
import com.food.ordering.model.Restaurant;
import com.food.ordering.model.User;
import com.food.ordering.repository.AddressRepository;
import com.food.ordering.repository.RestaurantRepository;
import com.food.ordering.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class RestaurantServiceImpl implements RestaurantService{

    private RestaurantRepository restaurantRepository;

    private AddressRepository addressRepository;

    private UserRepository userRepository;


    @Override
    public Restaurant createRestaurantRequest(CreateRestaurantRequest restaurantRequest, User user) {
        Address address = addressRepository.save(restaurantRequest.getAddress());

        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.getName());
        restaurant.setDescription(restaurantRequest.getDescription());
        restaurant.setCuisineType(restaurantRequest.getCuisineType());
        restaurant.setAddress(address);
        restaurant.setContactInformation(restaurantRequest.getContactInformation());
        restaurant.setImages(restaurantRequest.getImages());
        restaurant.setOpeningHours(restaurantRequest.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRes) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurant.setName(updatedRes.getName());
        restaurant.setDescription(updatedRes.getDescription());
        restaurant.setCuisineType(updatedRes.getCuisineType());
        restaurant.setContactInformation(updatedRes.getContactInformation());
        restaurant.setOpeningHours(updatedRes.getOpeningHours());

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);

    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurants(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception {
        Optional<Restaurant> restaurant =  restaurantRepository.findById(restaurantId);
        if(restaurant.isEmpty()) throw new Exception("Restaurant not found....");

        return restaurant.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception{

        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if(restaurant == null) throw new Exception("Restaurant Not Found with Id: "+ userId);

        return restaurant;
    }

    @Override
    public RestaurantDto addFavourite(Long restaurantId, User user) throws Exception{

        Restaurant restaurant = findRestaurantById(restaurantId);
        RestaurantDto dto = new RestaurantDto();

        dto.setTitle(restaurant.getName());
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setId(restaurantId);

        if(user.getFavorites().contains(dto)) user.getFavorites().remove(dto);
        else user.getFavorites().add(dto);

        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception{
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
