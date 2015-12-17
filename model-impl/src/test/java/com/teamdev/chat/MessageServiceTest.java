package com.teamdev.chat;


import com.teamdev.chat.dto.ChatRoomDTO;
import com.teamdev.chat.dto.MessageDTO;
import com.teamdev.chat.dto.RegisterUserDTO;
import com.teamdev.chat.dto.UserProfileDTO;
import com.teamdev.chat.factory.ServiceFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MessageServiceTest {

    private RegisterUserDTO registerUserDTO = new RegisterUserDTO("ivan", "123456", 123, new Date(1700, 10, 10));
    private UserProfileDTO testUser;
    private String testUserToken = "";
    private ChatRoomDTO chatRoom;

    private ChatRoomDTO readLastChatRoom(String token) {
        final Iterable<ChatRoomDTO> chatRoomDTOs = ServiceFactory.getChatRoomService().readAllChatRooms(token);
        ChatRoomDTO lastChatRoom = null;
        for (ChatRoomDTO chatRoomDTO : chatRoomDTOs) {
            lastChatRoom = chatRoomDTO;
        }
        return lastChatRoom;
    }

    private MessageDTO readLastMessage(String token){
        final Iterable<MessageDTO> messageDTOs = ServiceFactory.getMessageService().readChatRoomMessages(testUserToken, chatRoom.id, 0);
        MessageDTO lastMessage = null;
        for (MessageDTO messageDTO : messageDTOs) {
            lastMessage = messageDTO;
        }
        return lastMessage;
    }

    private String RegisterAndLoginAsTestUser() {
        ServiceFactory.getUserManagementService().register(registerUserDTO);
        final String token = ServiceFactory.getUserAuthenticationService().login(registerUserDTO.login, registerUserDTO.password);
        testUser = ServiceFactory.getUserService().readCurrentUserProfile(token);

        return token;
    }

    @Before
    public void before(){
        testUserToken = RegisterAndLoginAsTestUser();
        chatRoom = readLastChatRoom(testUserToken);
    }

    @After
    public void after(){
        ServiceFactory.getUserManagementService().deleteUser(testUser.id);
        testUserToken = "";
    }

    @Test
    public void testPostingMessageToChatRoom(){
        ServiceFactory.getChatRoomService().joinChatRoom(testUserToken, chatRoom.id);

        ServiceFactory.getMessageService().sendMessage(testUserToken, chatRoom.id, "test message");
        final MessageDTO lastMessage = readLastMessage(testUserToken);
        ServiceFactory.getChatRoomService().leaveChatRoom(testUserToken, chatRoom.id);

        assertEquals("last message should be from Test user", testUser.id, lastMessage.fromUserId);
        assertEquals("last message should be from Test user", testUser.name, lastMessage.fromUserName);
        assertEquals("message text should be equals", "test message", lastMessage.message);
    }

    @Test
    public void testFailsPostingMessageToNotExistingChatRoom(){
        try {
            ServiceFactory.getMessageService().sendMessage(testUserToken, 666, "test message");
            fail("Exception should be thrown.");
        } catch (Exception e) {
            assertEquals("Not correct Exception message.", "No chat room with id 666 found.",
                    e.getMessage());
        }
    }

    @Test
    public void testFailsPostingMessageToNotJoinedChatRoom(){
        try {
            ServiceFactory.getMessageService().sendMessage(testUserToken, chatRoom.id, "test message");
            fail("Exception should be thrown.");
        } catch (Exception e) {
            assertEquals("Not correct Exception message.", "Error send message to not joined chat room.",
                    e.getMessage());
        }
    }

    @Test
    public void testPostingPrivateMessage(){
        ServiceFactory.getUserManagementService().register(new RegisterUserDTO("ivan2", "1", 1, new Date()));
        final String receiverUserToken = ServiceFactory.getUserAuthenticationService().login("ivan2", "1");
        UserProfileDTO receiverUser = ServiceFactory.getUserService().readCurrentUserProfile(receiverUserToken);


        ServiceFactory.getChatRoomService().joinChatRoom(testUserToken, chatRoom.id);
        ServiceFactory.getChatRoomService().joinChatRoom(receiverUserToken, chatRoom.id);
        ServiceFactory.getMessageService().sendPrivateMessage(testUserToken, chatRoom.id,
                "test private message", receiverUser.id);
        final MessageDTO lastMessage = readLastMessage(testUserToken);
        ServiceFactory.getChatRoomService().leaveChatRoom(testUserToken, chatRoom.id);
        ServiceFactory.getChatRoomService().leaveChatRoom(receiverUserToken, chatRoom.id);

        assertEquals("last message should be from Test user", testUser.id, lastMessage.fromUserId);
        assertEquals("last message should be from Test user", testUser.name, lastMessage.fromUserName);
        assertEquals("last message should be To user with id " + Long.toString(receiverUser.id), receiverUser.id,
                lastMessage.toUserId);
        assertEquals("last message should be To user with name " + receiverUser.name, receiverUser.name,
                lastMessage.toUserName);
        assertEquals("last message should be private", true, lastMessage.privateMessage);
        assertEquals("last message should be from Test user", testUser.name, lastMessage.fromUserName);
        assertEquals("message text should be equals", "test private message", lastMessage.message);
    }

    @Test
    public void testFailPostingPrivateMessageToUserNotInChatRoom(){
        try{
            ServiceFactory.getChatRoomService().joinChatRoom(testUserToken, chatRoom.id);
            ServiceFactory.getMessageService().sendPrivateMessage(testUserToken, chatRoom.id,
                    "test private message", 1);
            ServiceFactory.getChatRoomService().leaveChatRoom(testUserToken, chatRoom.id);
            fail("Exception should be thrown.");
        } catch (Exception e){
            ServiceFactory.getChatRoomService().leaveChatRoom(testUserToken, chatRoom.id);
            assertEquals("Not correct Exception message.", "Error send message to user that not joined chat room.",
                    e.getMessage());
        }
    }

}
