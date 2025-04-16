<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SBHM 지원하기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/applList.css" type="text/css">

</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/aside_board.jsp"/>
	<div class="container">
        <h2 style="width:135px;">지원 목록</h2>
        <!-- 지원 목록 테이블 -->
        <table >
            <tr>
                <th width="117px" height="35px">지원번호</th>
                <th width="400px">자기소개</th> <!-- 추가된 부분 -->
                <th width="117px">지원분야</th> <!-- 추가된 부분 -->
                <th width="117px">지원 지점</th>
                <th width="117px">등록일</th> <!-- 추가된 부분 -->
                <th width="125px">확인 상태</th>
            </tr>                
            <c:forEach var="appl" items="${list}">
            <tr>
                <td>${appl.appl_num}</td>
                <td>
                	<a href="detail.do?appl_num=${appl.appl_num}">${appl.content}</a>
                </td> <!-- 내용 -->
                <td>
                	<c:if test="${appl.field ==2}">트레이너</c:if>
                	<c:if test="${appl.field ==3}">사무직원</c:if>
                </td>
                <td>
           			<c:if test="${appl.appl_center == 1}">강남점</c:if>
           			<c:if test="${appl.appl_center == 2}">강북점</c:if>                	
                </td>
                <td>
                	<c:if test="${!empty appl.appl_modifydate }">
                	${appl.appl_modifydate}
	                </c:if>
    	            <c:if test="${empty appl.appl_modifydate }">
        	       	${appl.appl_regdate}
            	    </c:if>
                </td>
                <td>
                	<c:if test="${appl.appl_status == 0}">미확인</c:if> 
                	<c:if test="${appl.appl_status == 1}">확인</c:if> 
                	<c:if test="${appl.appl_status == 3}">직원전환</c:if> 
                	<c:if test="${appl.appl_status == 4}">확인</c:if> 
                </td>                
            </tr>
            </c:forEach>
        </table>
        
        <c:if test="${empty list}">
        	<div class="nodata-msg">지원목록이 없습니다.</div>   		
        </c:if> 
    </div>
</div>
<jsp:include page="/WEB-INF/views/board/board_footer.jsp"/>
</body>


</html>