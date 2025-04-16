<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SBHM 소통공간</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/board_list.css" type="text/css">
    <script type="text/javascript">
    	window.onload=function(){
    		const search_form = document.getElementById('search_form');
    		
    		search_form.onsubmit=function(){
    			const search_input = document.getElementById('search_input');
    			if(search_input.value.trim()==''){//빈문자 검색
    				alert('검색어를 입력하세요!');
    				search_input.value = '';
    				search_input.focus();
    				return false;
    			}    			
    		};    		
    	};
    </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/aside_board.jsp"/>
    <div class="container">
        <h2>
        <span>
        <c:if test="${cate==1}">공지사항</c:if>
        <c:if test="${cate==2}">자유게시판</c:if>
        <c:if test="${cate==3}">오늘 운동 완료</c:if>
        <c:if test="${empty cate}">소통공간</c:if>
        </span>
        </h2>
        <div class="content-main">   
        	<div class="border-table">
            <table>
                <tr style="background-color: #d9d9d9; border-bottom: 2px solid black;">
                    <th width="60">글번호</th>
                    <th width="60">구분</th>
                    <th width="660">제목</th>
                    <th width="100">작성자</th>
                    <th width="80">작성일</th>
                    <th width="60">조회수</th>
                </tr>    
                
                <c:forEach var="board" items="${list}">
                <tr <c:if test="${board.board_category == 1 }">class="board_notice"</c:if>>
                    <td>${board.board_num}</td>
                    <td>
						<c:if test="${board.board_category == 1 }">공지</c:if>
						<c:if test="${board.board_category == 2 }">자유</c:if>
						<c:if test="${board.board_category == 3 }">오운완</c:if>
                    </td>
                    <td>
                    <c:if test="${cate == null }">
                    <a href="detail.do?board_num=${board.board_num}" <c:if test="${board.board_category == 1 }">class="board_notice"</c:if>>${board.board_title}</a> <span style="font-size:15px;">[${board.recount}]</span>
                    </c:if>
                    <c:if test="${cate != null }">
                    <a href="detail.do?board_category=${cate}&board_num=${board.board_num}" <c:if test="${board.board_category == 1 }">class="board_notice"</c:if>>${board.board_title}</a> <span style="font-size:15px;">[${board.recount}]</span>
                    </c:if> 
                     
                    </td>
                    <td>
                    <c:if test="${empty board.nick_name}">${board.login_id}</c:if>
                    <c:if test="${!empty board.nick_name}">${board.nick_name}</c:if>                                        
                    </td>
                    <td>${board.board_regdate}</td>
                    <td>${board.board_count}</td>
                </tr>
                </c:forEach>
            </table>  
            </div>
            <c:if test="${count==0}">
            	<div class="count-0">표시할 게시물이 없습니다.</div> 		
            </c:if>          
            
            <hr size="3" noshade="noshade" width="1020px">
            <div style="text-align:center;">
            	${page}
            </div>
            
            
            <!-- 글 등록 버튼 -->
            <div class="write-btn-container">
            <c:if test="${!empty user_num && cate != 1 || status == 4}">
            	<input type="button" value="글쓰기" id="write_btn" onclick="location.href='writeForm.do<c:if test="${!empty cate}">?board_category=${cate}</c:if>'">
            </c:if>                
            </div>
            <!-- 검색바 -->
            <form action="list.do" method="get" class="search-bar" id="search_form">
            	<input type="hidden" name="board_category" value="${cate}">
            	<div id="search-bar">
              	  <select name="keyfield">
               	     <option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
               	     <option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>내용</option>
                     <option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>닉네임</option>
             	   </select>
              	   <input type="search" placeholder="검색어 입력" id="search_input" name="keyword">
             	   <input type="submit" value="검색">
            	</div>
            </form>
        </div>           
           
    </div>
</div>    
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>