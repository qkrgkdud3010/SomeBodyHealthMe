<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>출입 내역 조회</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypageForm.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/mypageheader.jsp" />
<div class="mypage-container">
    <aside class="profile-sidebar">
        <div class="profile-main-box">
            <h3>출입 내역 조회</h3>
            <p>모든 회원에 대한 출입 내역을 확인</p>
        </div>
    </aside>
    <main class="content-section">
        <table class="membership-table">
            <thead>
                <tr>
                    <th>회원 이름</th>
                    <th>전화번호</th>
                    <th>출입 시간</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty entryLogs}">
                        <c:forEach var="entry" items="${entryLogs}">
                            <tr>
                                <td>${entry.name}</td>
                                <td>${entry.phone}</td>
                                <td><fmt:formatDate value="${entry.entryTime}" pattern="yy-MM-dd-HH-mm"/></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="3">출입 내역이 없습니다.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
       <div class="pagination" style="text-align: center;"> <!-- 강제 중앙 정렬 -->
    <c:if test="${page > 1}">
        <a href="${pageContext.request.contextPath}/member/entryLogsList.do?page=${page - 1}" class="btn">이전</a>
    </c:if>
    <c:forEach begin="1" end="${totalPages}" var="p">
        <a href="${pageContext.request.contextPath}/member/entryLogsList.do?page=${p}" 
           class="${p == page ? 'btn active' : 'btn'}">${p}</a>
    </c:forEach>
    <c:if test="${page < totalPages}">
        <a href="${pageContext.request.contextPath}/member/entryLogsList.do?page=${page + 1}" class="btn">다음</a>
    </c:if>
</div>
        <div class="profile-info-buttons" style="margin-top: 20px;">
            <button class="btn" onclick="location.href='${pageContext.request.contextPath}/member/adminPage.do'">관리자 페이지로 돌아가기</button>
        </div>
    </main>
</div>
</body>
</html>
