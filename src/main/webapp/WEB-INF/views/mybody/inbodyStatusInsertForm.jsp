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

            <!-- 인바디 정보 입력 폼 -->
            <section class="user-info">
            
                <div class="title">
                    <h2>인바디 측정 정보 입력</h2>
                </div>

                <!-- 인바디 데이터 입력 폼 -->
                <form action="${pageContext.request.contextPath}/mybody/inbodyStatusInsert.do" method="post">
                    <div class="info-box">
                        <div class="info-item">
                            <label for="measurementDate" class="label">인바디 측정 날짜</label>
                            <input type="date" id="measurementDate" name="measurementDate" 
                                   placeholder="날짜를 선택하세요" 
                                   value="${not empty inbodyData.measurementDate ? inbodyData.measurementDate : ''}" 
                                   required>
                        </div>

                        <div class="info-item">
                            <label for="muscleMass" class="label">근육량(kg)</label>
                            <input type="text" id="muscleMass" name="muscleMass" 
                                   placeholder="예: 20" 
                                   value="${not empty inbodyData.muscleMass ? inbodyData.muscleMass : ''}" 
                                   pattern="\d*" maxlength="5" required>
                        </div>
                        
                        <div class="info-item">
                            <label for="bodyFatPercentage" class="label">체지방률(%)</label>
                            <input type="text" id="bodyFatPercentage" name="bodyFatPercentage" 
                                   placeholder="예: 15" 
                                   value="${not empty inbodyData.bodyFatPercentage ? inbodyData.bodyFatPercentage : ''}" 
                                   pattern="\d*" maxlength="5" required>
                        </div>

                    </div>

                    <!-- 폼 제출 버튼 -->
                    <div id="submit-button" class="button">
                        <input type="submit" value="제출" id="submit-btn"/>
                    </div>

                </form>
            </section>

        </div>
    </div>
</body>
</html>
