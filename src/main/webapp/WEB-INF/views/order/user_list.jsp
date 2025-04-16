<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>구매 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/IJ.css" type="text/css">
<script type="text/javascript">
	window.onload=function(){
		const myForm = document.getElementById('search-bar');
		//이벤트 연결
		myForm.onsubmit=function(){
			const keyword = document.getElementById('keyword');
			if(keyword.value.trim()==''){
				alert('검색어를 입력하세요!');
				keyword.value = '';
				keyword.focus();
				return false;
			}
		};
	};
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/common/aside_goods.jsp"/>
	<div class="content-main">
		<h2>구매 목록</h2>
		<form class="search-bar" action="orderList.do" method="get">
			<ul id="search-bar">
				<li>
					<select name="keyfield">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>상품명</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>주문번호</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword"
					       id="keyword" value="${param.keyword}">
				</li>
				<li>
					<input type="submit" value="검색">
				</li>
			</ul>
		</form>
		<div class="list-space align-right">
			<input type="button" value="목록"
			       onclick="location.href='orderList.do'">
			<input type="button" value="홈으로"
			       onclick="location.href='${pageContext.request.contextPath}/main/main.do'">       
		</div>
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 주문 내역이 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<table>
			<tr>
				<th>주문번호</th>
				<th>주문상품</th>
				<th>총주문금액</th>
				<th>주문날짜</th>
				<th>상태</th>
			</tr>
			<c:forEach var="order" items="${list}">
			<tr>
				<td>${order.order_num}</td>
				<td><a href="orderDetail.do?order_num=${order.order_num}">${order.goods_name}</a></td>
				<td><fmt:formatNumber value="${order.order_total}"/>원</td>
				<td>${order.reg_date}</td>
				<td>
					<c:if test="${order.status == 1}">배송대기</c:if>
					<c:if test="${order.status == 2}">배송준비중</c:if>
					<c:if test="${order.status == 3}">배송중</c:if>
					<c:if test="${order.status == 4}">배송완료</c:if>
					<c:if test="${order.status == 5}">주문취소</c:if>
				</td>
			</tr>
			</c:forEach>
		</table>
		<div class="align-center">${page}</div>
		</c:if>		
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />	
</div>
</body>
</html>




