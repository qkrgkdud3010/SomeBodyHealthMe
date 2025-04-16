<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>매니저 페이지</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminpageForm.css" type="text/css">
    <script>
        function confirmDeactivation() {
            if (confirm('정말 탈퇴하시겠습니까?')) {
                location.href = '${pageContext.request.contextPath}/member/deactivateUser.do';
            }
        }
        function deletePhoto() {
            if (confirm('사진을 삭제하시겠습니까?')) {
                location.href = '${pageContext.request.contextPath}/member/deletePhoto.do';
            }
        }
    </script>
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/mypageheader.jsp" />

    <div class="mypage-container">
        <!-- 왼쪽 섹션 -->
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
                <h3>매니저 메뉴</h3>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/member/adminMembershipList.do">회원권 조회</a></li>
                </ul>
            </div>
        </aside>

        <!-- 오른쪽 콘텐츠 섹션 -->    
        <main class="content-section">
            <!-- 최근 게시글 -->
            <div class="recent-posts">
                <div class="section-header">
                    <h3>최근 게시글</h3>
                    <button class="more-btn" onclick="location.href='${pageContext.request.contextPath}/board/list.do'">더보기</button>
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>제목</th>
                            <th>작성시간</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty recentPosts}">
                                <c:forEach var="post" items="${recentPosts}">
                                    <tr>
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
                                    <td colspan="2">작성된 게시글이 없습니다.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>

            <!-- 최근 출입 내역 -->
            <div class="recent-entries">
                <div class="section-header">
                    <h3>회원 출입 내역</h3>
                    <button class="more-btn" onclick="location.href='${pageContext.request.contextPath}/member/entryLogsList.do'">더보기</button>
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>이름</th>
                            <th>전화번호</th>
                            <th>입장시간</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty recentEntries}">
                                <c:forEach var="entry" items="${recentEntries}">
                                    <tr>
                                        <td>${entry.name}</td>
                                        <td>${entry.phone}</td>
                                        <td><fmt:formatDate value="${entry.entryTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="3">최근 출입 내역이 없습니다.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>

            <!-- 최근 지원 신청 -->
            <div class="recent-applications">
                <div class="section-header">
                    <h3>최근 지원 신청</h3>
                    <button class="more-btn" onclick="location.href='${pageContext.request.contextPath}/appl/listByAdmin.do'">더보기</button>
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>지원번호</th>
                            <th>이름</th>
                            <th>지원분야</th>
                            <th>지원지점</th>
                            <th>등록일</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty recentApplications}">
                                <c:forEach var="appl" items="${recentApplications}">
                                    <tr>
                                        <td>${appl.appl_num}</td>
                                        <td>${appl.name}</td>
                                        <td>
                                            <c:if test="${appl.field == 2}">트레이너</c:if>
                                            <c:if test="${appl.field == 3}">사무직원</c:if>
                                        </td>
                                        <td>
                                            <c:if test="${appl.appl_center == 1}">강남점</c:if>
                                            <c:if test="${appl.appl_center == 2}">강북점</c:if>
                                        </td>
                                        <td>${appl.appl_regdate}</td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="5">최근 지원 신청이 없습니다.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>

            <!-- 회원 결제 내역 -->
            <div class="recent-payments">
                <div class="section-header">
                    <h3>회원 결제 내역</h3>
                    <button class="more-btn" onclick="location.href='${pageContext.request.contextPath}/order/adminList.do'">더보기</button>
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>결제번호</th>
                            <th>회원 이름</th>
                            <th>결제 금액</th>
                            <th>결제 시간</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty recentOrders}">
                                <c:forEach var="order" items="${recentOrders}">
                                    <tr>
                                        <td>${order.order_num}</td>
                                        <td>${order.goods_name}</td>
                                        <td><fmt:formatNumber value="${order.order_total}"/>원</td>
                                        <td><fmt:formatDate value="${order.reg_date}" pattern="yyyy-MM-dd HH:mm"/></td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="4">표시할 결제 내역이 없습니다.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>

            <!-- 식단 등록 요청 내역 -->
            <div class="recent-diet-requests">
                <div class="section-header">
                    <h3>식단 등록 요청 내역</h3>
                    <button class="more-btn" onclick="location.href='#'">더보기</button>
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>요청번호</th>
                            <th>회원 이름</th>
                            <th>요청 날짜</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td colspan="3">데이터가 없습니다.</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </main>
    </div>
</body>
</html>
