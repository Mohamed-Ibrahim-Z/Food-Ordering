package com.food.ordering.services;

import com.food.ordering.model.Category;
import com.food.ordering.model.IngredientsCategory;
import com.food.ordering.model.IngredientsItem;
import com.food.ordering.model.Restaurant;
import com.food.ordering.repository.IngredientCategoryRepository;
import com.food.ordering.repository.IngredientItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientsService{

    private IngredientItemRepository ingredientItemRepository;

    private IngredientCategoryRepository ingredientCategoryRepository;

    private RestaurantService restaurantService;

    public IngredientServiceImpl(IngredientItemRepository ingredientItemRepository, IngredientCategoryRepository ingredientCategoryRepository, RestaurantService restaurantService) {
        this.ingredientItemRepository = ingredientItemRepository;
        this.ingredientCategoryRepository = ingredientCategoryRepository;
        this.restaurantService = restaurantService;
    }

    @Override
    public IngredientsCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientsCategory category = new IngredientsCategory();

        category.setRestaurant(restaurant);
        category.setName(name);

        return ingredientCategoryRepository.save(category);
    }

    @Override
    public IngredientsCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientsCategory> optional = ingredientCategoryRepository.findById(id);

        if(optional.isEmpty()) throw new Exception("Category Not Found");
        return optional.get();
    }

    @Override
    public List<IngredientsCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        //Just Checking
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientsCategory category = findIngredientCategoryById(categoryId);

        IngredientsItem item= new IngredientsItem();

        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(category);

        IngredientsItem ingredientsItem = ingredientItemRepository.save(item);
        category.getIngredients().add(ingredientsItem);
        return ingredientsItem;
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> optional= ingredientItemRepository.findById(id);
        if(optional.isEmpty()) throw new Exception("Ingredient Item Not Found");

        IngredientsItem item = optional.get();
        item.setStoke(!item.isStoke());
        return ingredientItemRepository.save(item);
    }

}
