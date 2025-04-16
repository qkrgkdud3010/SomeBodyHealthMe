<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>장바구니</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/IJ.css" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/shop.cart.js"></script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<jsp:include page="/WEB-INF/views/common/aside_goods.jsp"/>
		<div class="content-main">
			<h2 class="align-center">장바구니</h2>
			<c:if test="${empty list}">
			<div class="result-display">
			장바구니에 담은 상품이 없습니다.
			</div>
			</c:if>
			<c:if test="${!empty list}">
				<form id="cart_order" action="${pageContext.request.contextPath}/order/orderForm.do" method="POST">
					<table>
						<tr>
							<th>상품명</th>
							<th>수량</th>
							<th>상품가격</th>
							<th>합계</th>
						</tr>
						<c:forEach var="cart" items="${list}">
							<tr>
								<td>
								 	<a href="${pageContext.request.contextPath}/goods/detail.do?goods_num=${cart.goods_num}">
								 		<img src="${pageContext.request.contextPath}/upload/${cart.goodsVO.goods_img1}" width="80" >
								 		${cart.goodsVO.goods_name}
								 	</a>
								</td>
								<td class="align-center">
									<c:if test="${cart.goodsVO.goods_status == 1}">[판매중지]</c:if>
									<c:if test="${cart.goodsVO.goods_quantity < cart.order_quantity}">[수량부족]<br><small>삭제 후 재고 수량 확인</small></c:if>
									<c:if test="${cart.goodsVO.goods_status==2  and cart.goodsVO.goods_quantity >= cart.order_quantity}">
									<input type="number" name="order_quantity" min="1" max="${cart.goodsVO.goods_quantity}" autocomplete="off"
									value="${cart.order_quantity}" class="quantity-width">
									<br>
									<input type="button" value="변경" class="cart-modify" data-cartnum="${cart.cart_num}" data-itemnum="${cart.goods_num}">
									</c:if>
								</td>
								<td class="align-center"><fmt:formatNumber value="${cart.goodsVO.goods_price}"/>원</td>
								<td class="align-center">
								<fmt:formatNumber value="${cart.sub_total}"/>원
								<br>
								<input type="button" value="삭제" class="cart-del" data-cartnum="${cart.cart_num}">
								</td>

							</tr>
						</c:forEach>
						<tr>
							<td colspan="3" class="align-center"><b>총구매금액</b></td>
							<td class="align-center"><fmt:formatNumber value="${all_total}"></fmt:formatNumber>원</td>
						</tr>
					</table>
					<div class="align-center">
						<input type="submit" value="구매하기" id="order_btn">
					</div>
				</form>
			</c:if>
		</div>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
</body>
</html>