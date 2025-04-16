<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원정보 수정</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypageForm.css">
    <script>
        function validateForm() {
            const nickname = document.querySelector('[name="nick_name"]').value.trim();
            const email = document.querySelector('[name="email"]').value.trim();
            const phone = document.querySelector('[name="phone"]').value.trim();
            if (!nickname && !email && !phone) {
                alert('적어도 하나의 정보를 입력해야 수정 가능합니다.');
                return false;
            }
            return true;
        }

        // 취소 버튼 클릭 시 이전 페이지로 이동
        function goBack() {
            history.back();
        }
    </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/mypageheader.jsp" />
<div class="mypage-container">
    <aside class="profile-sidebar">
        <div class="profile-main-box">
            <h3>회원정보 수정</h3>
            <p>수정하려는 정보를 입력 후 저장</p>
        </div>
    </aside>
    <main class="content-section">
        <form class="profile-details-box" action="${pageContext.request.contextPath}/member/updateProfile.do" method="post" onsubmit="return validateForm();">
            <ul>
                <li>
                    <label for="nick_name">닉네임</label>
                    <input type="text" id="nick_name" name="nick_name" value="${member.nick_name}" placeholder="변경할 닉네임 입력">
                </li>
                <li>
                    <label for="email">이메일</label>
                    <input type="email" id="email" name="email" value="${member.email}" placeholder="변경할 이메일 입력">
                </li>
                <li>
                    <label for="phone">전화번호</label>
                    <input type="text" id="phone" name="phone" value="${member.phone}" placeholder="변경할 전화번호 입력">
                </li>
                <li>
                    <label for="birth_date">생년월일</label>
                    <input type="text" id="birth_date" name="birth_date" value="${member.birth_date}" placeholder="생년월일 (YYYYMMDD)">
                </li>
            </ul>
            <div class="profile-info-buttons">
                <button type="submit" class="btn">수정</button>
                <button type="button" class="btn" onclick="goBack()">취소</button>
            </div>
        </form>
    </main>
</div>
</body>
</html>
