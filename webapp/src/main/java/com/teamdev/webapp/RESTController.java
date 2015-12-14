package com.teamdev.webapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teamdev.chat.dto.ChatRoomDTO;
import com.teamdev.chat.service.ChatRoomService;
import com.teamdev.chat.service.ChatRoomServiceImpl;
import com.teamdev.chat.service.UserAuthenticationService;
import com.teamdev.chat.service.UserAuthenticationServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

@Path("/")
public class RESTController {

    UserAuthenticationService userAuthenticationService = new UserAuthenticationServiceImpl();

    ChatRoomService chatRoomService = new ChatRoomServiceImpl();

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

        Long.parseLong(formData.get("id").size() > 0 ? formData.get("id").get(0) : "-1");

        return "error";
    }

}
