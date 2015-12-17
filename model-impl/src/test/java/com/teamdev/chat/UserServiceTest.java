package com.teamdev.chat;

import com.teamdev.chat.dto.RegisterUserDTO;
import com.teamdev.chat.dto.UserProfileDTO;
import com.teamdev.chat.factory.ServiceFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UserServiceTest {
    private RegisterUserDTO registerUserDTO = new RegisterUserDTO("not_ivan", "123456", 123, new Date(1700, 10, 10));
    private UserProfileDTO testUser;
    private String testUserToken = "";

    private String RegisterAndLoginAsTestUser() {
        ServiceFactory.getUserManagementService().register(registerUserDTO);
        final String token = ServiceFactory.getUserAuthenticationService().login(registerUserDTO.login, registerUserDTO.password);
        testUser = ServiceFactory.getUserService().readCurrentUserProfile(token);

        return token;
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
    public void testReadUserProfile(){
        final RegisterUserDTO newUser = new RegisterUserDTO("read_user", "pass", 123, new Date());
        ServiceFactory.getUserManagementService().register(newUser);
        final String token = ServiceFactory.getUserAuthenticationService().login("read_user", "pass");
        final long userId = ServiceFactory.getUserAuthenticationService().readCurrentUserId(token);

        final UserProfileDTO userProfile = ServiceFactory.getUserService().readUserProfile(testUserToken, userId);
        ServiceFactory.getUserManagementService().deleteUser(userId);

        assertEquals("Wrong user name.", newUser.login, userProfile.name);
        assertEquals("Wrong user age.", newUser.age, userProfile.age);
        assertEquals("Wrong user birthday.", newUser.getBirthday(), userProfile.getBirthday());
    }

    @Test
    public void testReadUserProfileFailsOnReadProfileNotExistedUser(){
        try {
            ServiceFactory.getUserService().readUserProfile(testUserToken, 666);
            fail("Exception should be thrown.");
        } catch (Exception e) {
            assertEquals("Not correct Exception message.", "No user found with id 666.", e.getMessage());
        }
    }

    @Test
    public void testReadCurrentUserProfile(){
        final UserProfileDTO userProfile = ServiceFactory.getUserService().readCurrentUserProfile(testUserToken);

        assertEquals("Wrong user name.", registerUserDTO.login, userProfile.name);
        assertEquals("Wrong user age.", registerUserDTO.age, userProfile.age);
        assertEquals("Wrong user birthday.", registerUserDTO.getBirthday(), userProfile.getBirthday());
    }

}
