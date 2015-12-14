package com.teamdev.chat.service;


import com.teamdev.chat.dto.MessageDTO;

import java.util.Date;

public interface MessageService {

    Iterable<MessageDTO> getUserChatRoomMessages(long chatRoom);
    boolean sendMessage(long actor, long chatRoom);
    boolean sendPrivateMessage(long actor, long chatRoom, long user);

}
