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
        <h2 style="width:133px;">지원 상세</h2>
        <hr size="3" noshade="noshade"><br>         
        <!-- 사용자 지원상세 페이지 -->
        <form>
            <div class="appl-radio">
                <div>
                    <label for="">지원분야</label><p>
                    <div class="radios">
                        <input type="radio" name="field" value="2" <c:if test="${appl.field ==2}">checked</c:if> disabled>트레이너
                        <input type="radio" name="field" value="3" <c:if test="${appl.field ==3}">checked</c:if> disabled>사무직원
                    </div>
                </div>
                <div>                    
                    <label for="">경력유무</label><p>
                    <div class="radios-2">
                        <input type="radio" name="career" value="1" <c:if test="${appl.career ==1}">checked</c:if> disabled>경력
                        <input type="radio" name="career" value="2" <c:if test="${appl.career ==2}">checked</c:if> disabled>신입
                    </div>
                </div>
            </div>

            <label for="source">지원경로</label><br>
            <textarea name="source" id="source" style="resize: none;" disabled>${appl.source}</textarea>
            
            <label for="">지원지점</label><br>
            <select name="appl_center" id="appl_center" disabled>
                <option value="1" <c:if test="${appl.appl_center ==1}">selected</c:if> disabled>강남점</option>
                <option value="2" <c:if test="${appl.appl_center ==2}">selected</c:if> disabled>강북점</option>
            </select>
            <br>
             
            <label for="appl_attachment">첨부파일</label><br>
            <div class="attachmentList">
            <c:if test="${!empty appl.appl_attachment}"><span>${appl.appl_attachment}가 등록되어있습니다.</span></c:if>
            <c:if test="${empty appl.appl_attachment}"><span>첨부된 파일이 없습니다.</span></c:if>
          	</div>
          	
            <label for="">자기소개</label><br>
            <textarea name="content" id="content" placeholder="간단한 자기소개와 이력을 입력해 주세요." disabled>${appl.content}</textarea>            
           
            
            <div class="appl-btn">
                <input type="button" value="목록보기" onclick="location.href='listByUser.do'">
                <c:if test="${appl.appl_status ==0}">
                <input type="button" value="수정하기" onclick="location.href='updateForm.do?appl_num=${appl.appl_num}'">
                </c:if>
            </div>
        </form>   
    </div>  
</div>    
<jsp:include page="/WEB-INF/views/board/board_footer.jsp"/>
</body>
</html>