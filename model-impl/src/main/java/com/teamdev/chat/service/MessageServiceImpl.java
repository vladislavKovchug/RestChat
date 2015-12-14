package com.teamdev.chat.service;

import com.teamdev.chat.dto.MessageDTO;
import com.teamdev.chat.entity.Message;
import com.teamdev.chat.entity.User;
import com.teamdev.chat.repository.ChatRoomRepository;
import com.teamdev.chat.repository.MessageRepository;
import com.teamdev.chat.repository.UserRepository;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Date;

public class MessageServiceImpl implements MessageService {

    UserAuthenticationService userAuthenticationService = new UserAuthenticationServiceImpl();

    ChatRoomRepository chatRoomRepository = new ChatRoomRepository();

    MessageRepository messageRepository = new MessageRepository();

    UserRepository userRepository = new UserRepository();

    @Override
    public Iterable<MessageDTO> getChatRoomMessages(String token, long chatRoomId, long time) {
        final long userId = userAuthenticationService.checkUserLogged(token);
        if (userId == -1) {
            throw new AccessControlException("Access denied.");
        }
        if (chatRoomRepository.findOne(chatRoomId) == null) {
            return new ArrayList<>();
        }

        if (time == -1) {
            time = new Date().getTime() - 300;
        }

        final Iterable<Message> allUserMessagesAfter = messageRepository.findAllUserMessagesAfter(userId,
                chatRoomId, new Date(time));
        final ArrayList<MessageDTO> messageDTOs = new ArrayList<>();
        for (Message message : allUserMessagesAfter) {

            final User userFrom = userRepository.findOne(message.getUserFromId());

            messageDTOs.add(new MessageDTO(message.getUserFromId(), userFrom != null ? userFrom.getLogin() : "",
                    message.getMessage(), false, message.getDate()));
        }

        return messageDTOs;
    }


    @Override
    public boolean sendMessage(String token, long chatRoomId, String message) {
        final long userId = userAuthenticationService.checkUserLogged(token);
        if (userId == -1) {
            throw new AccessControlException("Access denied.");
        }
        if (chatRoomRepository.findOne(chatRoomId) == null) {
            return false;
        }

        messageRepository.save(new Message(userId, -1, chatRoomId, new Date(), message));

        return true;
    }

    @Override
    public boolean sendPrivateMessage(String token, long chatRoomId, long userId) {
        throw new RuntimeException("not implemented");
    }

}
