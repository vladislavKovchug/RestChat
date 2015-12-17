$(function() {
    var loginToken = "";
    var currentChatRoom = undefined;
    var lastMessagesUpdate = 0;

    var registerForm = $("#registration-form");
    registerForm.find("#register-btn").click(function () {
        var login = registerForm.find("#login").val();
        var pass = registerForm.find("#pass").val();
        var age = registerForm.find("#age").val();
        var birthday = registerForm.find("#birthday").val();

        registerUser(login, pass, age, birthday);
    });

    function registerUser(login, pass, age, birthday) {
        $.ajax({
            url: "/rest/register",
            data: {"login" : login, "password" : pass, "age" : age, "birthday" : birthday},
            type: "POST",
            success : function(data){
                console.log(data);
                if(data == "success"){

                } else {
                    alert("Failed to register new user, try again later");
                }
            }
        })
    }

});