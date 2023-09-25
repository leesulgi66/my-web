let index = {
    init: function() {
        $("#board-save").on("click", ()=>{  // 화살표 함수 -> JavaScript 함수의 this 바인딩
            this.save();
        });

        $("#board-delete").on("click", ()=>{  // 화살표 함수 -> JavaScript 함수의 this 바인딩
                    this.deleteById();
                });
    },

    save: function() {
        let data = {
            title : $("#title").val(),
            content : $("#content").val()
        }
        if(data.title == "") {
            alert("제목을 입력해 주세요");
            return;
        }
        if(data.content == "") {
            alert("내용을 입력해 주세요");
            return;
        }

        $.ajax({
            type:"POST",
            url: "/api/board/",
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
                alert("글쓰기가 실패했습니다.");
                location.href="/auth/joinForm";
            }else {
                alert("글쓰기가 완료 되었습니다.");
                location.href="/";
            }
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    deleteById : function() {
            let id = $("#id").text();

            if (!confirm("정말 글을 삭제할까요?")) {
                return false;
            } else {
                $.ajax({
                    type:"DELETE",
                    url: "/api/board/"+id,
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
                        alert("삭제가 실패했습니다.");
                        location.href="/auth/joinForm";
                    }else {
                        alert("삭제가 완료 되었습니다.");
                        location.href="/";
                    }
                }).fail(function(error){
                    alert(JSON.stringify(error));
                });
            }
        },
}

index.init();