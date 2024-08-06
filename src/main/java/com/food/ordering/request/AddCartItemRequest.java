package com.food.ordering.request;

import java.util.List;

public class AddCartItemRequest {
    private Long foodId;
    private int quantity;
    private List<String> Ingredients;

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<String> getIngredients() {
        return Ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        Ingredients = ingredients;
    }
}
