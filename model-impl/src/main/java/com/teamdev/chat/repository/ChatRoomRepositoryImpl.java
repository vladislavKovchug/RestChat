package com.teamdev.chat.repository;


import com.teamdev.chat.database.ChatDatabase;
import com.teamdev.chat.database.Tables;
import com.teamdev.chat.entity.ChatRoom;
import com.teamdev.chat.entity.DatabaseEntity;

import java.util.List;

public class ChatRoomRepositoryImpl extends AbstractRepository<ChatRoom> implements ChatRoomRepository {

    @Override
    protected Tables getTable() {
        return Tables.CHAT_ROOMS_TABLE;
    }

    @Override
    public ChatRoom findChatRoomByName(String name) {
        final List<ChatRoom> allChatRooms = findAll();
        for (ChatRoom chatRoom : allChatRooms){
            if(chatRoom.getName().equals(name)){
                return chatRoom;
            }
        }

        return null;
    }
}
