package com.food.ordering.services;

import com.food.ordering.model.Cart;
import com.food.ordering.model.CartItem;
import com.food.ordering.model.Food;
import com.food.ordering.model.User;
import com.food.ordering.repository.CartItemRepository;
import com.food.ordering.repository.CartRepository;
import com.food.ordering.repository.FoodRepository;
import com.food.ordering.request.AddCartItemRequest;

import java.util.Optional;

public class CartServiceImpl implements CartService{

    private CartRepository cartRepository;

    private UserService userService;

    private CartItemRepository cartItemRepository;

    private FoodService foodService;

    public CartServiceImpl(CartRepository cartRepository, UserService userService, CartItemRepository cartItemRepository,
                           FoodService foodService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.cartItemRepository = cartItemRepository;
        this.foodService = foodService;
    }

    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
       User user = userService.findUserByToken(jwt);

       Food food = foodService.findFoodById(req.getFoodId());

       Cart cart = cartRepository.findByCustomerId(user.getId());

       for(CartItem cartItem:cart.getItems()){
           if(cartItem.getFood().equals(food)){
               int newQuantity = cartItem.getQuantity()+ req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
           }
       }

       CartItem cartItem= new CartItem();
       cartItem.setFood(food);
       cartItem.setCart(cart);
       cartItem.setQuantity(req.getQuantity());
       cartItem.setIngredients(req.getIngredients());
       cartItem.setTotalPrice(req.getQuantity()* food.getPrice());

       CartItem savedCartItem=cartItemRepository.save(cartItem);
       cart.getItems().add(savedCartItem);
        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItemOptional=cartItemRepository.findById(cartItemId);
        if(cartItemOptional.isEmpty()) throw new Exception("Cart Item Not Found");

        CartItem item = cartItemOptional.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice()*quantity);
        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user=userService.findUserByToken(jwt);

        Cart cart=cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItemOptional=cartItemRepository.findById(cartItemId);
        if(cartItemOptional.isEmpty()) throw new Exception("Cart Item Not Found");

        cart.getItems().remove(cartItemOptional.get());
        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {
        Long total = 0L;
        for(CartItem cartItem: cart.getItems())
            total+=cartItem.getFood().getPrice()*cartItem.getQuantity();

        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> optionalCart = cartRepository.findById(id);

        if(optionalCart.isEmpty()) throw new Exception("Cart Not Found With this id "+ id);

        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(String jwt) throws Exception {
        User user = userService.findUserByToken(jwt);
        return cartRepository.findByCustomerId(user.getId());
    }

    @Override
    public Cart clearCart(String jwt) throws Exception {
        Cart cart=findCartByUserId(jwt);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
