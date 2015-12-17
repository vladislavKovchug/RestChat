package com.teamdev.chat.factory;


import com.teamdev.chat.service.*;

public class ServiceFactory {
    private static final ChatRoomService chatRoomService = new ChatRoomServiceImpl();
    private static final MessageService messageService = new MessageServiceImpl();
    private static final UserAuthenticationService userAuthenticationService = new UserAuthenticationServiceImpl();
    private static final UserManagementService userManagementService = new UserManagementServiceImpl();
    private static final UserService userService = new UserServiceImpl();

    public static ChatRoomService getChatRoomService() {
        return chatRoomService;
    }

    public static MessageService getMessageService() {
        return messageService;
    }

    public static UserAuthenticationService getUserAuthenticationService() {
        return userAuthenticationService;
    }

    public static UserManagementService getUserManagementService() {
        return userManagementService;
    }

    public static UserService getUserService() {
        return userService;
    }
}
