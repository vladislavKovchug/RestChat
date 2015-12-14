package com.teamdev.chat.dto;

public class ChatRoomDTO{
    final Long id;
    final String name;

    public ChatRoomDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}