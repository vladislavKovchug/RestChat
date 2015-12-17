package com.teamdev.chat;


import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.teamdev.chat.dto.ChatRoomDTO;
import com.teamdev.chat.dto.RegisterUserDTO;
import com.teamdev.chat.dto.UserProfileDTO;
import com.teamdev.chat.factory.ServiceFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ChatRoomServiceTest {

    private RegisterUserDTO registerUserDTO = new RegisterUserDTO("ivan", "123456", 123, new Date(1700, 10, 10));
    private UserProfileDTO testUser;
    private String testUserToken = "";

    private String RegisterAndLoginAsTestUser() {
        ServiceFactory.getUserManagementService().register(registerUserDTO);
        final String token = ServiceFactory.getUserAuthenticationService().login(registerUserDTO.login, registerUserDTO.password);
        testUser = ServiceFactory.getUserService().readCurrentUserProfile(token);

        return token;
    }

    private ChatRoomDTO readLastChatRoom(String token) {
        final Iterable<ChatRoomDTO> chatRoomDTOs = ServiceFactory.getChatRoomService().readAllChatRooms(token);
        ChatRoomDTO lastChatRoom = null;
        for (ChatRoomDTO chatRoomDTO : chatRoomDTOs) {
            lastChatRoom = chatRoomDTO;
        }
        return lastChatRoom;
    }

    @Before
    public void before(){
        testUserToken = RegisterAndLoginAsTestUser();
    }

    @After
    public void after(){
        ServiceFactory.getUserManagementService().deleteUser(testUser.id);
        testUserToken = "";
    }

    @Test
    public void TestAddingChatRooms() {
        ServiceFactory.getChatRoomService().addChatRoom(testUserToken, "add_chat_room");

        final ChatRoomDTO lastChatRoom = readLastChatRoom(testUserToken);
        if (lastChatRoom == null) {
            fail("Chat room wasn't added");
        }
        //assertNotEquals("Chat room wasn't added", lastChatRoom, null);

        assertEquals("last added chat room should be test_chat_room", "add_chat_room", lastChatRoom.name);
    }

    @Test
    public void TestFailOnAddingExistingChatRoom() {
        try {
            ServiceFactory.getChatRoomService().addChatRoom(testUserToken, "existing_chat_room");
            ServiceFactory.getChatRoomService().addChatRoom(testUserToken, "existing_chat_room");
            fail("Exception should be thrown.");
        } catch (RuntimeException e) {
            assertEquals("Not correct Exception message.", e.getMessage(),
                    "Error with create chat room. Chat room with name existing_chat_room already exists.");
        }
    }

    @Test
    public void TestJoinChatRoom() {
        ServiceFactory.getChatRoomService().addChatRoom(testUserToken, "test_chat_room");
        final ChatRoomDTO lastChatRoom = readLastChatRoom(testUserToken);
        ServiceFactory.getChatRoomService().joinChatRoom(testUserToken, lastChatRoom.id);

        final Iterable<UserProfileDTO> userProfileDTOs =
                ServiceFactory.getChatRoomService().readChatRoomUsersList(testUserToken, lastChatRoom.id);

        final UserProfileDTO chatRoomUser = userProfileDTOs.iterator().next();
        assertEquals("chat room user should be Test user", testUser.id, chatRoomUser.id);
        assertEquals("chat room user should be Test user", testUser.name, chatRoomUser.name);
        assertEquals("chat room user should be Test user", testUser.age, chatRoomUser.age);
        assertEquals("chat room user should be Test user", testUser.getBirthday(), chatRoomUser.getBirthday());
    }

    @Test
    public void TestFailOnJoinNotExistingChatRoom() {
        try{
            ServiceFactory.getChatRoomService().joinChatRoom(testUserToken, 666);
            fail("Exception should be thrown.");
        } catch (Exception e){
            assertEquals("Not correct Exception message.", e.getMessage(),
                    "Error with join chat room. Chat room with id 666 not found.");
        }
    }

    @Test
    public void TestFailOnJoinJoinedChatRoom() {
        try{
            final Iterable<ChatRoomDTO> chatRoomDTOs = ServiceFactory.getChatRoomService().readAllChatRooms(testUserToken);
            final ChatRoomDTO chatRoom = chatRoomDTOs.iterator().next();
            ServiceFactory.getChatRoomService().joinChatRoom(testUserToken, chatRoom.id);
            ServiceFactory.getChatRoomService().joinChatRoom(testUserToken, chatRoom.id);
            fail("Exception should be thrown.");
        } catch (Exception e){
            assertEquals("Not correct Exception message.", e.getMessage(),
                    "Error with join chat room. User is already in current chat room.");
        }
    }

    @Test
    public void TestLeaveChatRoom(){
        ServiceFactory.getChatRoomService().addChatRoom(testUserToken, "leave_chat_room");
        final ChatRoomDTO lastChatRoom = readLastChatRoom(testUserToken);
        ServiceFactory.getChatRoomService().joinChatRoom(testUserToken, lastChatRoom.id);
        ServiceFactory.getChatRoomService().leaveChatRoom(testUserToken, lastChatRoom.id);

        final Iterable<UserProfileDTO> userProfileDTOs =
                ServiceFactory.getChatRoomService().readChatRoomUsersList(testUserToken, lastChatRoom.id);

        for(UserProfileDTO userProfile : userProfileDTOs){
            if(userProfile.id.equals(testUser.id)){
                fail("Found user in leaved chat room.");
            }
        }
    }

    @Test
    public void TestFailOnLeaveNotExistingChatRoom() {
        try{
            ServiceFactory.getChatRoomService().leaveChatRoom(testUserToken, 666);
            fail("Exception should be thrown.");
        } catch (Exception e){
            assertEquals("Not correct Exception message.", e.getMessage(),
                    "Error with leave chat room. Chat room with id 666 not found.");
        }
    }

    @Test
    public void TestFailOnLeaveLeavedChatRoom() {
        try{
            final ChatRoomDTO lastChatRoom = readLastChatRoom(testUserToken);
            ServiceFactory.getChatRoomService().joinChatRoom(testUserToken, lastChatRoom.id);
            ServiceFactory.getChatRoomService().leaveChatRoom(testUserToken, lastChatRoom.id);
            ServiceFactory.getChatRoomService().leaveChatRoom(testUserToken, lastChatRoom.id);
            fail("Exception should be thrown.");
        } catch (Exception e){
            assertEquals("Not correct Exception message.", e.getMessage(),
                    "Error with leave chat room. User is not in chat room.");
        }
    }

}
