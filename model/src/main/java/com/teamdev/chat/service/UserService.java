package com.teamdev.chat.service;


import com.teamdev.chat.dto.UserProfileDTO;

import java.util.Date;

public interface UserService {

    UserProfileDTO getUserProfile(long user);

}
