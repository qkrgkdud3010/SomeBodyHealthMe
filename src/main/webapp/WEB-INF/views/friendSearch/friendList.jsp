<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/MS.css" type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script>
 $(document).ready(function() {
            $('.sendFriendRequestButton').click(function() {
                const receiverNum = $(this).data('receiver-num'); // 버튼에서 receiverNum 값 가져오기

                // AJAX 요청 보내기
                $.ajax({
                    type: 'POST',
                    url: 'sendFriendRequest.do', 
                    dataType:'json',// 서버에서 요청을 처리할 URL
                    data: { receiverNum: receiverNum },  // 보내는 데이터
                    success: function(response) {
                        // 서버에서 보내는 응답 처리
                        if (response.isRequestSent=='success') {
                            alert("친구 요청이 성공적으로 전송되었습니다!");
                        } else if(response.isRequestSent=='duple'){
                            alert("이미 친구입니다");
                        }else{
                        	alert("실패.!ns");
                        }
                    },
                    error: function() {
                        alert("서버와의 통신 오류가 발생했습니다.");
                    }
                });
            });
        });
 
 $(document).ready(function() {
     $('.sendFriendRequestButton2').click(function() {
         const receiverNum = $(this).data('receiver-num'); // 버튼에서 receiverNum 값 가져오기

         // AJAX 요청 보내기
         $.ajax({
             type: 'POST',
             url: 'sendFriendRequest2.do', 
             dataType:'json',// 서버에서 요청을 처리할 URL
             data: { receiverNum: receiverNum },  // 보내는 데이터
             success: function(response) {
                 // 서버에서 보내는 응답 처리
                 if (response.isRequestSent=='success') {
                     alert("친구 요청 취소가 완료되었습니다!");
                 } else if(response.isRequestSent=='duple'){
                     alert("이미 친구 입니다");
                 }else{
                 	alert("실패.!ns");
                 }
             },
             error: function() {
                 alert("서버와의 통신 오류가 발생했습니다.");
             }
         });
     });
 });
 
 function refreshPage() {
     location.reload(); // 페이지 새로고침
 }
    </script>
<style>
    .container {
        width: 60%;
        margin: 0 auto;
        padding: 20px;
        font-family: Arial, sans-serif;
    }
    
    .header {
        display: flex;
        justify-content: space-between;
        margin-bottom: 20px;
    }

    .button-group .button {
        background-color: rgba(0, 0, 0, .66);
        color: white;
        padding: 10px 20px;
        text-decoration: none;
        border-radius: 5px;
        font-weight: bold;
        transition: background-color 0.3s;
    }

    .button-group .button:hover {
        background-color: rgba(0, 0, 0, .66);
    }

    .search-form ul {
        display: flex;
        list-style: none;
        padding: 0;
        margin-bottom: 20px;
    }

    .search-form li {
        margin-right: 10px;
    }

    .sel {
        padding: 5px;
        font-size: 16px;
    }

    .search-input {
        padding: 5px;
        font-size: 16px;
        width: 200px;
    }

    .submit-button {
        background-color:rgba(0, 0, 0, .66);
        color: white;
        padding: 5px 20px;
        font-size: 16px;
        border: none;
        cursor: pointer;
        border-radius: 5px;
    }

    .submit-button:hover {
        background-color: rgba(0, 0, 0, .66);
    }

    .list-space {
        text-align: right;
        margin-top: 20px;
    }

    .list-button, .home-button {
        background-color: rgba(0, 0, 0, .66);
        color: white;
        padding: 10px 20px;
        font-size: 16px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        margin-left: 10px;
    }

    .list-button:hover, .home-button:hover {
        background-color: rgba(0, 0, 0, .66);
    }

    .friend-table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
    }

    .friend-table th, .friend-table td {
        padding: 10px;
        border: 1px solid #ddd;
        text-align: center;
    }

    .friend-table th {
        background-color: rgba(0, 0, 0, .66);
    }

    .friend-request-button, .friend-cancel-button {
        text-decoration: none;
        color: white;
        background-color: rgba(0, 0, 0, .66);
        padding: 5px 10px;
        border-radius: 5px;
        cursor: pointer;
    }

    .friend-request-button:hover, .friend-cancel-button:hover {
        background-color: rgba(0, 0, 0, .66);
    }

    .friend-request-button2 {
        color: #4CAF50;
    }
</style>

</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<jsp:include page="/WEB-INF/views/common/asideFriend.jsp" />
	<div class="container">
    <div class="header">
        <div class="button-group">
            <a href="${pageContext.request.contextPath}/friendSearch/friendList.do"  class="home-button">전체 보기</a>
            <a href="${pageContext.request.contextPath}/friendSearch/friendList2.do?center_num=1" class="home-button">지점으로 보기</a>
        </div>
    </div>

    <form id="search_form" action="friendList.do" method="get" class="search-form">
        <ul class="search">
            <li>
                <select name="keyfield" class="sel">
                    <option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>이름</option>
                    <option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>닉네임</option>
                </select>
            </li>
            <li><input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}" class="search-input"></li>
            <li><input type="submit" value="찾기" class="submit-button"></li>
        </ul>
    </form>

    <div class="list-space">
        <input type="button" value="목록" onclick="location.href='friendList.do'" class="list-button">
        <input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'" class="home-button">
    </div>

    <table class="friend-table">
        <thead>
            <tr>
                <th>이름</th>
                <th>닉네임</th>
                <th>번호</th>
                <th>상태</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="friend" items="${list}">
                <c:if test="${friend.status2!=0}">
	                <tr>
	                    <td>${friend.name}</td>
	                    <td>${friend.nick_name}</td>
	                    <td>
	                        <c:if test="${friend.center_Num=='1'}">강남점</c:if>
	                        <c:if test="${friend.center_Num=='2'}">강북점</c:if>
	                    </td>
	                    <td style="text-align:center;">
	                        <c:if test="${friend.status=='None'}">
	                            <a href="javascript:void(0);" class="friend-request-button sendFriendRequestButton" data-receiver-num="${friend.user_Num}" onclick="refreshPage()">친구 요청</a>
	                        </c:if>
	                        <c:if test="${friend.status=='1'}">
	                            <a href="javascript:void(0);" class="friend-cancel-button sendFriendRequestButton2" data-receiver-num="${friend.user_Num}" onclick="refreshPage()">친구 요청 취소</a>
	                        </c:if>
	                        <c:if test="${friend.status=='2'}">
	                            <span>친구입니다!</span>
	                        </c:if>
	                    </td>
	                </tr>
            	</c:if>
            </c:forEach>
        </tbody>
    </table>
</div>

		<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>
