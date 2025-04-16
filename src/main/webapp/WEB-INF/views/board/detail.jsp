<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head> 
<meta charset="UTF-8">
<title>SBHM 소통공간</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/board_detail.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board.reply.js"></script>
</head>

<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/aside_board.jsp"/>
    <div class="board">
        <div class="page-top">
            <h2>상세 글보기</h2>
            <div class="board-btn">
            <c:if test="${user_num == board.user_num}">
            	<input type="button" value="수정" onclick="location.href='updateForm.do?board_num=${board.board_num}<c:if test="${!empty cate}">&board_category=${cate}</c:if>'">
            </c:if>	
            <c:if test="${board.user_num == user_num || status == 4}">
                <input type="button" value="삭제" id="delete_btn" >
                <script type="text/javascript">
                	const delete_btn = document.getElementById('delete_btn');
                	delete_btn.onclick = function(){
                		let choice = confirm('게시글을 삭제하시겠습니까?');
                		if(choice){
                			var url = 'delete.do?board_num=${board.board_num}';
                			<c:if test="${!empty cate}">
                			val += '&board_category=${cate}';
                			</c:if>
                			location.replace(url);
                		} 
                	};
                </script>
            </c:if>                
                <input type="button" value="목록" onclick="location.href='list.do<c:if test="${!empty cate}">?board_category=${cate}</c:if>'">       
            </div>
        </div>
        <div class="board-info">
            <a onclick="location.href='${pageContext.request.contextPath}/board/list.do?board_category=${board.board_category}'" id="boardCate">
            <c:if test="${board.board_category == 1}">공지사항 ></c:if>            
            <c:if test="${board.board_category == 2}">자유게시판 ></c:if>            
            <c:if test="${board.board_category == 3}">오늘 운동 완료 ></c:if>            
            </a>
            <h3>${board.board_title}</h3>
            <div class="info-detail">
                <div>
                    <span><span style="font-weight:bold;">작성자 </span> 
                    <c:if test="${empty board.nick_name}">{board.lonin_id}</c:if>
                    <c:if test="${!empty board.nick_name}">${board.nick_name}</c:if>
                    </span>
                    <span>
                    <c:if test="${empty board.board_modifydate}"><span style="font-weight:bold;">작성일 </span> ${board.board_regdate}</c:if>
                    <c:if test="${!empty board.board_modifydate}"><span>최근 수정일 </span> ${board.board_modifydate}</c:if>
                    </span>
                    
                </div>
                <div>
                    <span>조회수 ${board.board_count}</span>
                </div>    
            </div>
        </div>
        <div class="board-content">
        <c:if test="${!empty board.board_attachment}">
        <div class="board-attachment">
        	<img src="${pageContext.request.contextPath}/upload/${board.board_attachment}" class="detail-img" border="1">
        </div>
        </c:if>        
            ${board.board_content}
        </div>
	
       
        <!-- 게시글 끝 -->
        
        <!-- 댓글 시작-->      
        <!-- 댓글등록 시작 -->
        <div id="reply">
            <h3>댓글 등록</h3>
            <div class="reply-form">
                <div id="profile">
                    <img src="
                    	<c:if test="${!empty user_num}">
                    		<c:if test="${member.photo!=null && member.photo !='' && member.photo != 'default_user_photo.png'}">
                    		${pageContext.request.contextPath}/upload/${member.photo}
                    		</c:if>
                    		<c:if test="${member.photo == null || member.photo =='' || member.photo == 'default_user_photo.png'}">
                    		${pageContext.request.contextPath}/images/User.png
                    		</c:if>                    		
                    		
                    	</c:if>
                    	<c:if test="${empty user_num}">${pageContext.request.contextPath}/images/User.png</c:if>" 
                    	id="re_img" width="60" height="60"><br>
                    	
                    <div><c:if test="${!empty user_num}">${member.nick_name}</c:if><c:if test="${empty member.nick_name}">${member.login_id}</c:if> </div>
                </div>
                <form action="" id="re_form">
                        <input type="hidden" name="board_num" value="${board.board_num}" id="board_num">
                    <div class="re-content">
                    <h4>내용</h4>
                    <textarea name="re_content" id="re_content" placeholder="<c:if test="${empty user_num}">로그인 후 댓글등록이 가능합니다.</c:if><c:if test="${!empty user_num}">댓글을 입력해 주세요.</c:if>" <c:if test="${empty user_num}">disabled</c:if>></textarea>
                    </div>
                    <div class="re-bnt" style="align-content: end; margin-bottom: 8px;">
                    	<c:if test="${!empty user_num}">
                        <input type="submit" value="등록" id="re_submit" style="margin-bottom: 10px;">
                        </c:if>            
                        <br><span id="re_count">300/300</span>
                    </div>
                </form>                
            </div>
        </div>
        <!-- 댓글등록 끝-->
        
        <!-- 댓글목록 시작-->
		<h3>댓글 <span id="reply_count"></span>개</h3>
		<hr size="3" noshade="noshade">
        <div id="output"></div>  
        <div class="paging-button" style="display:none;">
			<input type="button" value="다음글 보기">
		</div>
		<div id="loading" style="display:none;">
           <img src="${pageContext.request.contextPath}/images/loading.gif" width=50;>
        </div>

        <!-- 댓글목록 끝-->      
    
    </div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
