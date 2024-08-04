package com.food.ordering.services;


import com.food.ordering.model.User;

public interface UserService {

    public User findUserByToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;

}
