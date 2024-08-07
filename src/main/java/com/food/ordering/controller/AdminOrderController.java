package com.food.ordering.controller;

import com.food.ordering.model.Order;
import com.food.ordering.model.User;
import com.food.ordering.services.OrderService;
import com.food.ordering.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {
    private OrderService orderService;

    private UserService userService;

    public AdminOrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }


    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false) String orderStatus,
            @PathVariable Long id
    ) throws Exception{
        List<Order> orders=orderService.getRestaurantOrder(id,orderStatus);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String orderStatus,
            @PathVariable Long id

    ) throws Exception{
        Order order=orderService.updateOrder(id, orderStatus);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
