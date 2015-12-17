package com.teamdev.chat.service;


import com.teamdev.chat.dto.UserProfileDTO;

import java.util.Date;

public interface UserService {

    UserProfileDTO readUserProfile(String token, long userId);

    UserProfileDTO readCurrentUserProfile(String token);

    Iterable<UserProfileDTO> readAllUsersProfile(String token);


}
