package com.food.ordering.services;

import com.food.ordering.model.Category;
import com.food.ordering.model.Restaurant;
import com.food.ordering.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;

    private RestaurantService restaurantService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, RestaurantService restaurantService) {
        this.categoryRepository = categoryRepository;
        this.restaurantService = restaurantService;
    }

    @Override
    public Category createCategory(String name, Long userId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);

        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long id) throws Exception {

        return categoryRepository.findByRestaurantId(id);
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if(optionalCategory.isEmpty()) throw new Exception("Category Not Found");

        return optionalCategory.get();
    }
}
