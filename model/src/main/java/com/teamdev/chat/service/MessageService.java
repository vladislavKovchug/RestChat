package com.teamdev.chat.service;


import com.teamdev.chat.dto.MessageDTO;

public interface MessageService {

    Iterable<MessageDTO> getChatRoomMessages(String token, long chatRoomId, long time);
    boolean sendMessage(String token, long chatRoomId, String message);
    boolean sendPrivateMessage(String token, long chatRoomId, long userId);

}
