package com.teamdev.chat;


import com.teamdev.chat.dto.RegisterUserDTO;
import com.teamdev.chat.dto.UserProfileDTO;
import com.teamdev.chat.factory.ServiceFactory;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UserManagementServiceTest {

    @Test
    public void testUserRegister(){
        final RegisterUserDTO newUser = new RegisterUserDTO("new_user", "12345", 12, new Date(1900, 10, 11));
        ServiceFactory.getUserManagementService().register(newUser);
        final String token = ServiceFactory.getUserAuthenticationService().login(newUser.login, newUser.password);
        final UserProfileDTO userProfile = ServiceFactory.getUserService().readCurrentUserProfile(token);

        assertEquals("User name changed after register.", newUser.login, userProfile.name);
        assertEquals("User age changed after register.", newUser.age, userProfile.age);
        assertEquals("User birthday changed after register.", newUser.getBirthday(), userProfile.getBirthday());
    }

    @Test
    public void testUserRegisterFailOnRegisterExistedUser(){
        try {
            final RegisterUserDTO newUser = new RegisterUserDTO("user1", "12345", 12, new Date(1900, 10, 11));
            ServiceFactory.getUserManagementService().register(newUser);
            fail("Exception should be thrown.");
        } catch (Exception e) {
            assertEquals("Not correct Exception message.", "User user1 already exists.", e.getMessage());
        }
    }

    @Test
    public void testUserDelete(){
        final RegisterUserDTO newUser = new RegisterUserDTO("user_for_delete", "12345", 12, new Date(1900, 10, 11));
        ServiceFactory.getUserManagementService().register(newUser);
        final String token = ServiceFactory.getUserAuthenticationService().login(newUser.login, newUser.password);
        final long userId = ServiceFactory.getUserAuthenticationService().readCurrentUserId(token);
        ServiceFactory.getUserManagementService().deleteUser(userId);

        try{
            ServiceFactory.getUserAuthenticationService().readCurrentUserId(token);
            fail("Exception should be thrown.");
        } catch (Exception e){
            assertEquals("Not correct Exception message.", "Access denied.", e.getMessage());
        }
    }

    @Test
    public void testUserDeleteFailOnDeleteNotExistedUser(){
        try {
            ServiceFactory.getUserManagementService().deleteUser(666);
            fail("Exception should be thrown.");
        } catch (Exception e) {
            assertEquals("Not correct Exception message.", "User with id 666 does not exists.", e.getMessage());
        }
    }

}
