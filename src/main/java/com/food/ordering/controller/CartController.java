package com.food.ordering.controller;

import com.food.ordering.model.Cart;
import com.food.ordering.model.CartItem;
import com.food.ordering.model.User;
import com.food.ordering.request.AddCartItemRequest;
import com.food.ordering.request.UpdateCartItemRequest;
import com.food.ordering.response.MessageResponse;
import com.food.ordering.services.CartService;
import com.food.ordering.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    private CartService cartService;

    private UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(
            @RequestHeader("Authorization") String jwt,
            @RequestBody AddCartItemRequest req
            ) throws Exception{
        CartItem cartItem= cartService.addItemToCart(req,jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @RequestHeader("Authorization") String jwt,
            @RequestBody UpdateCartItemRequest req
    ) throws Exception{
        CartItem cartItem= cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeItem(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception{
        Cart cart= cartService.removeItemFromCart(id,jwt);

        return new ResponseEntity<>(cart, HttpStatus.OK);

    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserByToken(jwt);

        Cart cart= cartService.clearCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserByToken(jwt);
        Cart cart = cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
