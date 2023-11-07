let index = {
    init: function() {
        $("#btn-create-room").on("click", ()=>{  // 화살표 함수 -> JavaScript 함수의 this 바인딩
            this.createRoom();
        });
    },

    createRoom: function() {
        let data = {
            name : $("#room_name").val()
        }
        console.log(data);

        if(data.name == "") {
            alert("채팅방 제목을 입력해 주세요");
            return;
        }

        $.ajax({
            type:"POST",
            url: "/chat/room",
            data: JSON.stringify(data),  // body date
            contentType: "application/json; charset=utf-8", // dody data type(MIME)
            dataType: "json", // reponse가 json 형식이라면 javascript로 변경해줌
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
            location.reload();
        }).fail(function(error){
            console.log("error : "+error);
        });
    },

}

index.init();