<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>출입 내역</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypageForm.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/mypageheader.jsp" />

<div class="mypage-container">
    <aside class="profile-sidebar">
        <div class="profile-main-box">
            <h3>출입 내역</h3>
            <p>내 출입 기록을 확인할 수 있습니다.</p>
        </div>
    </aside>
    <main class="content-section">
        <table class="entry-table">
            <thead>
                <tr>
                    <th>출입 시간</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty entryLogs}">
                        <c:forEach var="entry" items="${entryLogs}">
                            <tr>
                                <td>${entry.entryTime}</td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="1">출입 기록이 없습니다.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <a href="${pageContext.request.contextPath}/member/myEntryLogsList.do?page=${currentPage - 1}">이전</a>
            </c:if>
            <c:forEach begin="1" end="${totalPages}" var="p">
                <a href="${pageContext.request.contextPath}/member/myEntryLogsList.do?page=${p}" 
                   class="${p == currentPage ? 'active' : ''}">${p}</a>
            </c:forEach>
            <c:if test="${currentPage < totalPages}">
                <a href="${pageContext.request.contextPath}/member/myEntryLogsList.do?page=${currentPage + 1}">다음</a>
            </c:if>
        </div>

        <button onclick="location.href='${pageContext.request.contextPath}/member/myPage.do'" class="btn">마이페이지로 돌아가기</button>
    </main>
</div>
</body>
</html>
