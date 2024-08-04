package com.food.ordering.services;

import com.food.ordering.config.JwtProvider;
import com.food.ordering.model.User;
import com.food.ordering.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    private JwtProvider jwtProvider;

    public UserServiceImpl(UserRepository userRepository,JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }


    @Override
    public User findUserByToken(String jwt) throws Exception {

         String email = jwtProvider.getEmailFromJwtToken(jwt);

         if(email == null) throw new Exception("User Not Found...");

         return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if(user == null) throw  new Exception("User Not Found...");
        return user;
    }
}
