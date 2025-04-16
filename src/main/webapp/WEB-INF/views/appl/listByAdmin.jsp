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
<div class="page-main2">
<jsp:include page="/WEB-INF/views/common/aside_board.jsp"/>
	<div class="container2">
        <h2 style="width:135px;">지원 목록</h2>
        <div class="content-main"> 
            <!-- 관리자 검색 조건 -->
            <form action="listByAdmin.do" method="get" id="search_form">
            
                <!-- 확인 상태와 지원 분야를 한 줄로 배치 -->
                <div class="form-group">
                    <div>
                        <label for="appl_status">확인 상태</label>
                        <select id="appl_status" name="appl_status" required>
							<option value="9">전체</option>
                            <option value="0">미확인</option>
                            <option value="1">확인</option>                            
                            <option value="4">전환회원</option>                            
                            <option value="3">직원전환</option>                            
                        </select>
                    </div>
                    <div>
                        <label for="field">지원 분야</label>
                        <select id="field" name="field" required>
                        	<option value="9">전체</option>
                            <option value="2">트레이너</option>
                            <option value="3">사무직원</option>                            
                        </select>
                    </div>
                    <div>
                        <label for="career">경력 유무</label>
                        <select id="career" name="career" required>
							<option value="9">전체</option>
                            <option value="1">경력</option>
                            <option value="2">신입</option>                         
                        </select>
                    </div>
                    <div>
                        <label for="appl_center">지원 지점</label>
                        <select id="appl_center" name="appl_center" required>
                            <option value="9">전지점</option>
                            <option value="1">강남점</option>
                            <option value="2">강북점</option>                            
                        </select>
                    </div>
                </div>

                <!-- 이름 검색과 제출 버튼을 한 줄에 배치 -->
                <div class="search-group">
                    <input type="search" name="name" id="search_name" placeholder="이름 검색">
                    <button type="submit">검색</button>
                </div>

            </form>
        </div>    

        <!-- 지원 목록 테이블 -->
        <table >
            <tr>
                <th width="117px" height="35px">지원번호</th>
                <th width="90px">지원분야</th>
                <th width="351px">이름</th> <!-- 추가된 부분 -->
                <th width="117px">경력</th> <!-- 추가된 부분 -->
                <th width="117px">지원 지점</th>
                <th width="117px">등록일</th> <!-- 추가된 부분 -->
                <th width="175px">확인 상태</th>
            </tr>                
            <c:forEach var="appl" items="${list}">
            <tr>
                <td>${appl.appl_num}</td>
                <td>
                	<c:if test="${appl.field == 2}">트레이너</c:if>
                	<c:if test="${appl.field == 3}">사무직원</c:if>
                </td>
                <td>
                <a href="detailByAdmin.do?appl_num=${appl.appl_num}">
                	<c:if test="${!empty appl.name}">${appl.name}</c:if>
                	<c:if test="${empty appl.name}">${appl.login_id }</c:if>                	
                </a>
                </td> <!-- 이름 -->
                <td>
                	<c:if test="${appl.career == 1}">경력	</c:if>                	
                	<c:if test="${appl.career == 2}">신입	</c:if>
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
                	<c:if test="${appl.appl_status == 3}"><b style="color:red;">직원전환</b></c:if> 
                	<c:if test="${appl.appl_status == 4}">전환회원</c:if> 
                </td>                
            </tr>
            </c:forEach>
        </table>
        
        <c:if test="${empty list}">
        	<div class="nodata-msg">지원목록이 없습니다.</div>   		
        </c:if> 
        <div class="page">${page}</div> 
    </div>
</div>    
<jsp:include page="/WEB-INF/views/board/board_footer.jsp"/>
</body>


</html>