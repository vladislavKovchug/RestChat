package com.teamdev.chat.service;

import com.teamdev.chat.dto.MessageDTO;

public class MessageServiceImpl implements MessageService {

    @Override
    public Iterable<MessageDTO> getUserChatRoomMessages(long chatRoom) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public boolean sendMessage(long actor, long chatRoom) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public boolean sendPrivateMessage(long actor, long chatRoom, long user) {
        throw new RuntimeException("not implemented");
    }
}
