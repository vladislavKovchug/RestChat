package com.teamdev.chat.repository;


import com.teamdev.chat.database.Tables;
import com.teamdev.chat.entity.ChatRoom;

public class ChatRoomRepositoryImpl extends AbstractRepository<ChatRoom> implements ChatRoomRepository {

    @Override
    protected Tables getTable() {
        return Tables.CHAT_ROOMS_TABLE;
    }

}
