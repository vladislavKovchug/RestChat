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

    private Map<Tables, List<DatabaseEntity>> database = new HashMap<>();
    private Map<Tables, Long> databaseIndex = new HashMap<>();

    public List<DatabaseEntity> selectTable(Tables table) {
        if(database.containsKey(table)){
            return database.get(table);
        }
        return new ArrayList<>();
    }

    public void insertIntoTable(Tables table, DatabaseEntity line){

        if(database.containsKey(table)){
            Long index = databaseIndex.get(table) + 1;
            line.setId(index);
            database.get(table).add(line);
            databaseIndex.put(table, index);
        }
    }

    public void updateInTable(Tables table, DatabaseEntity line, long id){
        int index = 0;
        if(!database.containsKey(table)){
            throw new RuntimeException("Error on UPDATE. No entity with id=" + Long.toString(id) + " was found.");
        }

        for(DatabaseEntity entity: database.get(table)){
            if(entity.getId() == id){
                database.get(table).set(index, line);
                break;
            }
            index++;
        }

    }

    public void deleteFromTable(Tables table, long id){
        if(!database.containsKey(table)){
            throw new RuntimeException("Error on DELETE. No entity with id=" + Long.toString(id) + " was found.");
        }

        for(DatabaseEntity entity: database.get(table)){
            if(entity.getId() == id){
                database.get(table).remove(entity);
                break;
            }
        }
    }

    private void createTable(Tables table) {
        if(database.containsKey(table)){
            throw new RuntimeException("Table " + table.name() + "already exists");
        }
        database.put(table, new ArrayList<>());
        databaseIndex.put(table, (long) 0);
    }

    ChatDatabase() {
        createTable(Tables.USERS_TABLE);
        createTable(Tables.CHAT_ROOMS_TABLE);
        createTable(Tables.MESSAGES_TABLE);

        HashFunction hf = Hashing.sha256();

        insertIntoTable(Tables.USERS_TABLE, new User("user1",
                hf.newHasher().putString("12345", Charsets.UTF_8).hash().toString(), 20, new Date()));
        insertIntoTable(Tables.USERS_TABLE, new User("user2",
                hf.newHasher().putString("big_password123", Charsets.UTF_8).hash().toString(), 20, new Date()));

        insertIntoTable(Tables.CHAT_ROOMS_TABLE, new ChatRoom("chat"));
    }
}
