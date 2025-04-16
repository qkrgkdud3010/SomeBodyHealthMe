<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비밀번호 찾기</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/findpasswordForm.css" type="text/css">
    <script src="https://kit.fontawesome.com/8e490eaab5.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="page-main">
    <!-- 로고와 타이틀 -->
    <div class="login-title">
        <div class="navbar__logo">
            <i class="fa-solid fa-fire"></i>
            <a href="${pageContext.request.contextPath}/main/main.do" style="text-decoration: none; color: inherit;">Some Body Health Me</a>
        </div>
    </div>
    <div class="content-main">
        <form id="password_find_form" action="findPassword.do" method="post">
            <ul>
                <li>
                    <label for="login_id">아이디</label>
                    <input type="text" name="login_id" id="login_id" required>
                </li>
                <li>
                    <label for="email">이메일</label>
                    <input type="email" name="email" id="email" required>
                </li>
            </ul>
            <div class="align-center buttons-row">
                <input type="submit" value="비밀번호 찾기">
            </div>
            <div class="align-center links-row">
                <button type="button" onclick="location.href='${pageContext.request.contextPath}/member/registerUserForm.do'">회원가입</button>
                <button type="button" onclick="location.href='${pageContext.request.contextPath}/member/loginForm.do'">로그인</button>
                <button type="button" onclick="location.href='${pageContext.request.contextPath}/member/findAccountForm.do'">아이디 찾기</button>
                <button type="button" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">홈으로</button>
            </div>
            <% if (request.getAttribute("foundPassword") != null) { %>
                <script>alert("찾으신 비밀번호는 '<%= request.getAttribute("foundPassword") %>' 입니다.");</script>
            <% } else if (request.getAttribute("errorMessage") != null) { %>
                <div class="error-message"><%= request.getAttribute("errorMessage") %></div>
            <% } %>
        </form>
    </div>
</div>
</body>
</html>