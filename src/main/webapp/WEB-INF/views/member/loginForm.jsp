<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/loginForm.css" type="text/css">
    <script src="https://kit.fontawesome.com/8e490eaab5.js" crossorigin="anonymous"></script>
</head>
<body>
    <div class="page-main">
        <div class="login-title">
            <div class="navbar__logo">
                <i class="fa-solid fa-fire"></i>
                <a href="${pageContext.request.contextPath}/main/main.do" style="text-decoration: none; color: inherit;">Some Body Health Me</a>
            </div>
        </div>
        <form id="login_form" action="${pageContext.request.contextPath}/member/login.do" method="post">
            <input type="text" id="login_id" name="login_id" placeholder="아이디를 입력하세요" required>
            <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요" required>
            <input type="submit" value="로그인">
        </form>
        <div class="button-group">
            <a href="${pageContext.request.contextPath}/member/registerUserForm.do">회원가입</a>
            <a href="${pageContext.request.contextPath}/member/findAccountForm.do">계정 찾기</a>
            <a href="${pageContext.request.contextPath}/member/findPasswordForm.do">비밀번호 찾기</a>
        </div>
        <div class="error-message">
            <% 
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null) { 
                    out.print(errorMessage); 
                }
            %>
        </div>
    </div>
</body>
</html>