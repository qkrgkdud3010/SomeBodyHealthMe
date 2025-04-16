<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DietPlan 관리자 페이지</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
        }

        .page-main {
            display: flex;
            flex-direction: row;
            align-items: flex-start;
            gap: 20px;
            padding: 20px;
        }

        .main-content {
            flex-grow: 1;
            max-width: 75%;
        }

        .diet-table {
            width: 100%;
            max-width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            font-size: 14px;
            text-align: left;
            table-layout: fixed;
        }

        .diet-table th, .diet-table td {
            border: 1px solid #dddddd;
            padding: 8px;
            word-wrap: break-word;
            text-align: center;
        }

        .diet-table th {
            background-color: #f2f2f2;
        }

        button {
            padding: 5px 10px;
            font-size: 14px;
            border: none;
            background-color: #4CAF50;
            color: white;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <!-- Header -->
    <jsp:include page="/WEB-INF/views/common/header.jsp" />

	<jsp:include page="/WEB-INF/views/common/aside_mybody.jsp" />

    <!-- Sidebar -->
    <div class="page-main">
        
        <!-- Main Content -->
        <div class="main-content">
            <h2>DietPlan 관리자 수정 페이지</h2>
            <table class="diet-table">
                <thead>
                    <tr>
                        <th>음식 이름</th>
                        <th>칼로리</th>
                        <th>단백질</th>
                        <th>탄수화물</th>
                        <th>지방</th>
                        <th>공개 상태</th>
                        <th>노출 요청</th>
                        <th>수정</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="diet" items="${dietPlans}">
                        <tr>
                            <td>${diet.foodName}</td>
                            <td>${diet.calories}</td>
                            <td>${diet.protein}</td>
                            <td>${diet.carbohydrate}</td>
                            <td>${diet.fat}</td>
                            <td>
                                <select name="dietShow" form="form-${diet.dietId}">
                                    <option value="0" <c:if test="${diet.dietShow == 0}">selected</c:if>>비공개</option>
                                    <option value="1" <c:if test="${diet.dietShow == 1}">selected</c:if>>공개</option>
                                </select>
                            </td>
                            <td>
                                <select name="dietComment" form="form-${diet.dietId}">
                                    <option value="0" <c:if test="${diet.dietComment == 0}">selected</c:if>>비요청</option>
                                    <option value="1" <c:if test="${diet.dietComment == 1}">selected</c:if>>요청</option>
                                    <option value="2" <c:if test="${diet.dietComment == 2}">selected</c:if>>반려됨</option>
                                </select>
                            </td>
                            <td>
                                <form id="form-${diet.dietId}" action="${pageContext.request.contextPath}/mydiet/adminUpdateDietPlan.do" method="post">
                                    <input type="hidden" name="dietId" value="${diet.dietId}" />
                                    <button type="submit">수정</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Footer -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
