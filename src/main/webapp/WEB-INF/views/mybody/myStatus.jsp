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
            <!-- 사용자 정보 섹션 (키, 체중, BMI 등) -->
            <section class="user-info">
            
                <div class="title">
                    <h2>사용자 건강 정보</h2>
                    
                    <!-- 조건에 따라 수정 또는 등록 버튼 표시 -->
                    <c:if test="${not empty mybodystatus}">
                        <!-- mybodystatus가 존재하면 수정 버튼 보이기 -->
                        <div id='modify-button' class='button'>
                            <a href="${pageContext.request.contextPath}/mybody/myStatusModifyForm.do">수정</a>
                        </div>
                    </c:if>
                    
                    <c:if test="${empty mybodystatus}">
                        <!-- mybodystatus가 비어 있으면 등록 버튼 보이기 -->
                        <div id='insert-button' class='button'>
                            <a href="${pageContext.request.contextPath}/mybody/myStatusInsertForm.do">등록</a>
                        </div>
                    </c:if>
                </div>
                
                <div class="info-box">
                    <div class="info-item">
                        <span class="label">키:</span>
                        <span class="value">${mybodystatus.height}</span> <!-- 예시값 -->
                    </div>
                    <div class="info-item">
                        <span class="label">체중:</span>
                        <span class="value">${mybodystatus.weight}</span> <!-- 예시값 -->
                    </div>
                    
                    <div class="info-item">
                        <span class="label">나이:</span>
                        <span class="value">${mybodystatus.age}</span> <!-- 예시값 -->
                    </div>
                    
                    <div class="info-item">
                        <span class="label">BMI 지수:</span>
                        <span class="value">${mybodystatus.bmi}</span> <!-- 예시값 -->
                    </div>
                    <div class="info-item">
                        <span class="label">운동 목표:</span>
                        <span class="value">${mybodystatus.goal}</span> <!-- 예시값 -->
                    </div>
                    <div class="info-item">
                        <span class="label">성별:</span>
                        <span class="value">${mybodystatus.gender}</span> <!-- 예시값 -->
                    </div>
                </div>
            </section>
        </div>
    </div>
</body>
</html>
