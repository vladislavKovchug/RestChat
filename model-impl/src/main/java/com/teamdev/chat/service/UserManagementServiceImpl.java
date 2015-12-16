package com.teamdev.chat.service;


import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.teamdev.chat.dto.RegisterUserDTO;
import com.teamdev.chat.entity.User;
import com.teamdev.chat.repository.UserRepository;

public class UserManagementServiceImpl implements UserManagementService {

    UserRepository userRepository = new UserRepository();

    @Override
    public void register(RegisterUserDTO registerUserDTO) {
        HashFunction hf = Hashing.sha256();
        String passwordHash = hf.newHasher().putString(registerUserDTO.password, Charsets.UTF_8).hash().toString();
        userRepository.save(new User(registerUserDTO.login, passwordHash, registerUserDTO.age,
                registerUserDTO.birthday));
        //userRepository.save(new User(login, ));

        throw new RuntimeException("not implemented");
    }
}
