package com.teamdev.chat.service;


import com.teamdev.chat.dto.UserProfileDTO;
import com.teamdev.chat.entity.User;
import com.teamdev.chat.repository.UserRepository;
import com.teamdev.chat.repository.UserRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    UserAuthenticationService userAuthenticationService = new UserAuthenticationServiceImpl();

    UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public UserProfileDTO readUserProfile(String token, long userId) {
        userAuthenticationService.checkUserLogged(token);
        final User user = userRepository.findOne(userId);
        if (user == null) {
            throw new RuntimeException("No user found with id " + Long.toString(userId));
        }

        return new UserProfileDTO(user.getId(), user.getLogin(), user.getAge(), user.getBirthday());
    }

    @Override
    public Iterable<UserProfileDTO> readAllUsersProfile(String token) {
        userAuthenticationService.checkUserLogged(token);
        final List<User> allUsers = userRepository.findAll();
        final ArrayList<UserProfileDTO> userProfileDTOs = new ArrayList<>();
        for (User user : allUsers) {
            userProfileDTOs.add(new UserProfileDTO(user.getId(), user.getLogin(), user.getAge(), user.getBirthday()));
        }
        return userProfileDTOs;
    }
}
