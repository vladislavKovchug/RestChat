package com.teamdev.chat.service;


import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.teamdev.chat.dto.RegisterUserDTO;
import com.teamdev.chat.entity.User;
import com.teamdev.chat.repository.UserRepository;
import com.teamdev.chat.repository.UserRepositoryImpl;

public class UserManagementServiceImpl implements UserManagementService {

    UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public void register(RegisterUserDTO registerUserDTO) {
        HashFunction hf = Hashing.sha256();
        String passwordHash = hf.newHasher().putString(registerUserDTO.password, Charsets.UTF_8).hash().toString();
        userRepository.save(new User(registerUserDTO.login, passwordHash, registerUserDTO.age,
                registerUserDTO.birthday));
    }
}
