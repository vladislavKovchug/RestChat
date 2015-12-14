package com.teamdev.chat.entity;


import java.util.Date;

public class User implements DatabaseEntity {
    private long id;
    private String login;
    private String passwordHash;
    private long age;
    private Date birthday;
    private long chatRoomId;

    public User() {
        this.id = -1;
    }

    public User(String login, String passwordHash, long age, Date birthday, long chatRoomId) {
        this.id = -1;
        this.login = login;
        this.passwordHash = passwordHash;
        this.age = age;
        this.birthday = birthday;
        this.chatRoomId = chatRoomId;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public long getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (age != user.age) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (passwordHash != null ? !passwordHash.equals(user.passwordHash) : user.passwordHash != null) return false;
        return !(birthday != null ? !birthday.equals(user.birthday) : user.birthday != null);

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
