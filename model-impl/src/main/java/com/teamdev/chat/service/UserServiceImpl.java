package com.teamdev.chat.service;


import com.teamdev.chat.dto.UserProfileDTO;
import com.teamdev.chat.entity.User;
import com.teamdev.chat.factory.RepositoryFactory;
import com.teamdev.chat.factory.ServiceFactory;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public UserProfileDTO readUserProfile(String token, long userId) {
        ServiceFactory.getUserAuthenticationService().readCurrentUserId(token);
        final User user = RepositoryFactory.getUserRepository().findOne(userId);
        if (user == null) {
            throw new RuntimeException("No user found with id " + Long.toString(userId) + ".");
        }

        return new UserProfileDTO(user.getId(), user.getLogin(), user.getAge(), user.getBirthday());
    }

    @Override
    public UserProfileDTO readCurrentUserProfile(String token) {
        final long userId = ServiceFactory.getUserAuthenticationService().readCurrentUserId(token);
        final User user = RepositoryFactory.getUserRepository().findOne(userId);
        return new UserProfileDTO(user.getId(), user.getLogin(), user.getAge(), user.getBirthday());
    }

    @Override
    public Iterable<UserProfileDTO> readAllUsersProfile(String token) {
        ServiceFactory.getUserAuthenticationService().readCurrentUserId(token);
        final List<User> allUsers = RepositoryFactory.getUserRepository().findAll();
        final ArrayList<UserProfileDTO> userProfileDTOs = new ArrayList<>();
        for (User user : allUsers) {
            userProfileDTOs.add(new UserProfileDTO(user.getId(), user.getLogin(), user.getAge(), user.getBirthday()));
        }
        return userProfileDTOs;
    }
}
