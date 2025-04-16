<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>건강지킴이</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
</head>
<body>
    <div class="page-main">
        <!-- Header 영역 (기존 include) -->
        <jsp:include page="/WEB-INF/views/common/header.jsp"/>
        
        <!-- Aside 영역 (기존 include) -->
        <jsp:include page="/WEB-INF/views/common/aside_mybody.jsp"/>

        <!-- Main Content 영역 추가 -->
        <div class="main-content">

            <!-- 인바디 정보 수정 폼 -->
            <section class="user-info">
                <div class="title">
                    <h2>인바디 측정 정보 수정</h2>
                </div>

                <!-- 사용자가 등록한 모든 인바디 데이터 목록 -->
                <div class="info-box">
                    <h3>등록된 인바디 데이터</h3>
                    <form action="${pageContext.request.contextPath}/mybody/inbodyStatusEdit.do" method="post">
                        <select name="measurementDate" id="measurementDate" onchange="toggleSubmitButton()">
                            <option value="">인바디 데이터를 선택하세요</option>
                            <!-- 데이터 목록 출력 -->
                            <c:forEach var="inbody" items="${inbodyStatusList}">
                                <option value="${inbody.measurementDate}">
                                    ${inbody.measurementDate} (근육량: ${inbody.muscleMass}kg, 체지방률: ${inbody.bodyFatPercentage}%)
                                </option>
                            </c:forEach>
                        </select>
                        <input id="modify-button-getinbody" class="button" type="submit" value="확인" disabled/>
                    </form>
                </div>

                <!-- 선택된 인바디 데이터를 자동으로 수정 폼에 채우기 -->
                <c:if test="${not empty inbodyStatus}">
                    <form action="${pageContext.request.contextPath}/mybody/inbodyStatusModify.do" method="post">
                        <div class="info-box">
                            <div class="info-item">
                                <label for="measurementDate" class="label">인바디 측정 날짜</label>
                                <input type="date" id="measurementDate" name="measurementDate" 
                                       value="${inbodyStatus.measurementDate}" required readonly />
                            </div>

                            <div class="info-item">
                                <label for="muscleMass" class="label">근육량(kg)</label>
                                <input type="text" id="muscleMass" name="muscleMass" 
                                       value="${inbodyStatus.muscleMass}" required />
                            </div>

                            <div class="info-item">
                                <label for="bodyFatPercentage" class="label">체지방률(%)</label>
                                <input type="text" id="bodyFatPercentage" name="bodyFatPercentage" 
                                       value="${inbodyStatus.bodyFatPercentage}" required />
                            </div>

                            <input id='modify-button-getinbody' class="button" type="submit" value="수정"/>
                        </div>
                    </form>
                </c:if>
            </section> <!-- section 태그 닫기 -->
        </div> <!-- main-content div 태그 닫기 -->
    </div> <!-- page-main div 태그 닫기 -->

    <script>
	    function toggleSubmitButton() {
	        var measurementDate = document.getElementById("measurementDate").value; // 선택된 값 가져오기
	        var modifyButton = document.getElementById("modify-button-getinbody"); // 버튼 요소
	
	        // measurementDate 값이 비어 있지 않으면 버튼 활성화, 비어 있으면 비활성화
	        if (measurementDate) {
	            modifyButton.disabled = false; // 버튼 활성화
	        } else {
	            modifyButton.disabled = true; // 버튼 비활성화
	        }
	    }
	
	    // 페이지 로드 시에도 초기화
	    window.onload = function() {
	        toggleSubmitButton(); // 페이지 로드 시에도 호출하여 초기 상태 설정
	    };
    </script>
</body>
</html>
