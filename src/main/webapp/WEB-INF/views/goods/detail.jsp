<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 상세</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/IJ.css" type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/shop.goods-detail.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/goods.like.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/goods.reply.js"></script>
<script>
	// JSP에서 contextPath를 JavaScript로 전달
	var contextPath = "${pageContext.request.contextPath}";
</script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<jsp:include page="/WEB-INF/views/common/aside_goods.jsp" />

		<div class="content-main">

			<div class="button-container">
				<c:if test="${status == 4}">
					<input type="button" value="상품 수정"
						onclick="location.href='updateForm.do?goods_num=${goods.goods_num}'">
					<input type="button" value="상품 삭제" id="delete_btn">
					<input type="button" value="상품 목록"
						onclick="location.href='adminlist.do'">
					<script type="text/javascript">
						const delete_btn = document
								.getElementById('delete_btn');
						delete_btn.onclick = function() {
							let choice = confirm('삭제하시겠습니까?');
							if (choice) {
								location
										.replace('delete.do?goods_num=${goods.goods_num}');
							}
						};
					</script>
				</c:if>
				<c:if test="${status != 4}">
					<input type="button" value="상품 목록"
						onclick="location.href='list.do'">
				</c:if>
			</div>


			<!-- 판매 X -->
			<c:if test="${goods.goods_status == 1}">
				<div class="detail-info">
					<div class="item-image">
						<img
							src="${pageContext.request.contextPath}/upload/${goods.goods_img1}"
							width="400" height="400">
						<div class="re-like-re">
							<img src="${pageContext.request.contextPath}/images/Star111.png"
								width="48">
							<div id="average_rating">
								<h2>
									<span id="avg_rating_value">0</span>
								</h2>
							</div>
							<img src="${pageContext.request.contextPath}/images/like02.png">
							<h2>
								<span id="output_lcount"></span>
							</h2>
							<img src="${pageContext.request.contextPath}/images/review.png"
								width="48">
							<h2>${recount}</h2>
						</div>
					</div>


					<div class="item-detail">
						<form id="goods_buy">
							<input type="hidden" name="goods_num" value="${goods.goods_num}"
								id="goods_num"> <input type="hidden" name="goods_price"
								value="${goods.goods_price}" id="goods_price"> <input
								type="hidden" name="goods_quantity"
								value="${goods.goods_quantity}" id="goods_quantity">
							<ul>
								<li><br>
								<h1>${goods.goods_name}</h1>
									<br></li>
								<li class="align-right"><h3>
										<fmt:formatNumber value="${goods.goods_price}" />
										원
									</h3></li>
								<hr size="1" noshade="noshade" width="100%">
								<c:if test="${goods.goods_quantity > 0}">
									<li>재고 : <span><fmt:formatNumber
												value="${goods.goods_quantity}" /></span></li>
									<li>
										<div class="align-center">
											본 상품은 판매 중지되었습니다.
											<p>
												<input type="button" value="판매상품 보기"
													onclick="location.href='adminlist.do'">
										</div>
									</li>
									<hr size="1" noshade="noshade" width="100%">
									<ul class="detail-sub">
										<li>
											<%-- 좋아요 --%> <img id="output_like"
											data-num="${goods.goods_num}"
											src="${pageContext.request.contextPath}/images/like01.png"
											width="50">
										</li>
										<li><input type="button" id="buygoods" value="구매하기"
											disabled></li>
									</ul>
									<ul>
										<li class="cart-ask-btn"><input type="button"
											id="cartgoods" value="장바구니에 담기" disabled> <input
											type="button" value="문의하기"
											onclick="location.href='${pageContext.request.contextPath}/friendSearch/chatAdmin.do'">
										</li>
									</ul>

								</c:if>
								<c:if test="${goods.goods_quantity == 0}">
									<li>재고 : <span><fmt:formatNumber
												value="${goods.goods_quantity}" /></span></li>
									<li>
										<div class="align-center">
											본 상품은 판매 중지되었습니다.
											<p>
												<input type="button" value="판매상품 보기"
													onclick="location.href='adminlist.do'">
										</div>
									</li>
									<hr size="1" noshade="noshade" width="100%">
									<ul class="detail-sub">
										<li>
											<%-- 좋아요 --%> <img id="output_like"
											data-num="${goods.goods_num}"
											src="${pageContext.request.contextPath}/images/like01.png"
											width="50">
										</li>
										<li><input type="button" id="buygoods" value="구매하기"
											disabled></li>
									</ul>
									<ul>
										<li class="cart-ask-btn"><input type="button"
											id="cartgoods" value="장바구니에 담기" disabled> <input
											type="button" value="문의하기"
											onclick="location.href='${pageContext.request.contextPath}/friendSearch/chatAdmin.do'">
										</li>
									</ul>

								</c:if>

							</ul>
						</form>
					</div>

				</div>
				<br>
				<br>
				<hr size="1" noshade="noshade" width="100%">
				<div class="goods-detail-img">
					<img
						src="${pageContext.request.contextPath}/upload/${goods.goods_img2}"
						width="900">
				</div>
				<p class="align-center">${goods.goods_info}</p>

				<div id="output"></div>
				<div class="paging-button" style="display: none;">
					<input type="button" value="다음글 보기">
				</div>
				<div id="loading" style="display: none;">
					<img src="${pageContext.request.contextPath}/images/loading.gif"
						width="50" height="50">
				</div>

			</c:if>
			<!-- 판매 X -->

			<!-- 판매 상품 -->
			<c:if test="${goods.goods_status == 2}">
				<c:if test="${goods.goods_quantity > 0}">
					<input type="hidden" name="goods_num" value="${goods.goods_num}"
						id="goods_num">
					<div class="detail-info">
						<div class="item-image">
							<img
								src="${pageContext.request.contextPath}/upload/${goods.goods_img1}"
								width="400" height="400">
							<div class="re-like-re">
								<img src="${pageContext.request.contextPath}/images/Star111.png"
									width="48">
								<div id="average_rating">
									<h2>
										<span id="avg_rating_value">0</span>
									</h2>
								</div>
								<img src="${pageContext.request.contextPath}/images/like02.png">
								<h2>
									<span id="output_lcount"></span>
								</h2>
								<img src="${pageContext.request.contextPath}/images/review.png"
									width="48">
								<h2>${recount}</h2>
							</div>
						</div>


						<div class="item-detail">
							<form id="goods_buy" action="writeOrderForm.do" method="post">
								<input type="hidden" name="goods_num" value="${goods.goods_num}"
									id="goods_num"> <input type="hidden" name="goods_price"
									value="${goods.goods_price}" id="goods_price"> <input
									type="hidden" name="goods_quantity"
									value="${goods.goods_quantity}" id="goods_quantity">
								<ul>
									<li><br>
									<h1>${goods.goods_name}</h1>
										<br></li>
									<li class="align-right"><h3>
											<fmt:formatNumber value="${goods.goods_price}" />
											원
										</h3></li>
									<hr size="1" noshade="noshade" width="100%">

									<li>재고 : <span><fmt:formatNumber
												value="${goods.goods_quantity}" /></span></li>
									<li><label for="order_quantity">구매수량</label> <input
										type="number" name="order_quantity" min="1"
										max="${goods.goods_quantity}" autocomplete="off"
										id="order_quantity" class="quantity-width"></li>
									<hr size="1" noshade="noshade" width="100%">
									<li>총 상품 금액 <span id="goods_total_txt">0원</span></li>
									<hr size="1" noshade="noshade" width="100%">

									<ul class="detail-sub">
										<li>
											<%-- 좋아요 --%> <img id="output_like"
											data-num="${goods.goods_num}"
											src="${pageContext.request.contextPath}/images/like01.png"
											width="50">
										</li>
										<li><input type="submit" id="buygoods" value="구매하기" class="order_btn"></li>
									</ul>
									<ul>
										<li class="cart-ask-btn"><input type="button"
											id="cartgoods" value="장바구니에 담기"> <input type="button"
											value="문의하기"
											onclick="location.href='${pageContext.request.contextPath}/friendSearch/chatAdmin.do'">
										</li>
									</ul>
								</ul>
							</form>
						</div>

					</div>
					<br>
					<br>
					<hr size="1" noshade="noshade" width="100%">
					<div class="goods-detail-img">
						<img
							src="${pageContext.request.contextPath}/upload/${goods.goods_img2}"
							width="900">
					</div>
					<p class="align-center">${goods.goods_info}</p>

					<!-- 댓글시작 -->
					<div id="reply_div">
						<form id="re_form">
							<input type="hidden" name="goods_num" value="${goods.goods_num}"
								id="goods_num">
							<div class="rating-value">
								<label> <img
									src="${pageContext.request.contextPath}/images/emptystar.png"
									class="star" data-rating="1" id="star1">
								</label> <label> <img
									src="${pageContext.request.contextPath}/images/emptystar.png"
									class="star" data-rating="2" id="star2">
								</label> <label> <img
									src="${pageContext.request.contextPath}/images/emptystar.png"
									class="star" data-rating="3" id="star3">
								</label> <label> <img
									src="${pageContext.request.contextPath}/images/emptystar.png"
									class="star" data-rating="4" id="star4">
								</label> <label> <img
									src="${pageContext.request.contextPath}/images/emptystar.png"
									class="star" data-rating="5" id="star5">
								</label>
							</div>
							<textarea rows="3" cols="50" name="re_content" id="re_content"
								class="rep-content"
								<c:if test="${empty user_num}">disabled="disabled"</c:if>><c:if
									test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>
							<c:if test="${!empty user_num}">
								<!-- <div id="re_first">
									<span class="letter-count">300/300</span>
								</div>  -->
								<div id="re_second" class="align-right">
									<div id="my-btn">
										<input type="submit" value="리뷰 작성">
									</div>
								</div>
							</c:if>
						</form>
					</div>
					<!-- 댓글 목록 출력 시작 -->
					<div id="output"></div>
					<div class="paging-button" style="display: none;">
						<input type="button" value="다음글 보기">
					</div>
					<div id="loading" style="display: none;">
						<img src="${pageContext.request.contextPath}/images/loading.gif"
							width="50" height="50">
					</div>
					<!-- 댓글 목록 출력 끝 -->
					<!-- 댓글끝 -->

				</c:if>



				<!-- 재고 부족 -->
				<c:if test="${goods.goods_quantity == 0}">
					<input type="hidden" name="goods_num" value="${goods.goods_num}"
						id="goods_num">
					<div class="detail-info">
						<div class="item-image">
							<img
								src="${pageContext.request.contextPath}/upload/${goods.goods_img1}"
								width="400" height="400">
							<div class="re-like-re">
								<img src="${pageContext.request.contextPath}/images/Star111.png"
									width="48">
								<div id="average_rating">
									<h2>
										<span id="avg_rating_value">0</span>
									</h2>
								</div>
								<img src="${pageContext.request.contextPath}/images/like02.png">
								<h2>
									<span id="output_lcount"></span>
								</h2>
								<img src="${pageContext.request.contextPath}/images/review.png"
									width="48">
								<h2>${recount}</h2>
							</div>
						</div>


						<div class="item-detail">
							<form id="goods_buy">
								<input type="hidden" name="goods_num" value="${goods.goods_num}"
									id="goods_num"> <input type="hidden" name="goods_price"
									value="${goods.goods_price}" id="goods_price"> <input
									type="hidden" name="goods_quantity"
									value="${goods.goods_quantity}" id="goods_quantity">
								<ul>
									<li><br>
									<h1>${goods.goods_name}</h1>
										<br></li>
									<li class="align-right"><h3>
											<fmt:formatNumber value="${goods.goods_price}" />
											원
										</h3></li>
									<hr size="1" noshade="noshade" width="100%">

									<li>재고 : <span><fmt:formatNumber
												value="${goods.goods_quantity}" /></span></li>
									<li><label for="order_quantity">구매수량</label> <input
										type="number" name="order_quantity" min="1"
										max="${goods.goods_quantity}" autocomplete="off"
										id="order_quantity" class="quantity-width"></li>
									<hr size="1" noshade="noshade" width="100%">
									<li>총 상품 금액 <span id="goods_total_txt">0원</span></li>
									<hr size="1" noshade="noshade" width="100%">

									<ul class="detail-sub">
										<li>
											<%-- 좋아요 --%> <img id="output_like"
											data-num="${goods.goods_num}"
											src="${pageContext.request.contextPath}/images/like01.png"
											width="50">
										</li>
										<li><input type="button" id="buygoods" value="구매하기"
											disabled></li>
									</ul>
									<ul>
										<li class="cart-ask-btn"><input type="button"
											id="cartgoods" value="장바구니에 담기" disabled> <input
											type="button" value="문의하기"
											onclick="location.href='${pageContext.request.contextPath}/friendSearch/chatAdmin.do'">
										</li>
									</ul>
								</ul>
							</form>
						</div>

					</div>
					<br>
					<br>
					<hr size="1" noshade="noshade" width="100%">
					<div class="goods-detail-img">
						<img
							src="${pageContext.request.contextPath}/upload/${goods.goods_img2}"
							width="900">
					</div>
					<p class="align-center">${goods.goods_info}</p>

					<!-- 댓글시작 -->
					<div id="reply_div">
						<form id="re_form">
							<input type="hidden" name="goods_num" value="${goods.goods_num}"
								id="goods_num">
							<div class="rating-value">
								<label> <img
									src="${pageContext.request.contextPath}/images/emptystar.png"
									class="star" data-rating="1" id="star1">
								</label> <label> <img
									src="${pageContext.request.contextPath}/images/emptystar.png"
									class="star" data-rating="2" id="star2">
								</label> <label> <img
									src="${pageContext.request.contextPath}/images/emptystar.png"
									class="star" data-rating="3" id="star3">
								</label> <label> <img
									src="${pageContext.request.contextPath}/images/emptystar.png"
									class="star" data-rating="4" id="star4">
								</label> <label> <img
									src="${pageContext.request.contextPath}/images/emptystar.png"
									class="star" data-rating="5" id="star5">
								</label>
							</div>
							<textarea rows="3" cols="50" name="re_content" id="re_content"
								class="rep-content"
								<c:if test="${empty user_num}">disabled="disabled"</c:if>><c:if
									test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>
							<c:if test="${!empty user_num}">
								<!-- <div id="re_first">
									<span class="letter-count">300/300</span>
								</div>  -->
								<div id="re_second" class="align-right">
									<div id="my-btn">
										<input type="submit" value="리뷰 작성">
									</div>
								</div>
							</c:if>
						</form>
					</div>
					<!-- 댓글 목록 출력 시작 -->
					<div id="output"></div>
					<div class="paging-button" style="display: none;">
						<input type="button" value="다음글 보기">
					</div>
					<div id="loading" style="display: none;">
						<img src="${pageContext.request.contextPath}/images/loading.gif"
							width="50" height="50">
					</div>
					<!-- 댓글 목록 출력 끝 -->
					<!-- 댓글끝 -->
				</c:if>


			</c:if>




		</div>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
</body>
</html>