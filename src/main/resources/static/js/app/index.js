// p141
var main = { //p142-p143 중요. footer.js 에서 include 한다.
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () { // p153 1 btn-update 란 id를 가진 html 엘리먼트에 click 이벤트가 발생할 때 update function 을 실행하도록 이벤트를 등록한다
            _this.update();
        });

        $('#btn-delete').on('click', function () { // p158 삭제 버튼 이벤트
            _this.delete();
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(data) {
            alert('글이 등록되었습니다.');
            var json = $.parseJSON(data);
            console.info(json); //새로 입력한 키(Long)
            window.location.href = '/';  // 1  p141 글 등록이 성공하면 메인페이지(/)로 이동한다
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () { // p153
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT', // p153 3 PostsApiController 에 있는 API 에서 @PuttingMapping 으로 선언했기 때문에 PUT 를 사용해야 한다. REST 규약에 맞게 설정된 것이다.
            // REST 에서 CRUD 는 다음과 같이 HTTP Method 에 매핑 된다.
            // 생성(create) = POST
            // 읽기(read) = GET
            // 수정(update) = PUT
            // 삭제 (delete) = DELETE
            url: '/api/v1/posts/'+id,  // p153 4 어느 게시글을 수정할 지 URL Path 로 구분하기 위해 Path 에 id를 추가한다
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            //alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

main.init();