package com.teamdev.chat.service;

import com.teamdev.chat.dto.MessageDTO;
import com.teamdev.chat.entity.ChatRoom;
import com.teamdev.chat.entity.Message;
import com.teamdev.chat.entity.User;
import com.teamdev.chat.factory.RepositoryFactory;
import com.teamdev.chat.factory.ServiceFactory;
import com.teamdev.chat.repository.*;

import java.util.ArrayList;
import java.util.Date;

public class MessageServiceImpl implements MessageService {


    ChatRoomRepository chatRoomRepository = RepositoryFactory.getChatRoomRepository();

    MessageRepository messageRepository = RepositoryFactory.getMessageRepository();

    UserRepository userRepository = RepositoryFactory.getUserRepository();

    @Override
    public Iterable<MessageDTO> readChatRoomMessages(String token, long chatRoomId, long time) {
        final long userId = ServiceFactory.getUserAuthenticationService().checkUserLogged(token);
        final ChatRoom chatRoom = chatRoomRepository.findOne(chatRoomId);
        if (chatRoom == null) {
            throw new RuntimeException("Error with getting chat room messages. Chat room with id " +
                    Long.toString(chatRoomId) + " not found.");
        }

        if (time == -1) {
            time = new Date().getTime() - 300;
        }

        final Iterable<Message> allUserMessagesAfter = messageRepository.findAllUserMessagesAfter(userId,
                chatRoomId, new Date(time));
        final ArrayList<MessageDTO> messageDTOs = new ArrayList<>();
        for (Message message : allUserMessagesAfter) {

            if(message.getUserTo() == null){
                messageDTOs.add(new MessageDTO(
                        message.getUserFrom().getId(), message.getUserFrom().getLogin(),
                        (long)-1, "",
                        message.getMessage(), false, message.getDate()));
            } else {
                messageDTOs.add(new MessageDTO(
                        message.getUserFrom().getId(), message.getUserFrom().getLogin(),
                        message.getUserTo().getId(), message.getUserTo().getLogin(),
                        message.getMessage(), true, message.getDate()));
            }
        }

        return messageDTOs;
    }


    @Override
    public void sendMessage(String token, long chatRoomId, String messageText) {
        final long userId = ServiceFactory.getUserAuthenticationService().checkUserLogged(token);
        final ChatRoom chatRoom = chatRoomRepository.findOne(chatRoomId);
        if (chatRoom == null) {
            throw new RuntimeException("No chat room with id " + Long.toString(chatRoomId) + " found.");
        }
        final User user = userRepository.findOne(userId);
        if(!chatRoom.getUsers().contains(user)){
            throw new RuntimeException("Error send message to not joined chat room.");
        }

        final Message message = new Message(user, new Date(), messageText);
        message.setChatRoom(chatRoom);
        messageRepository.save(message);
    }

    @Override
    public void sendPrivateMessage(String token, long chatRoomId, String messageText, long receiverUserId) {
        final long userFromId = ServiceFactory.getUserAuthenticationService().checkUserLogged(token);
        final ChatRoom chatRoom = chatRoomRepository.findOne(chatRoomId);
        if (chatRoom == null) {
            throw new RuntimeException("No chat room with id " + Long.toString(chatRoomId) + " found.");
        }
        final User userFrom = userRepository.findOne(userFromId);
        if(!chatRoom.getUsers().contains(userFrom)){
            throw new RuntimeException("Error send message to not joined chat room.");
        }
        final User userTo = userRepository.findOne(receiverUserId);
        if(!chatRoom.getUsers().contains(userFrom)){
            throw new RuntimeException("Error send message to user that not joined chat room.");
        }
        final Message message = new Message(userFrom, new Date(), messageText);
        message.setChatRoom(chatRoom);
        message.setUserTo(userTo);
        messageRepository.save(message);
    }

}
