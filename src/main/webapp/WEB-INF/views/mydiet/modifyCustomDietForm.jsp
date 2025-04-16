<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>사용자 지정 식단 수정</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
    <style>
        .message {
            color: green;
            margin-bottom: 10px;
        }
        .errorMessage {
            color: red;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="page-main">
        <!-- Header 영역 포함 -->
        <jsp:include page="/WEB-INF/views/common/header.jsp"/>
        
        <!-- Aside 영역 포함 -->
        <jsp:include page="/WEB-INF/views/common/aside_mybody.jsp"/>

        <!-- Main Content 영역 -->
        <div class="main-content">

            <!-- 사용자 지정 식단 수정 섹션 -->
            <section class="diet-plan">
                <div class="title">
                    <h2>사용자 지정 식단 수정</h2>
                </div>

                <!-- 메시지 표시 -->
                <c:if test="${not empty sessionScope.message}">
                    <div class="message">${sessionScope.message}</div>
                    <c:remove var="message" scope="session"/>
                </c:if>

                <!-- 에러 메시지 표시 -->
                <c:if test="${not empty errorMessage}">
                    <div class="errorMessage">${errorMessage}</div>
                </c:if>

                <!-- 사용자가 등록한 모든 사용자 지정 식단 목록 -->
                <div class="info-box">
                    <h3>등록된 사용자 지정 식단</h3>
                    <form action="${pageContext.request.contextPath}/mydiet/showCustomDietAction.do" method="post">
                        <select name="dietIdSelect" id="dietIdSelect" onchange="toggleDietSubmitButton()">
                            <option value="">식단을 선택하세요</option>
                            <!-- 사용자 지정 식단 목록 출력 -->
                            <c:forEach var="diet" items="${dietList}">
                                <option value="${diet.dietId}"
                                    <c:if test="${diet.dietId == selectedDietPlan.dietId}">selected</c:if>>
                                    ${diet.foodName} (칼로리: ${diet.calories}kcal, 단백질: ${diet.protein}g)
                                </option>
                            </c:forEach>
                        </select>
                        <!-- '확인' 버튼 추가 -->
                        <input id="modify-button-getdiet" class="button" type="submit" value="확인" disabled/>
                    </form>
                </div>

                <!-- 선택된 식단 데이터를 수정 폼에 자동으로 채우기 -->
                <c:if test="${not empty selectedDietPlan}">
                    <form action="${pageContext.request.contextPath}/mydiet/updateDietPlan.do" method="post">
                        <div class="info-box">
                            <!-- 식단 ID (Hidden) -->
                            <input type="hidden" name="dietId" value="${selectedDietPlan.dietId}" />

                            <div class="info-item">
                                <label for="foodName" class="label">음식 이름</label>
                                <input type="text" id="foodName" name="foodName" 
                                       value="${selectedDietPlan.foodName}" required />
                            </div>

                            <div class="info-item">
                                <label for="calories" class="label">칼로리(kcal)</label>
                                <input type="text" id="calories" name="calories" 
                                       value="${selectedDietPlan.calories}" required />
                            </div>

                            <div class="info-item">
                                <label for="protein" class="label">단백질(g)</label>
                                <input type="text" id="protein" name="protein" 
                                       value="${selectedDietPlan.protein}" required />
                            </div>

                            <div class="info-item">
                                <label for="carbohydrate" class="label">탄수화물(g)</label>
                                <input type="text" id="carbohydrate" name="carbohydrate" 
                                       value="${selectedDietPlan.carbohydrate}" required />
                            </div>

                            <div class="info-item">
                                <label for="fat" class="label">지방(g)</label>
                                <input type="text" id="fat" name="fat" 
                                       value="${selectedDietPlan.fat}" required />
                            </div>

                            <div class="info-item">
                                <label for="minerals" class="label">미네랄(mg)</label>
                                <input type="text" id="minerals" name="minerals" 
                                       value="${selectedDietPlan.minerals}" required />
                            </div>

                            <input id="modify-button-getdiet" class="button" type="submit" value="수정"/>
                        </div>
                    </form>
                </c:if>
            </section> <!-- section 태그 닫기 -->
        </div> <!-- main-content div 태그 닫기 -->
    </div> <!-- page-main div 태그 닫기 -->

    <script>
        function toggleDietSubmitButton() {
            var dietIdSelect = document.getElementById("dietIdSelect").value;
            var selectButton = document.getElementById("modify-button-getdiet");

            if (dietIdSelect) {
                selectButton.disabled = false;
            } else {
                selectButton.disabled = true;
            }
        }

        // 페이지 로드 시 초기화
        window.onload = function() {
            toggleDietSubmitButton();
        };
    </script>
</body>
</html>
