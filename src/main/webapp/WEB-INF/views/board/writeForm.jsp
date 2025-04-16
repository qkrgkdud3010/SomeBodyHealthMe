<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SBHM 소통공간 : 게시글 작성</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/board_writeForm.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#write_form').submit(function(){
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
			
			const category = $('#board_category').val();
			const attachment = $('#board_attachment').val();
			if(category == 3 && attachment ==''){
				alert('오늘 운동 완료 게시판에는 사진을 필수로 첨부해야 합니다.');
				return false;
			}
		});
	});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/aside_board.jsp"/>
<div class="container">
        <h2><span>소통공간 글쓰기</span></h2>
        <hr size="3" noshade="noshade">
        <form action="write.do" method="post" enctype="multipart/form-data" id="write_form">
            <div class="form-group">
                <label for="board_category">게시판 선택</label>
                <select name="board_category" id="board_category" class="form-attr">
                    <c:if test="${status >= 4}"><option value="1" <c:if test="${cate == 1}">selected</c:if>>공지사항</option></c:if>
                    <option value="2" <c:if test="${cate == 2}">selected</c:if>>자유게시판</option>
                    <option value="3" <c:if test="${cate == 3}">selected</c:if>>오늘 운동 완료</option>
                </select>
            </div>
            <div class="form-group">
                <label for="board_title">제목</label>
                <input type="text" name="board_title" id="board_title" placeholder="제목을 입력해 주세요" maxlength="100" class="input-check" required>
            </div>
            <div class="form-group">
                <label for="board_attachment">첨부파일(이미지)</label>
                <input type="file" id="board_attachment" name="board_attachment" class="form-attr" accept="jpg, .jpeg, .png, .gif"> 
            </div>
            <div class="form-group">
                <label for="board_content">내용</label>
                <textarea name="board_content" id="board_content" placeholder="내용을 입력하세요" class="input-check" required></textarea>
            </div>
            <div class="align-end">
                <input type="button" value="등록취소" onclick="history.go(-1)">            
                <input type="submit" value="등록하기">            
            </div>
        </form>
    </div>
</div>    
<jsp:include page="/WEB-INF/views/board/board_footer.jsp"/>
</body>
</html>