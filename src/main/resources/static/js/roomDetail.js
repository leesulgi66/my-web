let index = {
    init: function() {
        let roomId = $("#chat-room-id").val();
        let roomName = $("#chat-room-name").text();
        let username = $("#chat-sender").text();
        console.log(roomId+","+roomName+","+username);

        var sockJs = new SockJS("/ws");
        //1. SockJS를 내부에 들고있는 stomp를 내어줌
        var stomp = Stomp.over(sockJs);

        // STOMP 구독
        stomp.connect({}, function(frame) {
            console.log("connected: " + frame);
            stomp.subscribe("/sub/channel/"+roomId, function(message) {
                var content = JSON.parse(message.body);

                var writer = content.sender;
                var str = '';

                if(writer === username){
                   str = "<div class='input-group'>";
                   str += "<div class='alert alert-secondary'>";
                   str += "<b>" + writer + " : " + content.message + "</b>";
                   str += "</div></div>";
                   $("#msgArea").append(str);
               }
               else{
                   str = "<div class='input-group'>";
                   str += "<div class='alert alert-warning'>";
                   str += "<b>" + writer + " : " + content.message + "</b>";
                   str += "</div></div>";
                   $("#msgArea").append(str);
               }

                console.log(content);
            });
            stomp.send("/pub/chat/message", {}, JSON.stringify({type:'ENTER', roomId:roomId, sender:username}));
        }, function(error) {
            alert("error "+error);
        });

        $("#btn-send-chat").on("click", function(e){
            var msg = document.getElementById("chat-message");
            if(msg == "") {
                return false;
            }

            console.log(username + ":" + msg.value);
            stomp.send('/pub/chat/message', {}, JSON.stringify({roomId: roomId, type: "TALK", message: msg.value, sender: username}));
            msg.value = '';
        });

        $("#chat-message").on("keydown", (key)=>{
            if(key.keyCode==13) {
                var msg = document.getElementById("chat-message");
                if(msg == "") {
                    return false;
                }

                console.log(username + ":" + msg.value);
                stomp.send('/pub/chat/message', {}, JSON.stringify({roomId: roomId, type: "TALK", message: msg.value, sender: username}));
                msg.value = '';
            }
        });

    },
}

index.init();