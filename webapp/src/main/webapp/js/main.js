$(function(){
    var loginToken = "";

    var loginForm = $("#login-form");
    loginForm.find("#login-btn").click(function(){
        var login = $("#login").val();
        var pass = $("#pass").val();

        console.log('try to log in: ' + login + ':' + pass);
        loginToChat(login, pass);
    });


    function loginToChat(login, pass){
        $.ajax({
            url: "/rest/login",
            data: {"login" : login, "password" : pass},
            type: "POST",
            beforeSend: function(xhr){xhr.setRequestHeader('Login-token', loginToken);},
            success : function(data){ console.log(data); loginToken = JSON.parse(data).token; getChatRooms(); }
        })
    }

    function getChatRooms(){
        $.ajax({
            url: "/rest/chatrooms",
            type: "GET",
            beforeSend: function(xhr){xhr.setRequestHeader('Login-token', loginToken);},
            success : function(data){
                console.log(data);
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
            }
        });
    }

});
