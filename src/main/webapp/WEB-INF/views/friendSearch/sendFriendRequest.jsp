<%@ page import="java.sql.*" %>

<%@ page import="java.util.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>친구 요청</title>
</head>
<body>
    <h1>친구 요청 보내기</h1>
    
    <form action="sendFriendRequest.do" method="post">
        <label for="receiverNum">친구 번호:</label>
        <input type="number" id="receiverNum" name="receiverNum" value="2" />
        <br />
        <input type="submit" value="친구 요청 보내기" />
    </form>

    <br />
    <a href="home.jsp">홈으로 돌아가기</a>
</body>
</html>
