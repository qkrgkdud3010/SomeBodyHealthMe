<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글 수정</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/IJ.css" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
	<script type="text/javascript">
		$(function(){
			//게시판  유효성 체크 
			$('#update_form').submit(function(){
				const items = document.querySelectorAll('.input-check');
				for(let i=0; i<items.length;i++){
					if(items[i].value.trim()==''){
						const label = document.querySelector('label[for="'+items[i].id+'"]');
						alert(label.textContent + ' 필수입력');
						items[i].value='';
						$(items[i]).focus();
						return false;
					}
				}//end of for
			});//end of submit
			
			//상품 이미지 미리보기
			$('#goods_img1,#goods_img2').change(function(){
				let my_img = this.files[0];
				let $goods_img = $(this).parent().find('.my-photo');
				if(!my_img){
					$goods_img.attr('src','../upload/'+$goods_img.attr('data-img'));
					return;
				}
				
				//이미지 용량 체크
				if(my_img.size > 1024 * 1024){
					alert(Math.round(my_img.size/1024) + 'kbytes(1024kbytes까지만 업로드 가능)');
					$(this).val('');
					return;
				}
				
				const reader = new FileReader();
				reader.readAsDataURL(my_img);
				
				reader.onload = function(){
					$goods_img.attr('src',reader.result);
				};
				
			});
			
		});
	
	</script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<jsp:include page="/WEB-INF/views/common/aside_goods.jsp" />
		<div class="content-main">
			<div class="button-container">
			<input type="button" value="상품으로" onclick="location.href='detail.do?goods_num=${goods.goods_num}'">
			<c:if test="${status == 4}">
				<input type="button" value="상품 목록" onclick="location.href='adminlist.do'">
			</c:if>
			<c:if test="${status != 4}">
				<input type="button" value="상품 목록" onclick="location.href='list.do'">
			</c:if>
			</div>
			<form id="update_form" action="update.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="goods_num" value="${goods.goods_num}">
				<ul>
					<li><h2 class="align-center">상품 수정</h2></li>
					<li>
					<label>상품표시여부</label> 
					<input type="radio" name="goods_status" value="1" id="status1"
					<c:if test="${goods.goods_status == 1}">checked</c:if>>미판매
					<input type="radio" name="goods_status" value="2" id="status2"
					<c:if test="${goods.goods_status == 2}">checked</c:if>>판매
					<li>
					<li>
					<label for="goods_name">상품명</label> 
					<input type="text" name="goods_name" id="goods_name" maxlength="10" value="${goods.goods_name}" class="input-check">
					</li>
					<li>
					<li>
		            <label for="goods_category">카테고리</label> 
		            <select name="goods_category" id="goods_category" class="input-check">
	                <!-- 카테고리 목록 -->
	                <option value="식품" ${goods.goods_category == '식품' ? 'selected' : ''}>식품</option>
	                <option value="보충제 & 영양제" ${goods.goods_category == '보충제 & 영양제' ? 'selected' : ''}>보충제 & 영양제</option>
	                <option value="운동용품" ${goods.goods_category == '운동용품' ? 'selected' : ''}>운동용품</option>
	            	</select>
        			</li>
        			<li>
					<label for="goods_price">가격</label> 
					<input type="number" name="goods_price" id="goods_price" min="1" max="999999999" value="${goods.goods_price}" class="input-check">
					</li>
					<li>
					<label for="goods_quantity">수량</label> 
					<input type="number" name="goods_quantity" id="goods_quantity" min="0" max="9999999" value="${goods.goods_quantity}" class="input-check">
					</li>
					<li>
					<label for="goods_img1">상품사진1</label>
					<img src="${pageContext.request.contextPath}/upload/${goods.goods_img1}"
							data-img="${goods.goods_img1}" width="50" height="50"
							class="my-photo">
					<br>
					<input type="file" name="goods_img1" id="goods_img1"
					       accept="image/gif,image/png,image/jpeg"  
					       class="form-notice">
					</li>
					<li>
					<label for="goods_img2">상품사진2</label> 
					<img src="${pageContext.request.contextPath}/upload/${goods.goods_img2}"
							data-img="${goods.goods_img2}" width="50" height="50"
							class="my-photo">
					<br>
					<input type="file" name="goods_img2" id="goods_img2"
					       accept="image/gif,image/png,image/jpeg"  
					       class="form-notice">
					</li>
					<li>
					<label for="goods_info">상품설명</label> 
					<textarea rows="5" cols="30" name="goods_info" id="goods_info">${goods.goods_info}</textarea>
					</li>
				</ul>
				<div class="align-center">
					<input id="submit-btn" type="submit" value="수정">
				</div>
			</form>
		</div>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
</body>
</html>