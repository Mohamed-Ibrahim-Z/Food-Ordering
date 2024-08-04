package com.food.ordering.controller;

import com.food.ordering.model.User;
import com.food.ordering.services.UserService;
import io.jsonwebtoken.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwtToken(@RequestHeader("Authorization") String jwt) throws Exception {
        return new ResponseEntity<>(userService.findUserByToken(jwt), HttpStatus.OK);
    }
}
