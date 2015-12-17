package com.teamdev.chat.repository;


import com.teamdev.chat.database.Tables;
import com.teamdev.chat.entity.User;

import java.util.List;

public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {
    @Override
    protected Tables getTable() {
        return Tables.USERS_TABLE;
    }

    @Override
    public User findUserByName(String name) {
        final List<User> allUsers = findAll();
        for (User user : allUsers){
            if(user.getLogin().equals(name)){
                return user;
            }
        }

        return null;
    }
}
