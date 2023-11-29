let index = {
    init: function() {
        let objDiv = document.getElementById("msgArea");
        objDiv.scrollTop = objDiv.scrollHeight;

        let roomId = $("#chat-room-id").val();
        let roomName = $("#chat-room-name").text();
        let username = $("#chat-sender").text();

        var sockJs = new SockJS("/ws");
        //1. SockJS를 내부에 들고있는 stomp를 내어줌
        var stomp = Stomp.over(sockJs);

        // STOMP 구독
        stomp.connect({}, function(frame) {
            stomp.subscribe("/sub/channel/"+roomId, function(message) {
                var content = JSON.parse(message.body);

                var writer = content.sender;
                var str = '';

                if(writer === username){
                   str = "<div class='input-group-text'>";
                   str += "<div class='alert-secondary'>";
                   str += "<p>" + writer + " : " + content.message + "</p>";
                   str += "</div></div>";
                   $("#msgArea").append(str);
               }
               else{
                   str = "<div class='input-group-text'>";
                   str += "<div class='alert-warning'>";
                   str += "<p>" + writer + " : " + content.message + "</p>";
                   str += "</div></div>";
                   $("#msgArea").append(str);
               }

               let objDiv = document.getElementById("msgArea");
               objDiv.scrollTop = objDiv.scrollHeight;
            });
            stomp.send("/pub/chat/message", {}, JSON.stringify({type:'ENTER', roomId:roomId, sender:username}));
        }, function(error) {
            alert("error "+error);
        });

        $("#btn-send-chat").on("click", function(e){
            var msg = document.getElementById("chat-message");
            if(msg.value == "") {
                return false;
            }

            stomp.send('/pub/chat/message', {}, JSON.stringify({roomId: roomId, type: "TALK", message: msg.value, sender: username}));
            msg.value = '';
        });

        $("#chat-message").on("keydown", (key)=>{
            if(key.keyCode==13) {
                var msg = document.getElementById("chat-message");
                if(msg.value == "") {
                    return false;
                }

                stomp.send('/pub/chat/message', {}, JSON.stringify({roomId: roomId, type: "TALK", message: msg.value, sender: username}));
                msg.value = '';
            }
        });

        $("#btn-room-out").on("click", ()=>{
            if (!confirm("정말 채팅방을 나갈까요?")) {
                return false;
            }else {
                stomp.send('/pub/chat/message', {}, JSON.stringify({roomId: roomId, type: "QUIT", sender: username}));
                stomp.disconnect("/sub/channel/"+roomId);
                this.outRoom();
            }
        });

        $("#btn-room-del").on("click", ()=>{
            if (!confirm("정말 채팅방을 삭제 할까요?")) {
                return false;
            }else {
                stomp.disconnect("/sub/channel/"+roomId);
                this.deleteRoom();
            }
        });

    },

    outRoom : function() {
        let data = {
            roomId : $("#chat-room-id").val(),
        }

        $.ajax({
            type:"DELETE",
            url: "/chat/room/out",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                    console.log("success : ")
                    console.log(JSON.stringify(data))
                },
            error: function (request, status, error) {
                console.log("code: " + request.status)
                console.log("message: " + request.responseText)
                console.log("error: " + error);
            }
        }).done(function(resp){
            console.log("done : "+ JSON.stringify(resp));
            alert("채팅방을 나갑니다.");
            location.href="/chat/room";
        }).fail(function(error){
            console.log("error : "+error);
        });
    },

    deleteRoom : function() {
        let data = {
            roomId : $("#chat-room-id").val(),
        }

        $.ajax({
            type:"DELETE",
            url: "/chat/room/delete",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                    console.log("success : ")
                    console.log(JSON.stringify(data))
                },
            error: function (request, status, error) {
                console.log("code: " + request.status)
                console.log("message: " + request.responseText)
                console.log("error: " + error);
            }
        }).done(function(resp){
            console.log("done : "+ JSON.stringify(resp));
            alert("채팅방을 삭제합니다.");
            location.href="/chat/room";
        }).fail(function(error){
            console.log("error : "+error);
        });
    },
}

index.init();