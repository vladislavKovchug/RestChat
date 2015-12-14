package com.teamdev.chat.service;

import com.teamdev.chat.dto.*;

public interface ChatRoomService {

    Iterable<ChatRoomDTO> getChatRooms(String token);
    boolean joinChatRoom(String token, long chatRoom);
    boolean leaveChatRoom(String token, long chatRoom);
    Iterable<UserProfileDTO> getUsersList(String token, long chatRoom);
}
