package com.teamdev.chat.service;

import com.teamdev.chat.dto.ChatRoomDTO;
import com.teamdev.chat.dto.UserProfileDTO;
import com.teamdev.chat.entity.ChatRoom;
import com.teamdev.chat.entity.User;
import com.teamdev.chat.factory.RepositoryFactory;
import com.teamdev.chat.factory.ServiceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ChatRoomServiceImpl implements ChatRoomService {

    @Override
    public Iterable<ChatRoomDTO> readAllChatRooms(String token) {
        ServiceFactory.getUserAuthenticationService().readCurrentUserId(token);

        final List<ChatRoom> chatRooms = RepositoryFactory.getChatRoomRepository().findAll();
        List<ChatRoomDTO> result = new ArrayList<>();
        for (ChatRoom chatRoom : chatRooms){
            result.add(new ChatRoomDTO(chatRoom.getId(), chatRoom.getName()));
        }
        return result;
    }

    @Override
    public void addChatRoom(String token, String chatRoomName) {
        ServiceFactory.getUserAuthenticationService().readCurrentUserId(token);
        if(RepositoryFactory.getChatRoomRepository().findChatRoomByName(chatRoomName) != null){
            throw new RuntimeException("Error with create chat room. Chat room with name " + chatRoomName +
                    " already exists.");
        }
        RepositoryFactory.getChatRoomRepository().save(new ChatRoom(chatRoomName));
    }

    @Override
    public void joinChatRoom(String token, long chatRoomId) {
        final long userId = ServiceFactory.getUserAuthenticationService().readCurrentUserId(token);
        final ChatRoom chatRoom = RepositoryFactory.getChatRoomRepository().findOne(chatRoomId);
        if (chatRoom == null) {
            throw new RuntimeException("Error with join chat room. Chat room with id " + Long.toString(chatRoomId) +
                    " not found.");
        }

        final User user = RepositoryFactory.getUserRepository().findOne(userId);
        if(chatRoom.getUsers().contains(user)){
            throw new RuntimeException("Error with join chat room. User is already in current chat room.");
        }

        user.addChatRoom(chatRoom);
        RepositoryFactory.getUserRepository().save(user);
    }

    @Override
    public void leaveChatRoom(String token, long chatRoomId) {
        final long userId = ServiceFactory.getUserAuthenticationService().readCurrentUserId(token);
        final ChatRoom chatRoom = RepositoryFactory.getChatRoomRepository().findOne(chatRoomId);
        if (chatRoom == null) {
            throw new RuntimeException("Error with leave chat room. Chat room with id " + Long.toString(chatRoomId) +
                    " not found.");
        }

        final User user = RepositoryFactory.getUserRepository().findOne(userId);
        if(!chatRoom.getUsers().contains(user)){
            throw new RuntimeException("Error with leave chat room. User is not in chat room.");
        }
        user.removeChatRoom(chatRoom);
        RepositoryFactory.getUserRepository().save(user);
    }

    @Override
    public Iterable<UserProfileDTO> readChatRoomUsersList(String token, long chatRoomId) {
        final long userId = ServiceFactory.getUserAuthenticationService().readCurrentUserId(token);
        final ChatRoom chatRoom = RepositoryFactory.getChatRoomRepository().findOne(chatRoomId);
        if (chatRoom == null) {
            throw new RuntimeException("Error with read chat room users. Chat room with id " + Long.toString(chatRoomId) +
                    " not found.");
        }

        final Set<User> chatRoomUsers = chatRoom.getUsers();
        final ArrayList<UserProfileDTO> userProfileDTOs = new ArrayList<>();
        for(User user : chatRoomUsers){
            userProfileDTOs.add(new UserProfileDTO(user.getId(), user.getLogin(), user.getAge(), user.getBirthday()));
        }
        return userProfileDTOs;
    }
}
