<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
footer{
	background-color:  rgba(217,217,217,0.58);
	margin-top:50px;
	display: flex;
	justify-content: space-between;
	padding : 15px 50px 15px 15px;
	width: auto;
	height:120px
}
footer p{
	margin:0;
}

footer a{
	cursor: pointer;
}
.footer-2{
	display: flex;
	justify-content: flex-start;
	width:170px;
}
</style>
</head>
<div></div>
<footer>
    <div style="width:;">
        <p style="text-align:left;"><b>공지사항</b> <a href="${pageContext.request.contextPath}/friendSearch/adminChatPage.do" style="margin-left: 538px; font-size:18px">운영자 전용 채팅 공간</a></p>
        <p>Developer 쌍용 개발자 &nbsp; 대장: 서민수 그외 인물들 강연권 신현호 박하영 최익준</p>
        <p>게시판보기 &nbsp; 친구만들기 &nbsp; 상품 구매 &nbsp; 인바디 결과보기 ⓒ someBodyHealthMe</p>
    </div>
    <div class="footer-2">   
    <div><img alt="asd" src="${pageContext.request.contextPath}/images/icon _fire_.png"></div>
    <div><a href="${pageContext.request.contextPath}/main/main.do">Some Body<br>Health Me</a></div>    
    </div>
</footer>
</html>