<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>구매상세(관리자 전용)</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/IJ.css" type="text/css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/common/aside_goods.jsp"/>
	<div class="content-main">
		<h2 class="align-center">구매상세</h2>
		<table>
			<tr>
				<th>상품명</th>
				<th>수량</th>
				<th>상품가격</th>
				<th>합계</th>
			</tr>
			<c:forEach var="detail" items="${detailList}">
			<tr>
				<td><a href="${pageContext.request.contextPath}/goods/detail.do?goods_num=${detail.goods_num}">${detail.goods_name}</a>${detail.goods_name}</td>
				<td class="align-center"><fmt:formatNumber value="${detail.order_quantity}"/></td>
				<td class="align-center"><fmt:formatNumber value="${detail.goods_price}"/>원</td>
				<td class="align-center"><fmt:formatNumber value="${detail.goods_total}"/>원</td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="3" class="align-right"><b>총구매금액</b></td>
				<td class="align-center"><fmt:formatNumber value="${order.order_total}"/>원</td>
			</tr>
		</table>
		<ul id="delivery_info">
			<li>
				<span>받는 사람</span>
				${order.receive_name}
			</li>
			<li>
				<span>우편번호</span>
				${order.receive_post}
			</li>
			<li>
				<span>주소</span>
				${order.receive_address1} ${order.receive_address2}
			</li>
			<li>
				<span>전화번호</span>
				${order.receive_phone}
			</li>
			<li>
				<span>남기실 말씀</span>
				${order.notice}
			</li>
			<li>
				<span>결제수단</span>
				<c:if test="${order.payment == 1}">은행입금</c:if>
				<c:if test="${order.payment == 2}">카드결제</c:if>
			</li>
			<li>
				<span>배송상태</span>
				<c:if test="${order.status == 1}">배송대기</c:if>
				<c:if test="${order.status == 2}">배송준비중</c:if>
				<c:if test="${order.status == 3}">배송중</c:if>
				<c:if test="${order.status == 4}">배송완료</c:if>
				<c:if test="${order.status == 5}">주문취소</c:if>
			</li>
		</ul>
		<form action="modifyStatus.do" method="post" id="status_modify">
			<input type="hidden" name="order_num" value="${order.order_num}">
			<ul>
				<li class="align-center">
					<c:if test="${order.status != 5}">
					<input type="radio" name="status"
					 id="status1" value="1"
					 <c:if test="${order.status == 1}">checked</c:if>>배송대기
					<input type="radio" name="status"
					 id="status2" value="2"
					 <c:if test="${order.status == 2}">checked</c:if>>배송준비중
					<input type="radio" name="status"
					 id="status3" value="3"
					 <c:if test="${order.status == 3}">checked</c:if>>배송중
					<input type="radio" name="status"
					 id="status4" value="4"
					 <c:if test="${order.status == 4}">checked</c:if>>배송완료
					</c:if>
					
					<input type="radio" name="status"
					 id="status5" value="5"
					 <c:if test="${order.status == 5}">checked</c:if>>주문취소
					
				</li>
			</ul>
			<div class="align-center">
				<c:if test="${order.status != 5}">
				<input type="submit" value="수정" id="my-btn">
				</c:if>
				<input type="button" value="주문목록" 
			onclick="location.href='adminList.do'">
			</div>
		</form>
		
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</div>
</body>
</html>