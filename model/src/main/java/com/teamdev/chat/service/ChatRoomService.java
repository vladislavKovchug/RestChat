package com.teamdev.chat.service;

import com.teamdev.chat.dto.*;

public interface ChatRoomService {

    Iterable<ChatRoomDTO> getChatRooms(String token);
    void joinChatRoom(String token, long actor, long chatRoom);
    void leaveChatRoom(String token, long actor, long chatRoom);
    Iterable<UserProfileDTO> getUsersList(String token, long chatRoom);
}
