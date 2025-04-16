<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>친구 요청</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/MS.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $('.sulag').click(function() {
            const receiverNum = $(this).data('receiver-num'); // 버튼에서 receiverNum 값 가져오기

            // AJAX 요청 보내기
            $.ajax({
                type: 'POST',
                url: 'sulag.do', 
                dataType: 'json', // 서버에서 요청을 처리할 URL
                data: { receiverNum: receiverNum },  // 보내는 데이터
                success: function(response) {
                    // 서버에서 보내는 응답 처리
                    if (response.isRequestSent == 'success') {
                        alert("친구가 되었습니다! 대화를 해보세요!");
                    } else if (response.isRequestSent == 'fail') {
                        alert("실패");
                    } else {
                        alert("오류 발생!");
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

        color: white;
        margin: 0;
        padding: 0;
    }

    .main-wrap {
        width: 80%;
        margin: 30px auto;

        padding: 30px;
        border-radius: 8px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    }

    .all2 p {
        font-size: 24px;
        font-weight: bold;
        color: #333;
    }

    .search {
        display: flex;
        justify-content: space-between;
        margin-top: 20px;
    }

    .search select,
    .search input[type="search"] {
        padding: 8px;
        font-size: 16px;
        border: 1px solid #ddd;
        border-radius: 5px;
    }

    .search input[type="submit"] {
        padding: 10px 20px;
        background-color: rgba(0, 0, 0, .66);
        color: white;
        border: none;
        cursor: pointer;
        font-weight: bold;
        border-radius: 5px;
    }

    .search input[type="submit"]:hover {
        background-color:  rgba(0, 0, 0, .66);
    }

    .list-space {
        display: flex;
        justify-content: flex-end;
        margin-top: 20px;
    }

    .list-space input {
        padding: 10px 20px;
        background-color:  rgba(0, 0, 0, .66);
        color: white;
        border: none;
        cursor: pointer;
        font-size: 16px;
        border-radius: 5px;
        margin-left: 10px;
    }

    .list-space input:hover {
        background-color:  rgba(0, 0, 0, .66);
    }

    .friend {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
    }

    .friend th, .friend td {
        padding: 12px;
        text-align: center;
        border: 1px solid #ddd;
    }

    .friend th {
        background-color: #333;
        color: white;
    }

    .friend td a {
        padding: 8px 15px;
        background-color:  rgba(0, 0, 0, .66);
        color: white;
        text-decoration: none;
        border-radius: 5px;
        transition: background-color 0.3s;
    }

    .friend td a:hover {
        background-color: #45a049;
    }

    .friend td span {
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
        <div class="all2">
            <p>친구 요청한 친구 리스트</p>
        </div>
        <div style="clear: both;"></div>

        <form id="search_form" action="friendList.do" method="get">
            <ul class="search">
                <li>
                    <select name="keyfield" class="sel">
                        <option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>이름</option>
                        <option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>닉네임</option>
                    </select>
                </li>
                <li>
                    <input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
                </li>
                <li>
                    <input type="submit" value="찾기" style="width: 300px; border: 1px solid black;">
                </li>
            </ul>
        </form>

        <div class="list-space align-right">
            <input type="button" value="목록" onclick="location.href='friendList.do'" style="width: 150px;">
            <input type="button" value="홈으로" style="width: 150px;" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
        </div>

        <table class="friend">
            <tr>
                <th>이름</th>
                <th>닉네임</th>
                <th>번호</th>
            </tr>
            <c:forEach var="friend" items="${list}">
                <tr>
                    <td style="color:black;">${friend.name}</td>
                    <td style="color:black;">${friend.nick_name}</td>

                    <c:if test="${friend.status=='1'}">
                        <td>
                            <a href="javascript:void(0);" class="sulag" data-receiver-num="${friend.user_Num}" onclick="refreshPage()">친구 수락</a>
                        </td>
                    </c:if>

                    <c:if test="${friend.status=='2'}">
                        <td>이미 친구</td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </div>

    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>
