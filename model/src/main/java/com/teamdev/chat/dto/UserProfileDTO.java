package com.teamdev.chat.dto;

import java.util.Date;


public class UserProfileDTO {
    public final long id;
    public final String name;
    public final long age;
    private final Date birthday;

    public UserProfileDTO(long id, String name, long age, Date birthday) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }
}