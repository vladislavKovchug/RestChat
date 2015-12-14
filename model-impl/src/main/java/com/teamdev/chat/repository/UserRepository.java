package com.teamdev.chat.repository;


import com.teamdev.chat.database.Tables;
import com.teamdev.chat.entity.User;

public class UserRepository extends AbstractRepository<User> {
    @Override
    Tables getTable() {
        return Tables.USERS_TABLE;
    }
}
