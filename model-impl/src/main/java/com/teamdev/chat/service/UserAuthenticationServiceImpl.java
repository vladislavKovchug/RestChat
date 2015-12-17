package com.teamdev.chat.service;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.teamdev.chat.entity.User;
import com.teamdev.chat.repository.UserRepository;
import com.teamdev.chat.repository.UserRepositoryImpl;

import java.security.AccessControlException;
import java.time.Instant;
import java.util.List;

public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    UserRepository userRepository = new UserRepositoryImpl();

    public String login(String login, String password) {
        HashFunction hf = Hashing.sha256();
        final String passwordHash = hf.newHasher().putString(password, Charsets.UTF_8).hash().toString();
        final List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPasswordHash().equals(passwordHash)) {
                return Long.toString(user.getId()) + "-" +
                        passwordHash + '-' +
                        Long.toString(Instant.now().getEpochSecond() + 300);
            }
        }
        throw new AccessControlException("Access denied.");
    }

    public long checkUserLogged(String token) {

        final List<String> strings = Splitter.on("-").splitToList(token);
        if (strings.size() < 3) {
            throw new AccessControlException("Access denied.");
        }

        final long id = Long.parseLong(strings.get(0));
        final String passwordHash = strings.get(1);
        final long timeout = Long.parseLong(strings.get(2));
        if (timeout < Instant.now().getEpochSecond()) {
            throw new AccessControlException("Access denied.");
        }

        final List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getId() == id && user.getPasswordHash().equals(passwordHash)) {
                return id;
            }
        }

        throw new AccessControlException("Access denied.");
    }

}
