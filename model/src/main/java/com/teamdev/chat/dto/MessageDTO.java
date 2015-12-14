package com.teamdev.chat.dto;

import java.util.Date;

public class MessageDTO{
    final Long fromUserId;
    final String fromUserName;
    final String message;
    final boolean privateMessage;
    private Date date;

    public MessageDTO(Long fromUserId, String fromUserName, String message, boolean privateMessage, Date date) {
        this.fromUserId = fromUserId;
        this.fromUserName = fromUserName;
        this.message = message;
        this.privateMessage = privateMessage;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}