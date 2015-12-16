package com.teamdev.webapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.teamdev.chat.dto.ChatRoomDTO;
import com.teamdev.chat.dto.MessageDTO;
import com.teamdev.chat.dto.RegisterUserDTO;
import com.teamdev.chat.service.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Path("/")
public class RESTController {

    UserAuthenticationService userAuthenticationService = new UserAuthenticationServiceImpl();

    ChatRoomService chatRoomService = new ChatRoomServiceImpl();

    MessageService messageService = new MessageServiceImpl();

    UserManagementService userManagementService = new UserManagementServiceImpl();

    @Path("login")
    @POST
    @Consumes( { MediaType.APPLICATION_FORM_URLENCODED })
    public String login(MultivaluedMap<String, String> formData) {
        String login = formData.get("login").size() > 0 ? formData.get("login").get(0) : "";
        String password = formData.get("password").size() > 0 ? formData.get("password").get(0) : "";
        String token = userAuthenticationService.login(login, password);

        final JsonObject response = new JsonObject();
        response.addProperty("token", token);
        return new GsonBuilder().create().toJson(response);
    }

    @Path("chatrooms")
    @GET
    public String getChatRooms(@HeaderParam("Login-token") String token) {
        final Gson gson = new Gson();
        final Iterable<ChatRoomDTO> chatRooms = chatRoomService.getChatRooms(token);

        return gson.toJson(chatRooms);
    }

    @Path("join")
    @POST
    @Consumes( { MediaType.APPLICATION_FORM_URLENCODED })
    public String joinChatRoom(MultivaluedMap<String, String> formData, @HeaderParam("Login-token") String token) {
        final long chatRoomId = Long.parseLong(formData.get("id").size() > 0 ? formData.get("id").get(0) : "-1");
        final boolean result = chatRoomService.joinChatRoom(token, chatRoomId);
        return result ? "success" : "fail";
    }

    @GET
    @Path("get-messages/{chatRoom}/{time}")
    public String getMessages(@HeaderParam("Login-token") String token, @PathParam("chatRoom") long chatRoom, @PathParam("time") long time) {
        final Gson gson = new Gson();
        final Iterable<MessageDTO> chatRoomMessages = messageService.getChatRoomMessages(token, chatRoom, time);

        return gson.toJson(chatRoomMessages);
    }

    @Path("send-message")
    @POST
    @Consumes( { MediaType.APPLICATION_FORM_URLENCODED })
    public String sendMessage(MultivaluedMap<String, String> formData, @HeaderParam("Login-token") String token) {
        final long chatRoomId = Long.parseLong(formData.get("chatRoomId").size() > 0 ? formData.get("chatRoomId").get(0) : "-1");
        String message = formData.get("message").size() > 0 ? formData.get("message").get(0) : "";

        final boolean result = messageService.sendMessage(token, chatRoomId, message);
        return result ? "success" : "fail";
    }

    @Path("send-message")
    @POST
    @Consumes( { MediaType.APPLICATION_FORM_URLENCODED })
    public String registerUser(MultivaluedMap<String, String> formData) {
        //new
        String login = formData.get("login").size() > 0 ? formData.get("login").get(0) : "";
        String password = formData.get("password").size() > 0 ? formData.get("password").get(0) : "";
        final long age = Long.parseLong(formData.get("age").size() > 0 ? formData.get("age").get(0) : "-1");
        String birthday = formData.get("birthday").size() > 0 ? formData.get("birthday").get(0) : "";
        DateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.CHINA);
        Date birthdayDate = new Date();
        try {
            birthdayDate = format.parse(birthday);
        } catch (ParseException e) {

        }
        final RegisterUserDTO registerUserDTO = new RegisterUserDTO(login, password, age, birthdayDate);
        userManagementService.register(registerUserDTO);

        return "success";
    }

}
