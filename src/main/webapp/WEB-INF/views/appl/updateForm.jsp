<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SBHM 지원하기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/applForm.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	window.onload = function(){
		const field = document.getElementsByName('field');
		for (let i = 0; i < field.length; i++) {
	        if (field[i].value == Number(${appl.field})) {
	            field[i].checked = true;  // 일치하는 라디오 버튼을 체크
	            break;  // 일치하는 값을 찾으면 반복문 종료
	        }
	    }
		const career = document.getElementsByName('career');
		for (let i = 0; i < career.length; i++) {
	        if (career[i].value == Number(${appl.career})) {
	        	career[i].checked = true;  // 일치하는 라디오 버튼을 체크
	            break;  // 일치하는 값을 찾으면 반복문 종료
	        }
	    }
		const appl_center = document.getElementsByName('appl_center');
		for (let i = 0; i < appl_center.length; i++) {
	        if (appl_center[i].value == Number(${appl.appl_center})) {
	        	appl_center[i].selected = true;  // 일치하는 라디오 버튼을 체크
	            break;  // 일치하는 값을 찾으면 반복문 종료
	        }
	    }	
		const update_form = document.getElementById('update_form');
		
		update_form.onsubmit=function(){
			const content = document.getElementById('content');
			if(content.value.trim()==''){
				alert('자기소개는 필수 입력 사항입니다.');
				content.value='';
				content.focus();
				return false;
			}
		}
		

		
	};
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/aside_board.jsp"/>
    <div class="container">
        <h2 style="width:128px;">내용 수정</h2>
        <hr size="3" noshade="noshade"><br>
        
        <form action="update.do" method="post" enctype="multipart/form-data" id="update_form">
        	<input type="hidden" name="appl_num" value="${appl.appl_num}">
            <div class="appl-radio">
                <div>
                    <label for="">지원분야</label><p>
                    <div class="radios">
                        <input type="radio" name="field" value="2" <c:if test="${appl.field ==2}">checked</c:if>>트레이너
                        <input type="radio" name="field" value="3" <c:if test="${appl.field ==3}">checked</c:if>>사무직원
                    </div>
                </div>
                <div>                    
                    <label for="">경력유무</label><p>
                    <div class="radios-2">
                        <input type="radio" name="career" value="1" <c:if test="${appl.career ==1}">checked</c:if>>경력
                        <input type="radio" name="career" value="2" <c:if test="${appl.career ==2}">checked</c:if>>신입
                    </div>
                </div>
            </div>

            <label for="source">지원경로</label><br>
            <textarea name="source" id="source" style="resize: none;" maxlength="50">${appl.source}</textarea>
            
            <label for="">지원지점</label><br>
            <select name="appl_center" id="appl_center">
                <option value="1" <c:if test="${appl.appl_center ==1}">selected</c:if>>강남점</option>
                <option value="2" <c:if test="${appl.appl_center ==2}">selected</c:if>>강북점</option>
            </select>
            <br>
             
            <label for="appl_attachment">첨부파일</label><br>
            <input type="file" name="appl_attachment" accept=".hwp, .jpg, .jpeg, .png, .gif, .pdf, .doc, .docx"><br>
            <div class="attachmentList">
            <c:if test="${!empty appl.appl_attachment}">
            <span>${appl.appl_attachment}가 등록되어있습니다.</span>            
            <input type="button" id="del_attachment" value="파일삭제">
            <script type="text/javascript">
            	$('#del_attachment').click(function(){
            		let choice = confirm('등록된 파일을 삭제하시겠습니까?\n(삭제 시, 수정 완료 없이 현재 지원 내용에서 삭제됩니다.)');
            		if(choice){
            			$.ajax({
            				url:'deleteAttachment.do',
            				type:'post',
            				data:{appl_num:${appl.appl_num}},
            				dataType:'json',
            				success:function(param){
            					if(param.result == 'logout'){
									alert('로그인 후 사용하세요');
								}else if(param.result == 'success'){
									$('.attachmentList').hide();
									alert('삭제 완료');
								}else if(param.result == 'wrongAccess'){
									alert('잘못된 접속입니다.');
								}else{
									alert('파일 삭제 오류 발생');
								}
            				},error:function(){
            					alert('네트워크 오류 발생');
            				}
						});
					}
				});
            </script>
            </c:if>
            <c:if test="${empty appl.appl_attachment}"><span>첨부된 파일이 없습니다.</span></c:if>
          	</div>
          	
            <label for="">자기소개</label><br>
            <textarea name="content" id="content" placeholder="간단한 자기소개와 이력을 입력해 주세요." maxlength="500">${appl.content}</textarea>            
           
            
            <div class="appl-btn">
                <input type="button" value="수정취소" onclick="location.href='${pageContext.request.contextPath}/appl/detail.do?appl_num=${appl.appl_num}'">
                <c:if test="${appl.appl_status ==0}">
                <input type="submit" value="수정완료">
                </c:if>
            </div>
        </form>   
    </div>
</div>
<jsp:include page="/WEB-INF/views/board/board_footer.jsp"/>    
</body>


</html>