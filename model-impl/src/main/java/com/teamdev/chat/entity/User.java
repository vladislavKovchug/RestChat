package com.teamdev.chat.entity;


import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class User implements DatabaseEntity {
    private long id;
    private String login;
    private String passwordHash;
    private long age;
    private Date birthday;
    private Set<ChatRoom> chatRooms = new LinkedHashSet<>();
    private Set<Message> messages = new LinkedHashSet<>();

    public User() {
        this.id = -1;
    }

    public User(String login, String passwordHash, long age, Date birthday) {
        this.id = -1;
        this.login = login;
        this.passwordHash = passwordHash;
        this.age = age;
        this.birthday = birthday;
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

    public Set<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void addChatRoom(ChatRoom chatRoom) {
        if (chatRooms.add(chatRoom)) {
            chatRoom.addUser(this);
        }

    }

    public void removeChatRoom(ChatRoom chatRoom) {
        if (chatRooms.remove(chatRoom)) {
            chatRoom.removeUser(this);
        }
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        if (messages.add(message)) {
            message.setUserTo(this);
        }
    }

    public void removeMessage(Message message) {
        if (messages.remove(message)) {
            message.setUserTo(null);
        }
    }

    @Override
    public void removeDependencies() {
        for (Message message : new LinkedHashSet<>(messages)) {
            message.setUserTo(null);
        }
        for (ChatRoom chatRoom : new LinkedHashSet<>(chatRooms)) {
            chatRoom.removeUser(this);
        }
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
