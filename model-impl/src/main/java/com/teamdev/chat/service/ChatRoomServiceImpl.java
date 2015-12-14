package com.teamdev.chat.service;

import com.teamdev.chat.dto.ChatRoomDTO;
import com.teamdev.chat.dto.UserProfileDTO;
import com.teamdev.chat.entity.ChatRoom;
import com.teamdev.chat.repository.ChatRoomRepository;

import java.util.ArrayList;
import java.util.List;


public class ChatRoomServiceImpl implements ChatRoomService {

    ChatRoomRepository chatRoomRepository = new ChatRoomRepository();

    @Override
    public Iterable<ChatRoomDTO> getChatRooms(String token) {
        final List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        List<ChatRoomDTO> result = new ArrayList<>();
        for (ChatRoom chatRoom : chatRooms){
            result.add(new ChatRoomDTO(chatRoom.getId(), chatRoom.getName()));
        }
        return result;
    }

    @Override
    public void joinChatRoom(String token, long actor, long chatRoom) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public void leaveChatRoom(String token, long actor, long chatRoom) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public Iterable<UserProfileDTO> getUsersList(String token, long chatRoom) {
        throw new RuntimeException("not implemented");
    }
}
