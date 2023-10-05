let index = {
    init: function() {
        $("#board-save").on("click", ()=>{  // 화살표 함수 -> JavaScript 함수의 this 바인딩
            this.save();
        });

        $("#board-delete").on("click", ()=>{
            this.deleteById();
        });

        $("#board-update").on("click", ()=>{
            this.update();
        });

        $("#btn-reply-save").on("click", ()=>{
            this.replySave();
        });

        $("#btn-comment-save").on("click", ()=>{
            this.commentSave();
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
                    location.href="/board/"+id;
                }else {
                    alert("삭제가 완료 되었습니다.");
                    location.href="/";
                }
            }).fail(function(error){
                alert(JSON.stringify(error));
             });
        }
    },

    update: function() {
        let id = $("#id").val();
        console.log(id);
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
            type:"PUT",
            url: "/api/board/"+id,
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
                alert("글수정이 실패했습니다.");
                location.href="/board/"+id;
            }else {
                alert("글수정이 완료 되었습니다.");
                location.href="/";
            }
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    replySave : function() {
        let data = {
            boardId : $("#id").text(),
            content : $("#reply-content").val()
        }
        console.log(data);

        if(data.content == "") {
            alert("내용을 입력해 주세요");
            return;
        }

        $.ajax({
            type:"POST",
            url: `/api/board/${data.boardId}/reply`,
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
                alert("댓글작성이 실패했습니다.");
                location.href=`/board/${data.boardId}`;
            }else {
                //alert("댓글작성이 완료 되었습니다.");
                location.href=`/board/${data.boardId}`;
            }
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    replyDelete : function(boardId, replyId) {
        if (!confirm("정말 댓글을 삭제할까요?")) {
            return false;
        } else {
            $.ajax({
                type:"DELETE",
                url: `../api/board/${boardId}/reply/${replyId}`,
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
                    location.href="/board/"+boardId;
                }else {
                    alert("삭제가 완료 되었습니다.");
                    location.href="/board/"+boardId;
                }
            }).fail(function(error){
                alert(JSON.stringify(error));
             });
        }
    },

    toggle : function(replyId) {
        let replyTarget = "toggle--"+replyId
          // 토글 할 버튼 선택 (btn1)
          const target = document.getElementById(replyTarget);

          // btn1 숨기기 (display: none)
          if(target.style.display !== 'none') {
            target.style.display = 'none';
          }else {
            target.style.display = "";
          }
    },

    commentSave : function() {
        let data = {
            boardId : $("#id").text(),
            replyId : $("#replyId").val(),
            content : $("#comment-content").val()
        }

        if(data.content == "") {
            alert("내용을 입력해 주세요");
            return;
        }

        $.ajax({
            type:"POST",
            url: `/api/reply/${data.replyId}/replyToComment`,
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
                alert("댓글작성이 실패했습니다.");
                location.href="/board/"+data.boardId;
            }else {
                //alert("댓글작성이 완료 되었습니다.");
                location.href="/board/"+data.boardId;
            }
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    commentDelete : function(boardId ,replyId, replyToCommentId) {
        if (!confirm("정말 댓글을 삭제할까요?")) {
            return false;
        } else {
            $.ajax({
                type:"DELETE",
                url: `../api/reply/${replyId}/replyToComment/${replyToCommentId}`,
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
                    location.href="/board/"+boardId;
                }else {
                    alert("삭제가 완료 되었습니다.");
                    location.href="/board/"+boardId;
                }
            }).fail(function(error){
                alert(JSON.stringify(error));
            });
        }
    },
}

index.init();