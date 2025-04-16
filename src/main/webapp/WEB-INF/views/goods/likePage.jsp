<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>찜 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/IJ.css" type="text/css"> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/common/aside_goods.jsp" />
	<div class="content-main">
		<div class="mypage-div">
 			<h2 class="align-center">찜 목록</h2>
 			<div class="button-container">
			<c:if test="${status == 4}">
				<input type="button" value="상품 목록" onclick="location.href='adminlist.do'">
			</c:if>
			<c:if test="${status != 4}">
				<input type="button" value="상품 목록" onclick="location.href='list.do'">
			</c:if>
			</div>
 			<c:if test="${empty goodsList}">
			<div class="result-display">
			찜한 상품이 없습니다.
			</div>
			</c:if>
			<c:if test="${!empty goodsList}">
 			<table>
					<tr>
						<th></th>
						<th>상품명</th>
						<th>가격</th>
					</tr> 			
					<c:forEach var="goods" items="${goodsList}">
					<tr>
						<td><a href="${pageContext.request.contextPath}/goods/detail.do?goods_num=${goods.goods_num}">
						<img src="${pageContext.request.contextPath}/upload/${goods.goods_img1}" width="200"></a></td>
						<td><a href="${pageContext.request.contextPath}/goods/detail.do?goods_num=${goods.goods_num}"
						target="_blank">${fn:substring(goods.goods_name,0,26)}</a></td>
						<td>${goods.goods_price}원</td>
					</tr>
					</c:forEach>
 			</table>
 			</c:if>
 		</div>
 		<div class="align-center" id="page-selector">${page}</div>
 	</div>
 	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</div> 	
</body>
</html>