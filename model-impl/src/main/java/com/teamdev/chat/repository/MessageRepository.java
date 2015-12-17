package com.teamdev.chat.repository;

import com.teamdev.chat.entity.Message;

import java.util.Date;

public interface MessageRepository extends Repository<Message> {
    public Iterable<Message> findAllUserMessagesAfter(long userId, long chatRoom, Date date);
}
