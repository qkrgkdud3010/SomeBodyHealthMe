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
<script type="text/javascript">
	window.onload = function(){
		const update_form = document.getElementById('appl_form');
		
		appl_form.onsubmit=function(){
			const field = document.querySelector('input[name="field"]:checked');
	        if (field==null) {
	            alert('지원분야를 선택해 주세요.');
	            return false;  
	        }

	        const career = document.querySelector('input[name="career"]:checked');
	        if (career==null) {
	            alert('경력유무를 선택해 주세요.');
	            return false; 
	        }	
						
			
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
        <h2>지원하기</h2>
        <hr size="3" noshade="noshade">
        <form action="write.do" id="appl_form" method="post" enctype="multipart/form-data">
            <div class="appl-radio">
                <div>
                    <label for="">지원분야</label><p>
                    <div class="radios">
                        <input type="radio" name="field" value="2">트레이너
                        <input type="radio" name="field" value="3">사무직원
                    </div>
                </div>
                <div>                    
                    <label for="">경력유무</label><p>
                    <div class="radios-2">
                        <input type="radio" name="career" value="1">경력
                        <input type="radio" name="career" value="2">신입
                    </div>
                </div>
            </div>

            <label for="source">지원경로</label><br>
            <textarea name="source" id="source" style="resize: none;" maxlength="50" placeholder="지원경로를 입력해 주세요. EX) SNS / 지인소개 기타..."></textarea>
            <br>
            
            <label for="appl_center">지원지점</label><br>
            <select name="appl_center" id="appl_center">
                <option value="1">강남점</option>
                <option value="2">강북점</option>
            </select>
            <br>
             
            <label for="appl_attachment">첨부파일</label><br>
            <input type="file" name="appl_attachment" accept=".hwp, .jpg, .jpeg, .png, .gif, .pdf, .doc, .docx"><br>
          
            <label for="">자기소개</label><br>
            <textarea name="content" id="content" placeholder="간단한 자기소개와 이력을 입력해 주세요." maxlength="500"></textarea>            
           
            
            <div class="appl-btn">
            <c:if test="${status != 4 }">
                <input type="button" value="지원취소" onclick="location.href='${pageContext.request.contextPath}/board/list.do'">
                <input type="submit" name="" value="지원하기">
            </c:if>
            <c:if test="${status == 4 }">
                <input type="button" value="지원목록">
                <input type="submit" name="" value="관리자전환">
            </c:if>
            </div>
        </form>        
    </div>  
</div>    
<jsp:include page="/WEB-INF/views/board/board_footer.jsp"/>
</body>


</html>