package com.food.ordering.controller;

import com.food.ordering.model.CartItem;
import com.food.ordering.model.Order;
import com.food.ordering.model.User;
import com.food.ordering.request.OrderRequest;
import com.food.ordering.services.OrderService;
import com.food.ordering.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    private OrderService orderService;

    private UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/order")
    public ResponseEntity<Order> createdOrder(
            @RequestHeader("Authorization") String jwt,
            @RequestBody OrderRequest req
            ) throws Exception{
        User user = userService.findUserByToken(jwt);
        Order order=orderService.createOrder(req, user);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(
            @RequestHeader("Authorization") String jwt
            ) throws Exception{
        User user = userService.findUserByToken(jwt);
        List<Order> orders=orderService.getUserOrder(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
