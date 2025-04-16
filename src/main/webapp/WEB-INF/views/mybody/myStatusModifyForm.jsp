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
                </div>

                <!-- 사용자 정보 입력 폼 -->
                <form action="${pageContext.request.contextPath}/mybody/myStatusModify.do" method="post">
                    <div class="info-box">
                        <div class="info-item">
						    <label for="height" class="label">키:</label>
						    <input type="text" id="height" name="height" 
						           placeholder="예: 170" 
						           value="${not empty healthInfo.height ? healthInfo.height : ''}" 
						           pattern="\d*" maxlength="5" required>
							</div>
						
							<div class="info-item">
							    <label for="weight" class="label">체중:</label>
							    <input type="text" id="weight" name="weight" 
							           placeholder="예: 70" 
							           value="${not empty healthInfo.weight ? healthInfo.weight : ''}" 
							           pattern="\d*" maxlength="5" required>
							</div>
                        
                        <div class="info-item">
						    <label for="age" class="label">나이:</label>
						    <input type="text" id="age" name="age" 
							     placeholder="예: 24" 
							     value="${not empty healthInfo.age ? healthInfo.age : ''}" 
							     pattern="\d*" maxlength="3" required>
						</div>
                        
                        <div class="info-item">
                            <label for="goal" class="label">운동 목표:</label>
                            <select id="goal" name="goal">
                                <option value="유지" 
                                        ${healthInfo.goal == '유지' ? 'selected' : ''}>유지</option>
                                <option value="증가" 
                                        ${healthInfo.goal == '증가' ? 'selected' : ''}>증가</option>
                                <option value="감량" 
                                        ${healthInfo.goal == '감량' ? 'selected' : ''}>감량</option>
                            </select>
                        </div>

                        <div class="info-item">
                            <label for="gender" class="label">성별:</label>
                            <select id="gender" name="gender">
                                <option value="M" 
                                        ${healthInfo.gender == 'male' ? 'selected' : ''}>남성</option>
                                <option value="F" 
                                        ${healthInfo.gender == 'female' ? 'selected' : ''}>여성</option>
                            </select>
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
