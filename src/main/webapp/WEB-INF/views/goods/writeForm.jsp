<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/IJ.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		//상품 등록 유효성 체크
		$('#write_form').submit(function(){
			const items = document.querySelectorAll('.input-check');
			for(let i=0;i<items.length;i++){
				if(items[i].value.trim()==''){
					const label = document.querySelector(
							       'label[for="'+items[i].id+'"]');
					alert(label.textContent + ' 필수 입력');
					items[i].value='';
					items[i].focus();
					return false;
				}			
			}//end of for		
		});//end of submit
	});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/common/aside_goods.jsp"/>
		<div class="content-main">
			<h2>상품 등록</h2>
			<div class="button-container">
			<c:if test="${status == 4}">
				<input type="button" value="상품 목록" onclick="location.href='adminlist.do'">
			</c:if>
			<c:if test="${status != 4}">
				<input type="button" value="상품 목록" onclick="location.href='list.do'">
			</c:if>
			</div>
			<form id="write_form" action="write.do" enctype="multipart/form-data" method="post">
				<ul>
					<li>
					<label>상품표시여부</label> 
					<input type="radio" name="goods_status" value="1" id="status1">미판매
					<input type="radio" name="goods_status" value="2" id="status2">판매
					<li>
					<label for="goods_name">상품명</label> 
					<input type="text" name="goods_name" id="goods_name" maxlength="20" class="input-check">
					</li>
					<li>
					<label for="goods_category">카테고리</label> 
					<select name="goods_category" id="goods_category" class="input-check">
	                <!-- 카테고리 목록 -->
	                <option value="식품">식품</option>
	                <option value="보충제 & 영양제">보충제 & 영양제</option>
	                <option value="운동용품">운동용품</option>
	            	</select>
					</li>
					<li>
					<label for="goods_price">가격</label> 
					<input type="number" name="goods_price" id="goods_price" min="1" max="999999999" class="input-check">
					</li>
					<li>
					<label for="goods_quantity">수량</label> 
					<input type="number" name="goods_quantity" id="goods_quantity" min="0" max="9999999" class="input-check">
					</li>
					<li>
					<label for="goods_img1">상품사진1</label>
					<input type="file" name="goods_img1" id="goods_img1"
					       accept="image/gif,image/png,image/jpeg">
					</li>
					<li>
					<label for="goods_img2">상품사진2</label>
					<input type="file" name="goods_img2" id="goods_img2"
					       accept="image/gif,image/png,image/jpeg">
					<li>
					<label for="goods_info">상품설명</label> 
					<textarea rows="5" cols="30" name="goods_info" id="goods_info"></textarea>
					</li>
				</ul>
				<div class="align-center">
					<input type="submit" value="등록" id="my-btn"> 
					<c:if test="${status == 4}">
					<input type="button" value="목록" onclick="location.href='adminlist.do'">
					</c:if>
				</div>
			</form>
		</div>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
</body>
</html>


