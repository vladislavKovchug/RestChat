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
import java.util.Iterator;

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

        assertEquals("last message should be from Test user", lastMessage.fromUserId, testUser.id);
        assertEquals("last message should be from Test user", lastMessage.fromUserName, testUser.name);
        assertEquals("message text should be equals", lastMessage.message, "test message");
    }

    @Test
    public void testFailsPostingMessageToNotExistingChatRoom(){
        try {
            ServiceFactory.getMessageService().sendMessage(testUserToken, 666, "test message");
            fail("Exception should be thrown.");
        } catch (Exception e) {
            assertEquals("Not correct Exception message.", e.getMessage(),
                    "No chat room with id 666 found.");
        }
    }

    @Test
    public void testFailsPostingMessageToNotJoinedChatRoom(){
        try {
            ServiceFactory.getMessageService().sendMessage(testUserToken, chatRoom.id, "test message");
            fail("Exception should be thrown.");
        } catch (Exception e) {
            assertEquals("Not correct Exception message.", e.getMessage(),
                    "Error send message to not joined chat room.");
        }
    }

    @Test
    public void testPostingPrivateMessages(){
        ServiceFactory.getChatRoomService().joinChatRoom(testUserToken, chatRoom.id);

        ServiceFactory.getMessageService().sendPrivateMessage(testUserToken, chatRoom.id,
                "test private message", 1);
        final MessageDTO lastMessage = readLastMessage(testUserToken);
        ServiceFactory.getChatRoomService().leaveChatRoom(testUserToken, chatRoom.id);

        assertEquals("last message should be from Test user", lastMessage.fromUserId, testUser.id);
        assertEquals("last message should be from Test user", lastMessage.fromUserName, testUser.name);
        assertEquals("last message should be To user with id 1", lastMessage.toUserId, new Long(1));
        assertEquals("last message should be private", lastMessage.privateMessage, true);
        assertEquals("last message should be from Test user", lastMessage.fromUserName, testUser.name);
        assertEquals("message text should be equals", lastMessage.message, "test private message");
    }

}
