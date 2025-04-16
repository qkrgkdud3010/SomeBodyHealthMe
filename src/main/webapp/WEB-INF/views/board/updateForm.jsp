<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SBHM 소통공간 : 게시글수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/board_writeForm.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#update_form').submit(function(){
			const items = document.querySelectorAll('.input-check');
			for(let i=0;i<items.length;i++){
				if(items[i].value.trim()==''){
					const label = document.querySelector('label[for="'+ items[i].id+'"]');
					alert(label.textContent + '필수 입력');
					items[i].value='';
					items[i].focus();
					return false;
				}
			}	
			/*
			const category = $('#board_category').val();
			const attachment = $('#board_attachment').val();
			const attach_check = $('#attach_check').val();
			if(category == 3 && attachment =='' && attach_check ==''){
				alert('오늘 운동 완료 게시판에는 사진을 필수로 첨부해야 합니다.');
				return false;
			}
			*/
		});
	});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/aside_board.jsp"/>
 <div class="container">
        <h2><span>글수정</span></h2>
        <hr size="3" noshade="noshade">
        <form action="update.do" method="post" enctype="multipart/form-data" id="update_form">
            <input type="hidden" name="board_num" value="${board.board_num}">
            <div class="form-group">
                <label for="board_category">카테고리</label>
                <select name="board_category" id="board_category" class="form-attr" disabled>
                    <option value="1" <c:if test="${status == 4 && board.board_category == 1}">selected</c:if>>공지사항</option>
                    <option value="2" <c:if test="${board.board_category == 2}">selected</c:if>>자유게시판</option>
                    <option value="3" <c:if test="${board.board_category == 3}">selected</c:if>>오늘 운동 완료</option>
                </select>
            </div>
            <div class="form-group">
                <label for="board_title">제목</label>
                <input type="text" id="board_title" name="board_title" required placeholder="제목을 입력하세요" value="${board.board_title}" maxlength="50" class="input-check">
            </div>
            <div class="form-group">
                <label for="board_attachment">첨부파일(이미지)</label>
                <input type="file" id="board_attachment" class="form-attr" name="board_attachment" accept="jpg, .jpeg, .png, .gif"> 
				<input type="hidden" value="${board.board_attachment}" id="attach_check">
            </div>
            <c:if test="${!empty board.board_attachment}">
					<div id="db_attachment">
						<span>(${board.board_attachment})파일이 등록되어 있습니다.</span> <br>
						<img src="${pageContext.request.contextPath}/upload/${board.board_attachment}" border="1" style="max-width:600px; max-height:400px;">
						<c:if test="${board.board_category != 3}">
						<input type="button" value="파일삭제" id="file_del">
						<script type="text/javascript">
							$('#file_del').click(function(){
								let choice = confirm('등록된 파일을 삭제하시겠습니까?\n(삭제 시, 수정 완료 없이 현재 게시글 내용에서 삭제됩니다.)');
								if(choice){
									//서버와 통신
									$.ajax({
										url:'deleteAttachment.do',
										type:'post',
										data:{board_num:${board.board_num}},
										dataType:'json',
										success:function(param){
											if(param.result == 'logout'){
												alert('로그인 후 사용하세요');
											}else if(param.result == 'success'){
												$('#db_attachment').hide();
												$('#attach_check').val('');
												alert('삭제 완료');
											}else if(param.result == 'wrongAccess'){
												alert('잘못된 접속입니다.');
											}else{
												alert('파일 삭제 오류 발생');
											}
										},
										error:function(){
											alert('네트워크 오류 발생');
										}
									});
								}
							});
						</script>
						</c:if>
					</div>
					</c:if>       
            <div class="form-group">
                <label for="board_content">내용</label>
                <textarea name="board_content" id="board_content" placeholder="내용을 입력하세요" class="input-check" required>${board.board_content}</textarea>
            </div>
            <div class="align-end">
            	<input type="button" value="취소" onclick="history.go(-1)">            
                <input type="submit" value="수정완료">            
            </div>
        </form>
    </div>
</div>
<jsp:include page="/WEB-INF/views/board/board_footer.jsp"/>
</body>
</html>