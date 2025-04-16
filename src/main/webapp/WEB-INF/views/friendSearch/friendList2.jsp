<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>친구 요청</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/MS.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
    <script>
        $(document).ready(function() {
            $('.sendFriendRequestButton').click(function() {
                const receiverNum = $(this).data('receiver-num'); // 버튼에서 receiverNum 값 가져오기

                // AJAX 요청 보내기
                $.ajax({
                    type: 'POST',
                    url: 'sendFriendRequest.do', 
                    dataType: 'json', // 서버에서 요청을 처리할 URL
                    data: { receiverNum: receiverNum },  // 보내는 데이터
                    success: function(response) {
                        if (response.isRequestSent == 'success') {
                            alert("친구 요청이 성공적으로 전송되었습니다!");
                        } else if (response.isRequestSent == 'duple') {
                            alert("이미 친구입니다.");
                        } else {
                            alert("실패!");
                        }
                    },
                    error: function() {
                        alert("서버와의 통신 오류가 발생했습니다.");
                    }
                });
            });

            $('.sendFriendRequestButton2').click(function() {
                const receiverNum = $(this).data('receiver-num'); // 버튼에서 receiverNum 값 가져오기

                // AJAX 요청 보내기
                $.ajax({
                    type: 'POST',
                    url: 'sendFriendRequest2.do', 
                    dataType: 'json', // 서버에서 요청을 처리할 URL
                    data: { receiverNum: receiverNum },  // 보내는 데이터
                    success: function(response) {
                        if (response.isRequestSent == 'success') {
                            alert("친구 요청 취소가 완료되었습니다!");
                        } else if (response.isRequestSent == 'duple') {
                            alert("이미 친구입니다.");
                        } else {
                            alert("실패!");
                        }
                    },
                    error: function() {
                        alert("서버와의 통신 오류가 발생했습니다.");
                    }
                });
            });
        });

        function refreshPage() {
            location.reload(); // 페이지 새로고침
        }
    </script>

    <style>
        body {
            font-family: Arial, sans-serif;
   
            margin: 0;
            padding: 0;
        }

        .main-wrap {
            width: 80%;
            margin: 30px auto;
  
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .main-wrap h1 {
            text-align: center;
            color: #333;
            font-size: 28px;
        }

        .link {
            margin: 0 15px;
            text-decoration: none;
            color: #333;
            font-size: 18px;
        }

        .link:hover {
            color: #4CAF50;
        }

        .search-form {
            margin-top: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
        }

        .search-form select, .search-form input {
            padding: 8px;
            font-size: 16px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        .search-form input[type="submit"] {
            background-color: rgba(0, 0, 0, .66);
            color: white;
            border: none;
            cursor: pointer;
            font-weight: bold;
        }

        .search-form input[type="submit"]:hover {
            background-color: rgba(0, 0, 0, .66);
        }

        .list-space {
            display: flex;
            justify-content: flex-end;
            margin-top: 20px;
        }

        .list-space input {
            background-color: rgba(0, 0, 0, .66);
            color: white;
            padding: 10px 20px;
            border: none;
            cursor: pointer;
            font-size: 16px;
            margin-left: 10px;
            border-radius: 5px;
        }

        .list-space input:hover {
            background-color: rgba(0, 0, 0, .66);
        }

        .friend-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 30px;
        }

        .friend-table th, .friend-table td {
            padding: 12px;
            text-align: center;
            border: 1px solid #ddd;
        }

        .friend-table th {
            background-color: #f2f2f2;
            color: #333;
        }

        .friend-table td a {
            color: white;
            background-color: rgba(0, 0, 0, .66);
            padding: 5px 10px;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .friend-table td a:hover {
            background-color: rgba(0, 0, 0, .66);
        }

        .friend-table td span {
            font-size: 16px;
            color: #4CAF50;
        }

        .active-link {
            color: red;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <jsp:include page="/WEB-INF/views/common/asideFriend.jsp" />
    
    <div class="main-wrap">
        <a href="${pageContext.request.contextPath}/friendSearch/friendList.do" class="link">전체 보기</a>
        <a href="${pageContext.request.contextPath}/friendSearch/friendList2.do?center_num=1" class="link" id="center1">강남지점 회원 목록</a>
        <a href="${pageContext.request.contextPath}/friendSearch/friendList2.do?center_num=2" class="link" id="center2">강북지점 회원 목록</a>

        <form id="search_form" action="friendList.do" method="get" class="search-form">
            <select name="keyfield">
                <option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>아이디</option>
                <option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>닉네임</option>
                <option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>소개</option>
            </select>
            <input type="search" name="keyword" id="keyword" value="${param.keyword}">
            <input type="submit" value="찾기">
        </form>

        <div class="list-space">
            <input type="button" value="목록" onclick="location.href='friendList.do'">
            <input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
        </div>

        <table class="friend-table">
            <thead>
                <tr>
                    <th>이름</th>
                    <th>닉네임</th>
                    <th>번호</th>
                    <th>상태</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="friend" items="${list}">
                    <tr>
                        <td>${friend.name}</td>
                        <td>${friend.nick_name}</td>
                        <c:if test="${friend.center_Num=='1'}">
                            <td>강남점</td>
                        </c:if>
                        <c:if test="${friend.center_Num=='2'}">
                            <td>강북점</td>
                        </c:if>
                        <td>
                            <c:if test="${friend.status=='None'}">
                                <a href="javascript:void(0);" class="sendFriendRequestButton" data-receiver-num="${friend.user_Num}" onclick="refreshPage()">친구 요청</a>
                            </c:if>
                            <c:if test="${friend.status=='1'}">
                                <a href="javascript:void(0);" class="sendFriendRequestButton2" data-receiver-num="${friend.user_Num}" onclick="refreshPage()">친구 요청 취소</a>
                            </c:if>
                            <c:if test="${friend.status=='2'}">
                                <span>친구입니다</span>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script>
        window.onload = function() {
            const urlParams = new URLSearchParams(window.location.search);
            const centerNum = urlParams.get('center_num');

            const links = document.querySelectorAll('.link');
            links.forEach(link => {
                link.classList.remove('active-link');
            });

            if (centerNum === '1') {
                document.getElementById('center1').classList.add('active-link');
            } else if (centerNum === '2') {
                document.getElementById('center2').classList.add('active-link');
            }
        };
    </script>
</body>
</html>
