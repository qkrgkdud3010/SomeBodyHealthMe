<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/MS.css" type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<style>
.message {
	font-size: 32px;
	margin-left: 18px;
	margin-top: 24px;
	margin-bottom: 11px;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<jsp:include page="/WEB-INF/views/common/aside.jsp" />
	<div class="main-wrap">
		<div id="chat1">
			<p class="message">메시지</p>
			<div class="search-box">
				<img src="${pageContext.request.contextPath}/images/Search.png"
					style="float: left;">

				<form action="">

					<input type="text"
						style="float: left; margin-left: 23px; height: 42px; width: 220px;">
				</form>

			</div>
			<div class="chat1_1">
				<div class="user-profile"></div>
				<div class="name-content">
					<p>강연권</p>
					<p>안녕하세요</p>
				</div>
			</div>
		</div>
		<div id="chat2">
			<div id="chat2_1">
				<div class="chat1_1">
					<div class="user-profile"></div>
					<div class="name-content">
						<p>강연권</p>
						<p>안녕하세요</p>
					</div>
				</div>
			</div>
	<div style="clear:both;"></div>
			<div id="chat2_2">
				<div clas="chat-request">
					<div class="user-profile"></div>
				</div>
				<div clas="chat-response"></div>
			</div>
		</div>
	
		<div class="chat3">
			<form action="">
			
			<input type="text">
			</form>
			
			<img alt="" src="${pageContext.request.contextPath}/images/Send.png" width="48px" height="48px" >
		
		</div>
	</div>
</body>
</html>