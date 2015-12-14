package com.teamdev.chat.entity;


import java.util.Date;

public class Message implements DatabaseEntity {
    private long id;
    private long userFromId;
    private long userToId;
    private long chatRoomId;
    private Date date;
    private String message;

    public Message() {
        this.id = -1;
    }

    public Message(long userFromId, long userToId, long chatRoomId, Date date, String message) {
        this.id = -1;
        this.userFromId = userFromId;
        this.userToId = userToId;
        this.chatRoomId = chatRoomId;
        this.date = date;
        this.message = message;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public long getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(long userFromId) {
        this.userFromId = userFromId;
    }

    public long getUserToId() {
        return userToId;
    }

    public void setUserToId(long userToId) {
        this.userToId = userToId;
    }

    public long getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;

        Message message1 = (Message) o;

        if (id != message1.id) return false;
        if (userFromId != message1.userFromId) return false;
        if (userToId != message1.userToId) return false;
        if (chatRoomId != message1.chatRoomId) return false;
        if (date != null ? !date.equals(message1.date) : message1.date != null) return false;
        return !(message != null ? !message.equals(message1.message) : message1.message != null);

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
