package com.teamdev.chat.dto;

import java.util.Date;

public class MessageDTO {
    public final Long fromUserId;
    public final String fromUserName;
    public final String message;
    public final boolean privateMessage;
    private final Date date;

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