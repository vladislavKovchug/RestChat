package com.teamdev.chat.service;

import com.teamdev.chat.dto.ChatRoomDTO;
import com.teamdev.chat.dto.UserProfileDTO;
import com.teamdev.chat.entity.ChatRoom;
import com.teamdev.chat.entity.User;
import com.teamdev.chat.repository.ChatRoomRepository;
import com.teamdev.chat.repository.UserRepository;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.List;


public class ChatRoomServiceImpl implements ChatRoomService {

    UserAuthenticationService userAuthenticationService = new UserAuthenticationServiceImpl();

    ChatRoomRepository chatRoomRepository = new ChatRoomRepository();

    UserRepository userRepository = new UserRepository();

    @Override
    public Iterable<ChatRoomDTO> getChatRooms(String token) {
        if (userAuthenticationService.checkUserLogged(token) == -1){
            throw new AccessControlException("Access denied.");
        }

        final List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        List<ChatRoomDTO> result = new ArrayList<>();
        for (ChatRoom chatRoom : chatRooms){
            result.add(new ChatRoomDTO(chatRoom.getId(), chatRoom.getName()));
        }
        return result;
    }

    @Override
    public boolean joinChatRoom(String token, long chatRoom) {
        final long userId = userAuthenticationService.checkUserLogged(token);
        if (userId == -1){
            throw new AccessControlException("Access denied.");
        }

        if (chatRoomRepository.findOne(chatRoom) == null) {
            return false;
        }

        final User user = userRepository.findOne(userId);
        user.setChatRoomId(chatRoom);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean leaveChatRoom(String token, long chatRoom) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public Iterable<UserProfileDTO> getUsersList(String token, long chatRoom) {
        throw new RuntimeException("not implemented");
    }
}
