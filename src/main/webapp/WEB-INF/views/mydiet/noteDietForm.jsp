<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>음식 검색</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style type="text/css">
        /* 기존 스타일 유지 */
        /* 기본 레이아웃 설정 */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        .page-main {
            display: flex;
            justify-content: space-between;
            padding: 20px;
        }

        .search-form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 600px;
            margin-bottom: 30px;
        }

        .search-form h2 {
            font-size: 24px;
            margin-bottom: 10px;
        }

        .search-form input {
            width: calc(100% - 110px);
            padding: 10px;
            font-size: 16px;
            border-radius: 4px;
            border: 1px solid #ddd;
            margin-right: 10px;
        }

        .search-form button {
            padding: 10px 15px;
            font-size: 16px;
            border-radius: 4px;
            border: 1px solid #ddd;
            background-color: #28a745;
            color: #fff;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .search-form button:hover {
            background-color: #218838;
        }

        .search-results {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 800px;
        }

        .search-results h2 {
            font-size: 22px;
            margin-bottom: 20px;
        }

        .search-results ul {
            list-style-type: none;
            padding: 0;
        }

        .search-results li {
            padding: 10px;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            margin-bottom: 10px;
            border-radius: 4px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .search-results .pagination {
            margin-top: 20px;
            text-align: center;
        }

        .search-results .pagination a, .search-results .pagination span {
            display: inline-block;
            padding: 8px 16px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            cursor: pointer;
            margin: 0 5px;
        }

        .search-results .pagination a:hover, .search-results .pagination span:hover {
            background-color: #0056b3;
        }

        /* Aside 스타일 */
        aside {
            width: 30%;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        footer {
            text-align: center;
            background-color: #333;
            color: #fff;
            padding: 20px;
            margin-top: 30px;
        }

        /* 팝업창 스타일 */
        .modal {
            display: none; /* 팝업 기본적으로 숨김 */
            position: fixed; /* 화면에 고정 */
            top: 0; /* 화면의 최상단 */
            left: 0; /* 화면의 좌측 */
            width: 100%; /* 팝업 너비를 100%로 설정 */
            height: 100%; /* 팝업 높이도 100%로 설정 */
            background-color: rgba(0, 0, 0, 0.5); /* 반투명 배경 */
            z-index: 1000; /* 다른 요소 위에 표시 */
            justify-content: center; /* 중앙 정렬 */
            align-items: center; /* 중앙 정렬 */
        }

        /* 팝업 내용 */
        .modal-content {
            background-color: white;
            border-radius: 10px;
            padding: 20px;
            width: 80%; /* 팝업의 최대 너비를 80%로 설정 */
            max-width: 600px; /* 팝업 내용의 최대 너비를 600px로 설정 */
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            position: relative;
        }

        .close-button {
            font-size: 30px;
            color: #888;
            cursor: pointer;
            position: absolute;
            top: 10px;
            right: 15px;
        }

        .close-button:hover {
            color: #000;
        }

        .modal-content form {
            display: flex;
            flex-direction: column;
        }

        .modal-content label {
            margin-bottom: 10px;
        }

        .modal-content input[type="radio"] {
            margin-right: 5px;
        }

        .modal-content button[type="submit"] {
            padding: 10px;
            font-size: 16px;
            border-radius: 4px;
            border: none;
            background-color: #28a745;
            color: #fff;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .modal-content button[type="submit"]:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <!-- Header Include -->
    <jsp:include page="/WEB-INF/views/common/header.jsp" />

    <div class="page-main">
        <!-- Aside Include -->
        <jsp:include page="/WEB-INF/views/common/aside_mybody.jsp" />

        <!-- 음식 검색 폼 -->
        <div class="search-form">
            <h2>음식 검색</h2>
            <form action="${pageContext.request.contextPath}/mydiet/noteDietSearch.do" method="get">
                <input type="text" name="keyword" value="${param.keyword}" placeholder="검색어를 입력하세요" />
                <button type="submit">검색</button>
            </form>
        </div>

        <!-- 검색 결과 -->
        <div class="search-results">
            <h2>검색 결과</h2>

            <c:if test="${not empty keyword}">
                <p>검색어: ${keyword}</p>
            </c:if>

            <!-- 검색된 음식이 없을 경우 -->
            <c:if test="${count == 0}">
                <p>검색된 음식이 없습니다.</p>
            </c:if>

            <!-- 검색된 음식이 있을 경우 -->
            <c:if test="${not empty foodList}">
                <ul>
                    <c:forEach var="food" items="${foodList}">
                        <li>
                            <span>${food.foodName}</span>
                            <!-- 추가 버튼 수정: 디버깅을 위해 dietId 표시 -->
                            <button type="button" class="add-button"
                                data-food-id="${food.dietId}" data-food-name="${food.foodName}">
                                추가 (${food.dietId})
                            </button>
                        </li>
                    </c:forEach>
                </ul>

                <!-- 페이지 네비게이션 부분 -->
                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <a href="?keyword=${keyword}&pageNum=${currentPage - 1}">[이전]</a>
                    </c:if>

                    <c:forEach var="i" begin="1" end="${totalPage}" step="1">
                        <c:choose>
                            <c:when test="${i == currentPage}">
                                <span>${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="?keyword=${keyword}&pageNum=${i}">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage < totalPage}">
                        <a href="?keyword=${keyword}&pageNum=${currentPage + 1}">[다음]</a>
                    </c:if>
                </div>
            </c:if>

        </div>
    </div>

    <!-- 팝업 창 (Modal) -->
    <div id="food-modal" class="modal">
        <div class="modal-content">
            <span class="close-button" id="close-modal">&times;</span>
            <h2>식사 추가</h2>
            <form action="${pageContext.request.contextPath}/mydiet/insertMealLog.do" method="post" id="add-meal-form">
                <input type="hidden" id="food-id" name="dietId">
                <input type="hidden" id="food-name" name="foodName"> <!-- 추가된 히든 필드 -->
                <label for="meal-type">식사 유형:</label><br>
                <input type="radio" id="breakfast" name="mealType" value="아침" required>
                <label for="breakfast">아침</label><br>
                <input type="radio" id="lunch" name="mealType" value="점심" required>
                <label for="lunch">점심</label><br>
                <input type="radio" id="dinner" name="mealType" value="저녁" required>
                <label for="dinner">저녁</label><br><br>
                <button type="submit">추가</button>
            </form>
        </div>
    </div>

    <!-- 팝업을 열기 위한 스크립트 -->
    <script type="text/javascript">
    document.addEventListener('DOMContentLoaded', function () {
        // 추가 버튼 클릭 시 팝업 열기
        const addButtons = document.querySelectorAll('.add-button');
        addButtons.forEach(function(button) {
            button.addEventListener('click', function() {
                // 버튼에서 데이터 가져오기
                const foodId = button.getAttribute('data-food-id');
                const foodName = button.getAttribute('data-food-name');

                // 디버깅 로그 추가
                console.log("Food ID clicked:", foodId);
                console.log("Food Name clicked:", foodName);

                // 히든 필드에 값 설정
                const hiddenFoodId = document.getElementById('food-id');
                const hiddenFoodName = document.getElementById('food-name'); // 추가
                hiddenFoodId.value = foodId;
                hiddenFoodName.value = foodName; // 추가
                console.log("Food ID set to hidden input:", hiddenFoodId.value);
                console.log("Food Name set to hidden input:", hiddenFoodName.value); // 추가

                // 팝업 열기
                document.getElementById('food-modal').style.display = 'flex';
            });
        });

        // 팝업 닫기 버튼 클릭 시 닫기
        document.getElementById('close-modal').addEventListener('click', function() {
            document.getElementById('food-modal').style.display = 'none';
        });

        // 팝업 외부 클릭 시 닫기
        window.addEventListener('click', function(event) {
            const modal = document.getElementById('food-modal');
            if (event.target === modal) {
                modal.style.display = 'none';
            }
        });
    });
    </script>

    <!-- Footer Include (필요한 경우) -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
