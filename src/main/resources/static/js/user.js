let index = {
    init: function() {
        $("#btn-save").on("click", ()=>{  // 화살표 함수 -> JavaScript 함수의 this 바인딩
            this.save();
        })
    },

    save: function() {
        let data = {
            username : $("#username").val(),
            nickname : $("#nickname").val(),
            password : $("#password").val(),
            password_re : $("#password-re").val(),
            email : $("#email").val(),
        }

        $.ajax({
            type:"POST",
            url: "/web/api/user/",
            data: JSON.stringify(data),  // body date
            contentType: "application/json; charset=utf-8", // dody data type(MIME)
            dataType: "json" // reponse가 json 형식이라면 javascript로 변경해줌
        }).done(function(resp){
            alert("회원가입이 완료 되었습니다.");   
            alert(resp);
            location.href="/web";

        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
}

index.init();