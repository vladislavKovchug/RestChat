package com.teamdev.chat.repository;


import com.teamdev.chat.database.Tables;
import com.teamdev.chat.entity.User;

public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {
    @Override
    protected Tables getTable() {
        return Tables.USERS_TABLE;
    }
}
