<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원권 조회</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypageForm.css">
    <style>
        .pagination {
            text-align: center; /* 중앙 정렬 */
            margin-top: 20px;
        }

        .pagination .btn {
            display: inline-block;
            padding: 8px 12px;
            margin: 0 5px;
            border: 1px solid #ddd;
            border-radius: 4px;
            text-decoration: none;
            color: black;
            font-size: 14px;
        }

        .pagination .btn.active {
            background-color: #d48566;
            color: white;
            font-weight: bold;
        }

        .pagination .btn:hover {
            background-color: #f0f0f0;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/mypageheader.jsp" />
<div class="mypage-container">
    <aside class="profile-sidebar">
        <div class="profile-main-box">
            <h3>회원권 조회</h3>
            <p>현재 등록된 회원권 목록을 확인할 수 있습니다.</p>
        </div>
    </aside>
    <main class="content-section">
        <table class="membership-table">
            <thead>
                <tr>
                    <th>회원 이름</th>
                    <th>회원 번호</th>
                    <th>회원권 타입</th>
                    <th>구매 날짜</th>
                    <th>지속 기간 (개월)</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty memberships}">
                        <c:forEach var="membership" items="${memberships}">
                            <tr>
                                <td>${membership.user_name}</td>
                                <td>${membership.user_num}</td>
                                <td>${membership.mem_type}</td>
                                <td>${membership.mem_startdate}</td>
                                <td>${membership.duration_months}</td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="5">등록된 회원권이 없습니다.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
        <div class="pagination">
            <c:if test="${page > 1}">
                <a href="${pageContext.request.contextPath}/member/adminMembershipList.do?page=${page - 1}" class="btn">이전</a>
            </c:if>
            <c:forEach begin="1" end="${totalPages}" var="p">
                <a href="${pageContext.request.contextPath}/member/adminMembershipList.do?page=${p}" 
                   class="${p == page ? 'btn active' : 'btn'}">${p}</a>
            </c:forEach>
            <c:if test="${page < totalPages}">
                <a href="${pageContext.request.contextPath}/member/adminMembershipList.do?page=${page + 1}" class="btn">다음</a>
            </c:if>
        </div>
        <div class="profile-info-buttons" style="margin-top: 20px;">
            <button class="btn" onclick="location.href='${pageContext.request.contextPath}/member/adminPage.do'">관리자 페이지로 돌아가기</button>
        </div>
    </main>
</div>
</body>
</html>
