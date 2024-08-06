package com.food.ordering.services;

import com.food.ordering.model.Category;
import com.food.ordering.model.Food;
import com.food.ordering.model.Restaurant;
import com.food.ordering.request.CreateFoodRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FoodService {
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantFoods(
            Long restaurantId,
            boolean isVegetarian,
            boolean isNonveg,
            boolean isSeasonal,
            String foodCategory
    );

    public List<Food> searchFood(String keyword);

    public  Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailabilityStatus(Long foodId) throws Exception;
}
