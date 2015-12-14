$(function(){
    var loginToken = "";
    var currentChatRoom = undefined;
    var lastMessagesUpdate = 0;

    var loginForm = $("#login-form");
    loginForm.find("#login-btn").click(function(){
        var login = loginForm.find("#login").val();
        var pass = loginForm.find("#pass").val();

        console.log('try to log in: ' + login + ':' + pass);
        loginToChat(login, pass);
    });

    var postMessageForm = $("#message-form");
    postMessageForm.find("#send-message-btn").click(function(){
        var message = postMessageForm.find("#message").val();

        console.log('Sending message: ' + message);
        sendMessage(message);
    });


    function loginToChat(login, pass){
        $.ajax({
            url: "/rest/login",
            data: {"login" : login, "password" : pass},
            type: "POST",
            beforeSend: function(xhr){xhr.setRequestHeader('Login-token', loginToken);},
            success : function(data){

                console.log(data);
                data = JSON.parse(data);
                if(data.token != "error"){
                    loginToken = data.token;
                    getChatRooms();
                }
            }
        })
    }

    function getChatRooms(){
        $.ajax({
            url: "/rest/chatrooms",
            type: "GET",
            beforeSend: function(xhr){xhr.setRequestHeader('Login-token', loginToken);},
            success : function(data){
                console.log(data);
                $("#chat-view").show();
                var chatRooms = JSON.parse(data);

                for(var i=0; i<chatRooms.length; i++){
                    var chatRoomBtn = $("<button/>", {
                        "text" : chatRooms[i].name
                    });
                    var chatRoom = chatRooms[i];
                    chatRoomBtn.click(function(){ joinChatRoom(chatRoom) });
                    $("#chat-rooms").append(chatRoomBtn);
                }

            }
        });
    }


    function joinChatRoom(chatRoom){
        $.ajax({
            url: "/rest/join",
            type: "POST",
            data: {"id" : chatRoom.id},
            beforeSend: function(xhr){xhr.setRequestHeader('Login-token', loginToken);},
            success : function(data){
                console.log(data);
                if(data == "success"){
                    currentChatRoom = chatRoom.id;
                    console.log(currentChatRoom);
                    window.setTimeout(getMessages, 1000);
                }
            }
        });
    }

    function getMessages(normalCall){
        if(!currentChatRoom){
            return;
        }

        $.ajax({
            url: "/rest/get-messages/" + currentChatRoom + "/" + lastMessagesUpdate,
            type: "GET",
            beforeSend: function(xhr){xhr.setRequestHeader('Login-token', loginToken);},
            success : updateMessages
        });

        if(!normalCall){
            window.setTimeout(getMessages, 3000);
        }
    }

    function updateMessages(data){
        console.log(data);
        var messages = JSON.parse(data);
        for(var i=0; i<messages.length; i++){
            var messageContainer = $('<div>');
            messageContainer.append('&lt;' + messages[i].date + '&gt;<b>' + messages[i].fromUserName + ':</b> ');
            var messageText = messages[i].message.replace('\n', '<br/>');
            messageContainer.append(messageText);
            $("#chat").append(messageContainer);
        }

        lastMessagesUpdate = Date.now();
    }

    function sendMessage(message){
        if(currentChatRoom){
            $.ajax({
                url: "/rest/send-message",
                type: "POST",
                data: {"message" : message, "chatRoomId" : currentChatRoom},
                beforeSend: function(xhr){xhr.setRequestHeader('Login-token', loginToken);},
                success : function(data){
                    console.log(data);
                    if(data == "success"){
                        getMessages(true);
                    }
                }
            });
        }
    }

});
