<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>마이 페이지</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypageForm.css" type="text/css">
    <script>
        // 회원 탈퇴 확인
        function confirmDeactivation() {
            if (confirm('정말 탈퇴하시겠습니까?')) {
                location.href = '${pageContext.request.contextPath}/member/deactivateUser.do';
            }
        }

        // 사진 삭제 확인
        function deletePhoto() {
            if (confirm('사진을 삭제하시겠습니까?')) {
                location.href = '${pageContext.request.contextPath}/member/deletePhoto.do';
            }
        }
    </script>
</head>
<body>
    <!-- 헤더 -->
    <jsp:include page="/WEB-INF/views/common/mypageheader.jsp" />

    <!-- 마이페이지 메인 컨테이너 -->
    <div class="mypage-container">
        <aside class="profile-sidebar">
            <div class="profile-photo">
                <img src="<c:choose>
                    <c:when test='${empty member.photo}'>
                        ${pageContext.request.contextPath}/images/default_user.png
                    </c:when>
                    <c:otherwise>
                        ${pageContext.request.contextPath}/upload/${member.photo}
                    </c:otherwise>
                </c:choose>" alt="프로필 사진">
                <div class="photo-buttons">
                    <form action="${pageContext.request.contextPath}/member/uploadPhoto.do" method="post" enctype="multipart/form-data" style="display: flex; align-items: center; gap: 10px;">
                        <label for="photo" class="styled-file-label">파일 선택</label>
                        <input type="file" id="photo" name="photo" accept="image/*" class="styled-file-input" required>
                        <button type="submit" class="btn photo-edit-btn">수정</button>
                        <button type="button" class="btn photo-delete-btn" onclick="deletePhoto()">삭제</button>
                    </form>
                </div>
                <div class="name-email">
                    <p>${member.name}</p>
                    <p>${member.email}</p>
                </div>
            </div>

            <div class="profile-info-buttons">
                <button class="logout-btn" onclick="location.href='${pageContext.request.contextPath}/member/logout.do'">로그아웃</button>
                <button class="deactivate-btn" onclick="confirmDeactivation()">회원탈퇴</button>
            </div>

            <div class="profile-main-box">
                <div class="profile-header">
                    <h3>내 프로필</h3>
                    <button onclick="location.href='${pageContext.request.contextPath}/member/editProfileForm.do'" class="btn edit-profile-btn">정보수정</button>
                </div>
                <ul class="profile-details-box">
                    <li>닉네임: ${member.nick_name}</li>
                    <li>전화번호: ${member.phone}</li>
                </ul>
            </div>

            <div class="menu-section">
                <h3>회원권 관련 내역</h3>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/member/myMembershipList.do">회원권 조회</a></li>
                    <li><strong>남은 구독 기간:</strong> ${remainingDays}일</li>
                </ul>
                <h3>쇼핑 관련 내역</h3>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/cart/list.do">장바구니</a></li>
                    <li><a href="${pageContext.request.contextPath}/order/orderList.do">구매내역 확인</a></li>
                </ul>
            </div>
        </aside>

        <main class="content-section">
            <div class="my-posts">
                <h3>내가 쓴 글 
                    <button class="more-btn" 
                            onclick="location.href='${pageContext.request.contextPath}/board/list.do?keyfield=3&keyword=${sessionScope.nick_name}'">
                        더보기
                    </button>
                </h3>
                <table>
                    <thead>
                        <tr>
                            <th>글번호</th>
                            <th>제목</th>
                            <th>작성일</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty sessionScope.recentPosts}">
                                <c:forEach var="post" items="${sessionScope.recentPosts}">
                                    <tr>
                                        <td>${post.board_num}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/board/detail.do?board_num=${post.board_num}">
                                                ${post.board_title}
                                            </a>
                                        </td>
                                        <td>${post.board_regdate}</td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="3">작성한 글이 없습니다.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>

            <div class="my-entry-logs">
               <h3>출입 내역 
    <button class="more-btn" onclick="location.href='${pageContext.request.contextPath}/entry/myEntryLogsList.do'">더보기</button>
</h3>
<table>
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
                        <td>
                            <fmt:formatDate value="${entry.entryTime}" pattern="yyyy-MM-dd HH:mm"/>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="1">출입 내역이 없습니다.</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </tbody>
</table>
            </div>
        </main>
    </div>
</body>
</html>