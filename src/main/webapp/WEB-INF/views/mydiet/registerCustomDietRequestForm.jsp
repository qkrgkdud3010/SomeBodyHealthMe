<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 지정 식단 등록 요청</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<style>
.message {
    color: green;
    margin-bottom: 10px;
}

.errorMessage {
    color: red;
    margin-bottom: 10px;
}

.info-item {
    margin-bottom: 10px;
}

.label {
    font-weight: bold;
    margin-right: 10px;
}

.info-box {
    margin-top: 20px;
}

.button {
    margin-top: 20px;
}
</style>
</head>
<body>
    <div class="page-main">
        <!-- Header 영역 포함 -->
        <jsp:include page="/WEB-INF/views/common/header.jsp" />

        <!-- Aside 영역 포함 -->
        <jsp:include page="/WEB-INF/views/common/aside_mybody.jsp" />

        <!-- Main Content 영역 -->
        <div class="main-content">

            <!-- 사용자 지정 식단 등록 요청 섹션 -->
            <section class="diet-plan">
                <div class="title">
                    <h2>사용자 지정 식단 등록 요청</h2>
                </div>

                <!-- 메시지 표시 -->
                <c:if test="${not empty sessionScope.message}">
                    <div class="message">${sessionScope.message}</div>
                    <c:remove var="message" scope="session" />
                </c:if>

                <!-- 에러 메시지 표시 -->
                <c:if test="${not empty errorMessage}">
                    <div class="errorMessage">${errorMessage}</div>
                </c:if>

                <!-- 사용자가 등록한 모든 사용자 지정 식단 목록 -->
                <div class="info-box">
                    <h3>등록된 사용자 지정 식단</h3>
                    <form
                        action="${pageContext.request.contextPath}/mydiet/registerCustomDietRequestForm.do"
                        method="post">
                        <select name="dietIdSelect" id="dietIdSelect"
                            onchange="toggleDietSubmitButton()">
                            <option value="">식단을 선택하세요</option>
                            <!-- 사용자 지정 식단 목록 출력 -->
                            <c:forEach var="diet" items="${dietList}">
                                <option value="${diet.dietId}"
                                    <c:if test="${diet.dietId == selectedDietPlan.dietId}">selected</c:if>>
                                    ${diet.foodName} (칼로리: ${diet.calories}kcal, 단백질:
                                    ${diet.protein}g)</option>
                            </c:forEach>
                        </select>
                        <!-- '확인' 버튼 추가 -->
                        <input id="modify-button-getdiet" class="button" type="submit"
                            value="확인" disabled />
                    </form>
                </div>

                <!-- 선택된 식단 데이터를 조회 및 등록 요청 -->
                <c:if test="${not empty selectedDietPlan}">
                    <div class="info-box">
                        <!-- 식단 정보 표시 -->
                        <div class="info-item">
                            <label class="label">음식 이름:</label> <span>${selectedDietPlan.foodName}</span>
                        </div>

                        <div class="info-item">
                            <label class="label">칼로리(kcal):</label> <span>${selectedDietPlan.calories}</span>
                        </div>

                        <div class="info-item">
                            <label class="label">단백질(g):</label> <span>${selectedDietPlan.protein}</span>
                        </div>

                        <div class="info-item">
                            <label class="label">탄수화물(g):</label> <span>${selectedDietPlan.carbohydrate}</span>
                        </div>

                        <div class="info-item">
                            <label class="label">지방(g):</label> <span>${selectedDietPlan.fat}</span>
                        </div>

                        <div class="info-item">
                            <label class="label">미네랄(mg):</label> <span>${selectedDietPlan.minerals}</span>
                        </div>

                        <!-- 등록 요청 버튼 -->
                        <form action="${pageContext.request.contextPath}/mydiet/registerCustomDietRequest.do" method="post">
                            <!-- 식단 ID (Hidden) -->
                            <input type="hidden" name="dietId" value="${selectedDietPlan.dietId}" />
                            <input id="modify-button-getdiet" class="button" type="submit" value="등록 요청"
                                <c:if test="${selectedDietPlan.dietComment == 1 || selectedDietPlan.dietComment == 2}">disabled="disabled"</c:if>
                            />
                        </form>

                        <!-- 상태에 따른 메시지 표시 -->
                        <c:choose>
                            <c:when test="${selectedDietPlan.dietComment == 1}">
                                <p>이미 등록 요청 중입니다.</p>
                            </c:when>
                            <c:when test="${selectedDietPlan.dietComment == 2}">
                                <p>이미 등록되었습니다.</p>
                            </c:when>
                            <c:when test="${selectedDietPlan.dietComment == 3}">
                                <p>등록이 반려되었습니다. 수정 후 다시 등록 요청할 수 있습니다.</p>
                            </c:when>
                            <c:otherwise>
                                <!-- 미등록 상태이므로 추가 메시지 없이 버튼만 표시 -->
                            </c:otherwise>
                        </c:choose>

                    </div>
                </c:if>
            </section>
            <!-- section 태그 닫기 -->
        </div>
        <!-- main-content div 태그 닫기 -->
    </div>
    <!-- page-main div 태그 닫기 -->

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
