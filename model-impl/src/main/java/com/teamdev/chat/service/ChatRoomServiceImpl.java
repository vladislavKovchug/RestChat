package com.teamdev.chat.service;

import com.teamdev.chat.dto.ChatRoomDTO;
import com.teamdev.chat.dto.UserProfileDTO;
import com.teamdev.chat.entity.ChatRoom;
import com.teamdev.chat.entity.User;
import com.teamdev.chat.repository.ChatRoomRepository;
import com.teamdev.chat.repository.ChatRoomRepositoryImpl;
import com.teamdev.chat.repository.UserRepository;
import com.teamdev.chat.repository.UserRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ChatRoomServiceImpl implements ChatRoomService {

    UserAuthenticationService userAuthenticationService = new UserAuthenticationServiceImpl();

    ChatRoomRepository chatRoomRepository = new ChatRoomRepositoryImpl();

    UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public Iterable<ChatRoomDTO> readAllChatRooms(String token) {
        userAuthenticationService.checkUserLogged(token);

        final List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        List<ChatRoomDTO> result = new ArrayList<>();
        for (ChatRoom chatRoom : chatRooms){
            result.add(new ChatRoomDTO(chatRoom.getId(), chatRoom.getName()));
        }
        return result;
    }

    @Override
    public void joinChatRoom(String token, long chatRoomId) {
        final long userId = userAuthenticationService.checkUserLogged(token);
        final ChatRoom chatRoom = chatRoomRepository.findOne(chatRoomId);
        if (chatRoom == null) {
            throw new RuntimeException("Error with join chat room. Chat room with id " + Long.toString(chatRoomId) + " not found.");
        }

        final User user = userRepository.findOne(userId);
        user.addChatRoom(chatRoom);
        userRepository.save(user);
    }

    @Override
    public void leaveChatRoom(String token, long chatRoomId) {
        final long userId = userAuthenticationService.checkUserLogged(token);
        final ChatRoom chatRoom = chatRoomRepository.findOne(chatRoomId);
        if (chatRoom == null) {
            throw new RuntimeException("Error with leave chat room. Chat room with id " + Long.toString(chatRoomId) + " not found.");
        }

        final User user = userRepository.findOne(userId);
        user.removeChatRoom(chatRoom);
        userRepository.save(user);
    }

    @Override
    public Iterable<UserProfileDTO> readChatRoomUsersList(String token, long chatRoomId) {
        final long userId = userAuthenticationService.checkUserLogged(token);
        final ChatRoom chatRoom = chatRoomRepository.findOne(chatRoomId);
        if (chatRoom == null) {
            throw new RuntimeException("Error with leave chat room. Chat room with id " + Long.toString(chatRoomId) + " not found.");
        }

        final Set<User> chatRoomUsers = chatRoom.getUsers();
        final ArrayList<UserProfileDTO> userProfileDTOs = new ArrayList<>();
        for(User user : chatRoomUsers){
            userProfileDTOs.add(new UserProfileDTO(user.getId(), user.getLogin(), user.getAge(), user.getBirthday()));
        }
        return userProfileDTOs;
    }
}
