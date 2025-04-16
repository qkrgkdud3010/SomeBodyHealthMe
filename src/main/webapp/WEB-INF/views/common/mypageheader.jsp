<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MYPAGE Header</title>
    <script src="https://kit.fontawesome.com/8e490eaab5.js" crossorigin="anonymous"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
	href="https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gowun+Dodum&family=Source+Sans+3:ital,wght@0,200..900;1,200..900&display=swap"
	rel="stylesheet">
<body>
    <div class="navbar">
        <div class="navbar__logo">
            <i class="fa-solid fa-fire"></i>
            <a href="${pageContext.request.contextPath}/main/main.do" class="logo-link">Some Body Health Me</a>
        </div>
        <ul class="navbar__menu">
            <li><a href="${pageContext.request.contextPath}/board/list.do">소통공간</a></li>
            <li><a href="${pageContext.request.contextPath}/goods/list.do">헬스용품</a></li>
            <c:if test="${!empty user_num}">
            </c:if>
            <li><a href="${pageContext.request.contextPath}/friendSearch/friendList.do">친구만들기</a></li>
            <li><a href="${pageContext.request.contextPath}/mybody/myStatus.do">건강지킴이</a></li>
            <li><a href="${pageContext.request.contextPath}/membership/membershipBuy.do">회원권</a></li>
        </ul>

        <ul class="navbar__button">
            <c:choose>
                <c:when test="${sessionScope.status == 1 || sessionScope.status == 2 || sessionScope.status == 3 || sessionScope.status == 4}">
                    <li class="hidden-button"><div class="button"><a href="${pageContext.request.contextPath}/member/myPage.do">MYPAGE</a></div></li>
                    <li class="hidden-button"><div class="button"><a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a></div></li>
                </c:when>
                <c:otherwise>
                    <li><div class="button"><a href="${pageContext.request.contextPath}/member/registerUserForm.do">회원가입</a></div></li>
                    <li><div class="button"><a href="${pageContext.request.contextPath}/member/loginForm.do">로그인</a></div></li>
                </c:otherwise>
            </c:choose>
        </ul>

        <!-- Toggle button  ㄴ-->
        <a href="#" class="navbar__toggleBtn">
            <i class="fa-solid fa-bars"></i>
        </a>
    </div>
</body>
<script type="text/javascript" defer>
    // 모바일 토글 키
    const toggleBtn = document.querySelector('.navbar__toggleBtn');
    const menu = document.querySelector('.navbar__menu');

    toggleBtn.addEventListener('click', () => {
        menu.classList.toggle('active');
    });
</script>
</html>