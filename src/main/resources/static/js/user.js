let index = {
    init: function() {
        $("#btn-save").on("click", ()=>{  // 화살표 함수 -> JavaScript 함수의 this 바인딩
            this.save();
        });

        $("#btn-update").on("click", ()=>{
            this.update();
        });

        $("#login-button").on("click", ()=>{
            this.chk_form();
        });

        $("#btn-delete").on("click", ()=>{
            this.deleteForm();
        });

        $("#email").on("keydown", (key)=>{
            if(key.keyCode==13) {
                this.save();
            }
        });

        $("#email-update").on("keydown", (key)=>{
            if(key.keyCode==13) {
                this.update();
            }
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
        }
        if(data.nickname == "") {
            alert("닉네임을 입력해 주세요");
            return;
        }
        if(data.password == "") {
            alert("비밀번호를 입력해 주세요");
            return;
        }
        if(data.password_re == "") {
            alert("비밀번호 확인을 입력해 주세요");
            return;
        }
        if(data.email == "") {
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
            dataType: "json", // reponse가 json 형식이라면 javascript로 변경해줌
            success: function (data) {
                    console.log(data)
                },
            error: function (request, status, error) {
                console.log("code: " + request.status)
                console.log("message: " + request.responseText)
                console.log("error: " + error);
            }
        }).done(function(resp){
            console.log(resp);
            if(resp.status == 500) {
                //alert(resp.data);
                alert("회원가입에 실패했습니다.");
                location.href="/auth/joinForm";
            }else {
                alert("회원가입이 완료 되었습니다.");
                location.href="/";
            }

        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    update: function() {
            let data = {
                id: $("#id").val(),
                nickname : $("#nickname").val(),
                password : $("#password").val(),
                password_re : $("#password-re").val(),
                email : $("#email-update").val(),
            }

            if(data.nickname == "") {
                alert("닉네임을 입력해 주세요");
                return;
            }
            if(data.password == "") {
                alert("비밀번호를 입력해 주세요");
                return;
            }
            if(data.password_re == "") {
                alert("비밀번호 확인을 입력해 주세요");
                return;
            }
            if(data.email == "") {
                alert("이메일을 입력해 주세요");
                return;
            }

            if(data.password !== data.password_re) {
                alert("비밀번호가 일치하지 않습니다.")
                return;
            }

            $.ajax({
                type:"PUT",
                url: "/user/info",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (data) {
                        console.log(data)
                    },
                error: function (request, status, error) {
                    console.log("code: " + request.status)
                    console.log("message: " + request.responseText)
                    console.log("error: " + error);
                }
            }).done(function(resp){
                console.log(resp);
                if(resp.status == 500) {
                    //alert(resp.data);
                    alert("회원수정에 실패했습니다.");
                    location.href="/user/info";
                }else {
                    alert("회원수정이 완료 되었습니다.");
                    location.href="/";
                }

        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    deleteForm: function() {
                let data = {
                    id: $("#id").val(),
                    password : $("#password").val(),
//                    password_re : $("#password-re").val(),
                }

//                if(data.password !== data.password_re) {
//                    alert("비밀번호가 일치하지 않습니다.")
//                    return;
//                }

                if (!confirm("정말 회원 탈퇴를 할까요?")) {
                    return false;
                }else {
                $.ajax({
                    type:"DELETE",
                    url: "/user/info",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function (data) {
                            console.log(data)
                        },
                    error: function (request, status, error) {
                        console.log("code: " + request.status)
                        console.log("message: " + request.responseText)
                        console.log("error: " + error);
                    }
                }).done(function(resp){
                    console.log(resp);
                    if(resp.status == 500) {
                        //alert(resp.data);
                        alert("회원 탈퇴에 실패했습니다.");
                        location.href="/user/info";
                    }else {
                        alert("회원 탈퇴에 완료 되었습니다.");
                        location.href="/logout";
                    }

                }).fail(function(error){
                    alert(JSON.stringify(error));
                });
                }
        },

    chk_form : function() {
        if(document.getElementById("username").value==''){
        	alert("아이디를 입력해주십시오.");
        	return false;
        }
        if(document.getElementById("password").value==''){
        	alert("비밀번호를 입력해주십시오.");
        	return false;
        }
        document.getElementById('frm').submit();
    }
}

index.init();