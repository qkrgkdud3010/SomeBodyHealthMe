<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>건강지킴이 - 식사 기록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
</head>
<body>
    <div class="page-main">
        <jsp:include page="/WEB-INF/views/common/header.jsp" />
        <jsp:include page="/WEB-INF/views/common/aside_mybody.jsp" />

        <div class="main-content-nutrition">
            <div class="meal-records">
                <!-- 아침 -->
                <div class="meal-box" id="breakfast">
                    <h3>아침</h3>
                    <div class="meal-info">
                        <c:choose>
                            <c:when test="${not empty breakfastLogs}">
                                <c:forEach var="meal" items="${breakfastLogs}">
                                    <span>${meal.foodName} (${fn:substring(meal.createdAt, 0, 10)})</span><br/>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <span>아침에 먹은 음식을 기록하세요</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="nutrition-summary">
                        <strong>영양 성분 합계:</strong><br/>
                        칼로리: ${breakfastSummary.calories} kcal<br/>
                        단백질: ${breakfastSummary.protein} g<br/>
                        탄수화물: ${breakfastSummary.carbohydrate} g<br/>
                        지방: ${breakfastSummary.fat} g<br/>
                    </div>
                    <button class="add-button"
                        onclick="location.href='${pageContext.request.contextPath}/mydiet/noteDietForm.do'">+</button>
                </div>

                <!-- 점심 -->
                <div class="meal-box" id="lunch">
                    <h3>점심</h3>
                    <div class="meal-info">
                        <c:choose>
                            <c:when test="${not empty lunchLogs}">
                                <c:forEach var="meal" items="${lunchLogs}">
                                    <span>${meal.foodName} (${fn:substring(meal.createdAt, 0, 10)})</span><br/>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <span>점심에 먹은 음식을 기록하세요</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="nutrition-summary">
                        <strong>영양 성분 합계:</strong><br/>
                        칼로리: ${lunchSummary.calories} kcal<br/>
                        단백질: ${lunchSummary.protein} g<br/>
                        탄수화물: ${lunchSummary.carbohydrate} g<br/>
                        지방: ${lunchSummary.fat} g<br/>
                    </div>
                    <button class="add-button"
                        onclick="location.href='${pageContext.request.contextPath}/mydiet/noteDietForm.do'">+</button>
                </div>

                <!-- 저녁 -->
                <div class="meal-box" id="dinner">
                    <h3>저녁</h3>
                    <div class="meal-info">
                        <c:choose>
                            <c:when test="${not empty dinnerLogs}">
                                <c:forEach var="meal" items="${dinnerLogs}">
                                    <span>${meal.foodName} (${fn:substring(meal.createdAt, 0, 10)})</span><br/>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <span>저녁에 먹은 음식을 기록하세요</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="nutrition-summary">
                        <strong>영양 성분 합계:</strong><br/>
                        칼로리: ${dinnerSummary.calories} kcal<br/>
                        단백질: ${dinnerSummary.protein} g<br/>
                        탄수화물: ${dinnerSummary.carbohydrate} g<br/>
                        지방: ${dinnerSummary.fat} g<br/>
                    </div>
                    <button class="add-button"
                        onclick="location.href='${pageContext.request.contextPath}/mydiet/noteDietForm.do'">+</button>
                </div>

                <!-- 간식 -->
                <div class="meal-box" id="snack">
                    <h3>간식</h3>
                    <div class="meal-info">
                        <c:choose>
                            <c:when test="${not empty snackLogs}">
                                <c:forEach var="meal" items="${snackLogs}">
                                    <span>${meal.foodName} (${fn:substring(meal.createdAt, 0, 10)})</span><br/>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <span>간식에 먹은 음식을 기록하세요</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="nutrition-summary">
                        <strong>영양 성분 합계:</strong><br/>
                        칼로리: ${snackSummary.calories} kcal<br/>
                        단백질: ${snackSummary.protein} g<br/>
                        탄수화물: ${snackSummary.carbohydrate} g<br/>
                        지방: ${snackSummary.fat} g<br/>
                    </div>
                    <button class="add-button"
                        onclick="location.href='${pageContext.request.contextPath}/mydiet/noteDietForm.do'">+</button>
                </div>
            </div>

            <!-- 총 영양 성분 합계 -->
            <div class="total-summary">
                <h3>총 영양 성분 합계</h3>
                칼로리: ${totalSummary.calories} kcal<br/>
                단백질: ${totalSummary.protein} g<br/>
                탄수화물: ${totalSummary.carbohydrate} g<br/>
                지방: ${totalSummary.fat} g<br/>
            </div>
        </div>
    </div>
</body>
</html>
