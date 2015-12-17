package com.teamdev.chat.repository;


import com.teamdev.chat.entity.ChatRoom;
import com.teamdev.chat.entity.User;

public interface UserRepository extends Repository<User> {
    User findUserByName(String name);
}
