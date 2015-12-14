package com.teamdev.chat.service;

public interface UserAuthenticationService {
    void logout(long actor);
    String login(String login, String password);
    long checkUserLogged(String token);
}
