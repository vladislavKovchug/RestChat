package com.teamdev.chat.repository;


import com.teamdev.chat.database.Tables;
import com.teamdev.chat.entity.ChatRoom;

public class ChatRoomRepository extends AbstractRepository<ChatRoom> {

    @Override
    Tables getTable() {
        return Tables.CHAT_ROOMS_TABLE;
    }

}
