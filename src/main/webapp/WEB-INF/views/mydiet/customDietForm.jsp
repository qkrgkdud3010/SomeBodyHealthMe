<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>건강지킴이 - 식단 입력</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
</head>
<body>
    <div class="page-main">
        <!-- Header 영역 (기존 include) -->
        <jsp:include page="/WEB-INF/views/common/header.jsp" />

        <!-- Aside 영역 (기존 include) -->
        <jsp:include page="/WEB-INF/views/common/aside_mybody.jsp" />

        <!-- Main Content 영역 추가 -->
        <div class="main-content">

            <!-- 사용자 지정 식단 입력 폼 -->
            <section class="diet-plan">

                <div class="title">
                    <h2>사용자 지정 식단 입력</h2>
                </div>

                <!-- 식단 데이터 입력 폼 -->
                <form
                    action="${pageContext.request.contextPath}/mydiet/insertDiet.do"
                    method="post">
                    <div class="info-box">
                        <div class="form-item">
                            <label for="foodName" class="label">식품 이름</label> 
                            <input
                                type="text" id="foodName" name="foodName" placeholder="예: 닭가슴살"
                                value="${not empty dietData.foodName ? dietData.foodName : ''}"
                                required>
                        </div>

                        <div class="form-item">
                            <label for="calories" class="label">칼로리(kcal)</label> 
                            <input
                                type="text" id="calories" name="calories" placeholder="예: 200"
                                value="${not empty dietData.calories ? dietData.calories : ''}"
                                pattern="\d*" maxlength="5" required>
                        </div>

                        <div class="form-item">
                            <label for="protein" class="label">단백질(g)</label> 
                            <input
                                type="text" id="protein" name="protein" placeholder="예: 30"
                                value="${not empty dietData.protein ? dietData.protein : ''}"
                                pattern="\d*" maxlength="5" required>
                        </div>

                        <div class="form-item">
                            <label for="carbohydrate" class="label">탄수화물(g)</label> 
                            <input
                                type="text" id="carbohydrate" name="carbohydrate"
                                placeholder="예: 50"
                                value="${not empty dietData.carbohydrate ? dietData.carbohydrate : ''}"
                                pattern="\d*" maxlength="5" required>
                        </div>

                        <div class="form-item">
                            <label for="fat" class="label">지방(g)</label> 
                            <input type="text"
                                id="fat" name="fat" placeholder="예: 10"
                                value="${not empty dietData.fat ? dietData.fat : ''}"
                                pattern="\d*" maxlength="5" required>
                        </div>

                        <div class="form-item">
                            <label for="minerals" class="label">미네랄(g)</label> 
                            <input
                                type="text" id="minerals" name="minerals" placeholder="예: 5"
                                value="${not empty dietData.minerals ? dietData.minerals : ''}"
                                pattern="\d*" maxlength="5">
                        </div>
                    </div>

                    <!-- 버튼 박스 -->
                    <div class="button-box">
                        <!-- 폼 제출 버튼 -->
                        <div id="submit-button" class="button">
                            <input type="submit" value="식단 제출" id="submit-btn" />
                        </div>

                        <!-- 사용자 지정 식단 보기 버튼 -->
                        <div id="view-button">
                            <input type="button" value="식단 보기" id="insert-button"
                                onclick="location.href='${pageContext.request.contextPath}/mydiet/selectCustomDiet.do'" />
                        </div>
                    </div>
                </form> <!-- 닫는 form 태그 추가 -->
            </section>
        </div>
    </div>
</body>
</html>
