<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
    .sidebar {
    	text-align: center;
    	font-family: Arial, sans-serif;          
      color: #000000a8;
      width: 295px;
      background-color: white;
      height: 450px;
      border: 1px solid black;
      margin : 20px 30px 0 30px; 
    }

    .aside-title {
      text-align: center;
      font-size: 32px;
      margin-top: 20px;
      margin-bottom:10px;
      display: flex;
      justify-content: center; /* 수평 중앙 정렬 */
      align-items: center;
      height: 55px;  
      padding-bottom: 10px;
      color: rgb(94, 91, 91);    
      }
      
    .aside-title img{
        margin-right: 10px;
    }


    ul {
      list-style: none;
      padding: 0;
      font-size: 20px;
    }

    .menu-title {
      cursor: pointer;
      background-color: #f0f0f0;
      transition: background-color 0.3s;
      width: 100%;
      box-sizing: border-box;
      height: 40px;
      font-size: 24px;
      display: flex;             /* Flexbox 활성화 */
      justify-content: space-between; /* 왼쪽과 오른쪽 끝에 배치 */
      align-items: center;
      padding: 0 10px;
    }
    .menu-title p{
      margin: 0px;
      font-size: 24px;
      color: #000000a8;
    }

    .submenu {
      display: none;
      padding: 0;
      width: 100%;
      box-sizing: border-box;
    }

    .submenu li {
      padding: 12px 18px;
      border-bottom: 1px solid white;
      transition: background-color 0.3s;
      width: 100%;
      height:50px;
      box-sizing: border-box;
      text-align: left;
      font-size: 18px;
      background-color: #f0f0f0;
    }
    a{
      text-decoration: none;
      cursor: pointer;
    }

    .submenu li:hover {
      background-color: #d0d0d0;
    }
</style>
</head>

	<aside class="sidebar">
   <div class="aside-title"><img src="${pageContext.request.contextPath}/images/image 1.png"  width="45" height="55"><p>커뮤니티</p></div>
    <ul>
      <li class="menu">
        <div class="menu-title" onclick="toggleMenu('social')"><p>소통공간</p><img src="${pageContext.request.contextPath}/images/free-icon-down-2732661 2.png" width="35" height="25"></div>
        <ul class="submenu" id="social">
          <li><a href="${pageContext.request.contextPath}/board/list.do">전체글보기</a></li>
                  <li><a href="${pageContext.request.contextPath}/board/list.do?board_category=1">공지사항</a></li>
                  <li><a href="${pageContext.request.contextPath}/board/list.do?board_category=2">자유 게시판</a></li>
                  <li><a href="${pageContext.request.contextPath}/board/list.do?board_category=3">오늘 운동 완료</a></li>     
        </ul>        
      </li>
      <li class="menu">
        <div class="menu-title" onclick="toggleMenu('support')"><p>지원하기</p><img src="${pageContext.request.contextPath}/images/free-icon-down-2732661 2.png" alt="" width="35" height="25"></div>
        <ul class="submenu" id="support">
          
          <c:if test="${status == 1 || empty status}">
          <li><a href="${pageContext.request.contextPath}/appl/writeForm.do">지원신청</a></li>
          </c:if>
          
          <c:if test="${!empty user_num && status <= 3}">
          <li><a href="${pageContext.request.contextPath}/appl/listByUser.do">내 지원 목록</a></li>
          </c:if>
          
          <c:if test="${!empty user_num && status == 4}">
          <li><a href="${pageContext.request.contextPath}/appl/listByAdmin.do">지원 목록 보기</a></li>
          </c:if>
        </ul>
      </li>
      
    </ul>
  </aside>

  <script>
  function toggleMenu(menuId) {
    // 클릭된 메뉴의 서브 메뉴 찾기
    const submenu = document.getElementById(menuId);
    const icon = submenu.previousElementSibling.querySelector('img');

    // 서브 메뉴가 보이는 상태라면 숨기고, 아니면 보이도록 처리
    if (submenu.style.display === "block") {
      submenu.style.display = "none";  // 서브 메뉴 숨기기
      icon.src = "${pageContext.request.contextPath}/images/free-icon-down-2732661 2.png";  // 아래 화살표로 변경
    } else {
      submenu.style.display = "block";  // 서브 메뉴 보이게 하기
      icon.src = "${pageContext.request.contextPath}/images/free-icon-down-2732661%201.png";  // 위 화살표로 변경
    }
  }
</script>
  
	

</html>