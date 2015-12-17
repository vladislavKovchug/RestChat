package com.teamdev.chat.repository;

import com.teamdev.chat.entity.ChatRoom;

public interface ChatRoomRepository extends Repository<ChatRoom> {
    ChatRoom findChatRoomByName(String name);
}
