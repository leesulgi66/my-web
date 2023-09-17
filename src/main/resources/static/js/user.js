let index = {
    init: function() {
        $("#btn-save").on("click", ()=>{  // 화살표 함수 -> JavaScript 함수의 this 바인딩
            this.save();
        });

        $("#btn-login").on("click", ()=>{  // 화살표 함수 -> JavaScript 함수의 this 바인딩
            this.login();
        });
    },

    save: function() {
        let data = {
            username : $("#username").val(),
            nickname : $("#nickname").val(),
            password : $("#password").val(),
            password_re : $("#password-re").val(),
            email : $("#email").val(),
        }

        if(data.username == "") {
            alert("아이디를 입력해 주세요");
            return;
        }else if(data.nickname == "") {
            alert("닉네임을 입력해 주세요");
            return;
        }else if(data.password == "") {
            alert("비밀번호를 입력해 주세요");
            return;
        }else if(data.password_re == "") {
            alert("비밀번호 확인을 입력해 주세요");
            return;
        }else if(data.email == "") {
            alert("이메일을 입력해 주세요");
            return;
        }

        if(data.password !== data.password_re) {
            alert("비밀번호가 일치하지 않습니다.")
            return;
        }

        $.ajax({
            type:"POST",
            url: "/auth/join/",
            data: JSON.stringify(data),  // body date
            contentType: "application/json; charset=utf-8", // dody data type(MIME)
            dataType: "json" // reponse가 json 형식이라면 javascript로 변경해줌
        }).done(function(resp){
            alert("회원가입이 완료 되었습니다.");   
            location.href="/";

        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

}

index.init();