<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
	<style type="text/css">
	
	 .container {
            width: 60%;
            height: 100%;
            flex-grow: 1; /* 남은 공간을 차지하도록 설정 */ 
            overflow-y: auto; /* 세로 스크롤 추가 */
            text-align: center;
 			margin-top:43px;
 			margin-left:43px;
            padding: 20px;
            background-color: #ffffff;
            border: 1px solid #ddd; /* 외부 테두리 */
    		float:left;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            table-layout: fixed;  /* 고정된 테이블 레이아웃 */
        }

        th, td {
            padding: 10px;
            text-align: center;
            border: 1px solid #ddd;
        }

        th {
            background-color: #D9D9D9;
            color: black;
        }</style>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
	</div>

	<div class="main-1">
		<div class="main-1-sub">
			<a href="${pageContext.request.contextPath}/friendSearch/friendList2.do?center_num=1"><div class="main-1-1">강남점></div></a>
			<div class="main-1-1-sub"></div>
			<a href="${pageContext.request.contextPath}/friendSearch/friendList2.do?center_num=2"><div class="main-1-2">강북점></div></a>
		</div>
	</div>

	<div class="main-2">

	</div>
	<a href="${pageContext.request.contextPath}/goods/list.do"><h3 style="float:right; margin-top:20px;"> 더보기>></h3> </a>
	<div class="main-3" style=" margin-bottom:70px;">
	   <c:forEach var="cart" items="${list1}" begin="0" end="3">
		<div class="main-3-1">
	   		<a href="${pageContext.request.contextPath}/goods/detail.do?goods_num=${cart.goods_num}">
			<div class="main-3-1-1">
				<img src="${pageContext.request.contextPath}/upload/${cart.goods_img1}">
			</div>
			<div class="main-3-2">
			<p id="main-name">${cart.goods_name}</p>
			<p id="main-price"><fmt:formatNumber value="${cart.goods_price}"/> 원</p>
			</div>
		</div>
		</a>
		</c:forEach>
	</div>
	<div class="main-4">
		<a href="${pageContext.request.contextPath}/board/list.do"><h3 style="float:right; margin-top:20px;"> 더보기>></h3> </a>
	<table>
                <tr>
                    <th>글번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성일</th>
                    <th>조회수</th>
                </tr>                
                <c:forEach var="board" items="${list}">
                <tr>
                    <td>${board.board_num}</td>
                    <td><a href="${pageContext.request.contextPath}/board/detail.do?board_num=${board.board_num}">${board.board_title}</a></td>
                    <td>${board.nick_name}</td>
                    <td>${board.board_regdate }</td>
                    <td>${board.board_count }</td>
                </tr>
                </c:forEach>
            </table>
	</div>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>



