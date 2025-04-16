<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SBHM 지원하기 : 지원 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/applForm.css" type="text/css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/aside_board.jsp"/>
  <div class="container">
        <h2 style="width:135px;">지원 상세</h2>
        <hr size="3" noshade="noshade"><br>  
        <div class="user-info">
        	<c:if test="${appl.status == 0}">
        		${appl.login_id}님은 탈퇴회원입니다.
        	</c:if>
        	<c:if test="${appl.status == 5}">
        		${appl.login_id}님은 정지회원입니다.
        	</c:if>
        	<c:if test="${appl.status != 5}">
			지원자 기본정보(지원번호 : ${appl.appl_num})
			<hr size="3" noshade="noshade"> 
        	<ul>
        		<li>이름 : ${appl.name}</li>
        		<li>
        			등급 : <c:if test="${appl.status == 1}">일반회원</c:if>
        			<c:if test="${appl.status == 2}">트레이너</c:if>
        			<c:if test="${appl.status == 3}">사무직원</c:if>
        		</li>
        		<li>회원번호 : ${appl.user_num}</li>
        		<li>생년월일 : ${appl.birth_date}</li>
        		<li>전화번호 : ${appl.phone}</li>
        	</ul>
        	</c:if>
        </div>
        <form action="updateStatus.do" method="post" id="appl_form" enctype="multipart/form-data">
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
            <textarea name="source" id="source" style="resize: none;" disabled>${appl.source}</textarea>
            
            <label for="">지원지점</label><br>
            <select name="appl_center" id="appl_center">
                <option value="1" <c:if test="${appl.appl_center ==1}">selected</c:if>>강남점</option>
                <option value="2" <c:if test="${appl.appl_center ==2}">selected</c:if>>강북점</option>
            </select>
            <br>
             
            <label for="appl_attachment">첨부파일다운</label><br>
            <div class="attachmentList">
            <c:if test="${!empty appl.appl_attachment}"><span>${appl.appl_attachment}가 등록되어있습니다.</span></c:if>
            <c:if test="${empty appl.appl_attachment}"><span>첨부된 파일이 없습니다.</span></c:if>
            <c:if test="${!empty appl.appl_attachment}"><a href="${pageContext.request.contextPath}/appl/download.do?appl_num=${appl.appl_num}" id="download_file">파일다운로드</a></c:if>
          	</div>                        
          
            <label for="">자기소개</label><br>
            <textarea name="content" id="content" placeholder="간단한 자기소개와 이력을 입력해 주세요." disabled>${appl.content}</textarea>            
           
            
            <div class="appl-btn">            
            <input type="button" value="지원목록" onclick="location.href='listByAdmin.do'">
            <c:if test="${appl.status == 1 && appl.appl_status <= 1}">
            <input type="submit" value="관리자전환" id="update_btn" style="width:110px;">
            </c:if>               
            </div>
            <script type="text/javascript">
            	const update_btn = document.getElementById('update_btn');
            	update_btn.onclick=function(event){
            		let choice = confirm('${appl.name}(${appl.user_num})님을 관리자로 전환하시겠습니까?');
            		if(!choice){            		
            			event.preventDefault();                      
            		}
            	};
            </script>
        </form>   
    </div>  
</div>    
<jsp:include page="/WEB-INF/views/board/board_footer.jsp"/>
</body>


</html>