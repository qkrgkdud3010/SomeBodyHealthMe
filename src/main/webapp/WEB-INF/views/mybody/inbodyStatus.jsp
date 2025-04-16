<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>    
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>인바디 상태</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <!-- Header 영역 -->
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    
    <!-- Sidebar 영역 -->
    <jsp:include page="/WEB-INF/views/common/aside_mybody.jsp" />

    <!-- Main Content -->
    <div class="page-main">
        <h2 style="text-align: center; padding-right:400px; padding-top:20px">월별 인바디 그래프</h2>

        <div id="chartContainer">
            <canvas id="inbodyChart"></canvas>
        </div>

        <script>
            // 데이터를 JSP에서 받아오기 (EL 표현식)
            var months = [];
            var avgMuscleMass = [];
            var avgBodyFatPercentage = [];

            // EL로 전달된 데이터를 자바스크립트 배열로 변환
            <c:forEach var="data" items="${inbodyData}">
                months.push('${data.month}');
                avgMuscleMass.push(${data.avgMuscleMass});
                avgBodyFatPercentage.push(${data.avgBodyFatPercentage});
            </c:forEach>

            // Chart.js로 그래프 그리기
            var ctx = document.getElementById('inbodyChart').getContext('2d');
            var inbodyChart = new Chart(ctx, {
                type: 'line',  // 라인 차트
                data: {
                    labels: months,  // x축: 월
                    datasets: [{
                        label: '평균 근육량',
                        data: avgMuscleMass,  // y축: 평균 근육량
                        borderColor: 'rgba(75, 192, 192, 1)',  // 라인 색
                        fill: false  // 채우지 않음
                    }, {
                        label: '평균 체지방률',
                        data: avgBodyFatPercentage,  // y축: 평균 체지방률
                        borderColor: 'rgba(255, 99, 132, 1)',  // 라인 색
                        fill: false  // 채우지 않음
                    }]
                },
                options: {
                    responsive: true,  // 반응형 설정
                    maintainAspectRatio: false,  // 비율을 자동으로 맞추도록 설정
                    scales: {
                        y: {
                            beginAtZero: true  // y축 0부터 시작
                        }
                    }
                }
            });
        </script>
        
        <!-- Action Buttons -->
        <div class="button-box">
            <c:if test="${not empty inbodyData}">
                <div id="modify-button" class="button">
                    <a href="${pageContext.request.contextPath}/mybody/inbodyStatusModifyForm.do">수정</a>
                </div>
            </c:if>
            <div id="insert-button" class="button">
                <a href="${pageContext.request.contextPath}/mybody/inbodyStatusInsertForm.do">등록</a>
            </div>
        </div>
    </div>

    <!-- Footer 영역 -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
