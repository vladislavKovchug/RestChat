package com.teamdev.chat.database;


import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.teamdev.chat.entity.ChatRoom;
import com.teamdev.chat.entity.DatabaseEntity;
import com.teamdev.chat.entity.User;

import java.util.*;

public enum ChatDatabase {
    INSTANCE();

    private Map<Tables, List<DatabaseEntity>> database = new HashMap<Tables, List<DatabaseEntity>>();

    public List<DatabaseEntity> selectTable(Tables table) {
        if(database.containsKey(table)){
            return database.get(table);
        }
        return new ArrayList<DatabaseEntity>();
    }

    public void insertIntoTable(Tables table, DatabaseEntity line){
        if(database.containsKey(table)){
            line.setId(database.get(table).size() + 1);
            database.get(table).add(line);
        }
    }

    public void updateInTable(Tables table, DatabaseEntity line, long id){
        int index = 0;
        if(database.containsKey(table)){
            for(DatabaseEntity entity: database.get(table)){
                if(entity.getId() == id){
                    database.get(table).set(index, line);
                    break;
                }
                index++;
            }
        }
    }

    private void createTable(Tables table) {
        if(!database.containsKey(table)){
            database.put(table, new ArrayList<DatabaseEntity>());
        }
    }

    ChatDatabase() {
        createTable(Tables.USERS_TABLE);
        createTable(Tables.CHAT_ROOMS_TABLE);
        createTable(Tables.MESSAGES_TABLE);

        HashFunction hf = Hashing.md5();

        insertIntoTable(Tables.USERS_TABLE, new User("user1",
                hf.newHasher().putString("12345", Charsets.UTF_8).hash().toString(), 20, new Date(), -1));
        insertIntoTable(Tables.USERS_TABLE, new User("user2",
                hf.newHasher().putString("big_password123", Charsets.UTF_8).hash().toString(), 20, new Date(), -1));

        insertIntoTable(Tables.CHAT_ROOMS_TABLE, new ChatRoom("chat"));
    }
}