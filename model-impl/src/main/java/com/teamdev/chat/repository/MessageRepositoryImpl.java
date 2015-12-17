package com.teamdev.chat.repository;


import com.teamdev.chat.database.Tables;
import com.teamdev.chat.entity.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageRepositoryImpl extends AbstractRepository<Message> implements MessageRepository {

    @Override
    protected Tables getTable() {
        return Tables.MESSAGES_TABLE;
    }

    public Iterable<Message> findAllUserMessagesAfter(long userId, long chatRoom, Date date) {
        final List<Message> allMessages = findAll();
        final ArrayList<Message> result = new ArrayList<>();
        for (Message message : allMessages) {
            if (message.getDate().after(date)) {
                result.add(message);
            }
        }
        return result;
    }
}
