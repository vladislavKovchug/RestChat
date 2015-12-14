package com.teamdev.chat.repository;


import com.teamdev.chat.database.Tables;
import com.teamdev.chat.entity.Message;

import java.util.Date;

public class MessageRepository extends AbstractRepository<Message> {

    @Override
    Tables getTable() {
        return Tables.MESSAGES_TABLE;
    }

    Iterable<Message> findAllUserMessages(long userId, long chatRoom){
        throw new RuntimeException("not implemented");
    }

    Iterable<Message> findAllUserMessagesBefore(long userId, long chatRoom, Date date){
        throw new RuntimeException("not implemented");
    }
}
