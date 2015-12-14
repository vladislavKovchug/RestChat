package com.teamdev.chat.entity;


public class ChatRoom implements DatabaseEntity {
    private long id;
    private String name;

    public ChatRoom() {
        this.id = -1;
    }

    public ChatRoom(String name) {
        this.id = -1;
        this.name = name;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatRoom)) return false;

        ChatRoom chatRoom = (ChatRoom) o;

        if (id != chatRoom.id) return false;
        return !(name != null ? !name.equals(chatRoom.name) : chatRoom.name != null);

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
