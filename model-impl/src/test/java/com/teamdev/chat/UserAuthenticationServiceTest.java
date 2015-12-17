package com.teamdev.chat;

import com.google.common.base.Splitter;
import com.teamdev.chat.factory.ServiceFactory;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UserAuthenticationServiceTest {

    @Test
    public void testUserLogin(){
        final String token = ServiceFactory.getUserAuthenticationService().login("user1", "12345");
        String passwordHash = "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5";

        final List<String> strings = Splitter.on("-").splitToList(token);
        assertEquals("login token id don't match", 1, Long.parseLong(strings.get(0)));
        assertEquals("login token passwordHash don't match", passwordHash, strings.get(1));
        assertEquals("login token time don't match", Instant.now().getEpochSecond() + 60 * 15,
                Long.parseLong(strings.get(2)));
    }

    @Test
    public void testFailsOnUserLoginWithBadCredential() {
        try{
            final String token = ServiceFactory.getUserAuthenticationService().login("admin", "admin");
            fail("Exception should be thrown.");
        } catch (Exception e){
            assertEquals("Not correct Exception message.", "Access denied.", e.getMessage());
        }
    }

    @Test
    public void testReadCurrentUserId(){
        final String token = ServiceFactory.getUserAuthenticationService().login("user1", "12345");
        final long userId = ServiceFactory.getUserAuthenticationService().readCurrentUserId(token);
        Assert.assertEquals("Wrong readed userId from token.", 1, userId);
    }

    @Test
    public void testFailsOnReadCurrentUserIdWithBadToken() {
        try{
            ServiceFactory.getUserAuthenticationService().readCurrentUserId("1-123-123");
            fail("Exception should be thrown.");
        } catch (Exception e){
            assertEquals("Not correct Exception message.", "Access denied.", e.getMessage());
        }
    }

}
