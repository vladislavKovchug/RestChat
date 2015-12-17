package com.teamdev.chat.service;


import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.teamdev.chat.dto.RegisterUserDTO;
import com.teamdev.chat.entity.User;
import com.teamdev.chat.factory.RepositoryFactory;
import com.teamdev.chat.repository.UserRepository;
import com.teamdev.chat.repository.UserRepositoryImpl;

import java.util.List;

public class UserManagementServiceImpl implements UserManagementService {


    @Override
    public void register(RegisterUserDTO registerUserDTO) {
        HashFunction hf = Hashing.sha256();
        String passwordHash = hf.newHasher().putString(registerUserDTO.password, Charsets.UTF_8).hash().toString();
        if (RepositoryFactory.getUserRepository().findUserByName(registerUserDTO.login) != null) {
            throw new RuntimeException("User " + registerUserDTO.login + " already exists.");
        }
        RepositoryFactory.getUserRepository().save(new User(registerUserDTO.login, passwordHash, registerUserDTO.age,
                registerUserDTO.getBirthday()));
    }

    @Override
    public void deleteUser(long userId) {
        final User user = RepositoryFactory.getUserRepository().findOne(userId);
        if(user == null){
            throw new RuntimeException("User with id " + Long.toString(userId) + " does not exists.");
        }
        RepositoryFactory.getUserRepository().delete(user);
    }
}
