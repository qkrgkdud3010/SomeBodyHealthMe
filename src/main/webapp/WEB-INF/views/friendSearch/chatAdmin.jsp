<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/MS.css" type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		//웹소켓 생성
		const message_socket = 
			new WebSocket("ws://localhost:8088/someBodyHealthMe/webSocket");
		message_socket.onopen = function(evt){
			message_socket.send('one:');
		};
		//서버로부터 메시지를 받으면 호출되는 함수 지정
		message_socket.onmessage = function(evt){
			//메시지 알림
			let data = evt.data;
			if(data.substring(0,4) == 'one:'){
				selectData();
			}
		};
		message_socket.onclose = function(evt){
			//소켓이 종료된 후 부과적인 작업이 있을 경우 명시
			//console.log('chat close');
			//alert('채팅이 종료되었습니다.');
		};
		
		//엔터키 처리 이벤트 연결
		$('#message').keydown(function(event){
			if(event.keyCode == 13 && !event.shiftKey){
				$('#chatting_form').trigger('submit');
			}
		});
		
		function selectData(){
			//서버와 통신
			$.ajax({
				url:'chatMessageList.do',
				type:'post',
				data:{recv_num:$('#recv_num').val()},
				dataType:'json',
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인 후 사용하세요!');
						message_socket.close();
					}else if(param.result == 'success'){
						$('#chatting_message').empty();
		
						let message_date='';
						$(param.list).each(function(index,item){
							let output = '';
							//날짜 추출
							if(message_date != item.message_date.split(' ')[0]){
								message_date = item.message_date.split(' ')[0];
								output += '<div class="date-position"><span>'+message_date+'</span></div>';
							}
							
							if(item.sender_num == ${user_num}){
								output += '<div class="from-position">'+item.name;
							}else{
								output += '<div class="to-position">'+item.name;
								$('#recv_id').text(item.name);
							}
							
							output += '<div class="item">';
							output += (item.is_read !=0 ? '<b>①</b>' : '') + ' <span>' + item.message_text + '</span>';
							//시간 표시
							output += '<div class="align-right">' + item.message_date.split(' ')[1] + '</div>';
							output += '</div>';
							output += '</div>';
							
							//문서 객체에 추가
							$('#chatting_message').append(output);
							//스크롤을 하단으로 위치시킴
							$('#chatting_message').scrollTop($('#chatting_message')[0].scrollHeight);
						});
					}else{
						alert('채팅 읽기 오류 발생');
						message_socket.close();
					}
				},
				error:function(){
					alert('네트워크 오류 발생');
					message_socket.close();
				}
			});
		}
		
		
		 $('#user_list').on('click', '.user-item', function (event) {
		        event.preventDefault(); // 기본 링크 동작 막기

		        // 클릭한 사용자 recv_num 가져오기
		        let newRecvNum = $(this).data('recv-num');
		        $('#recv_num').val(newRecvNum); // 숨겨진 input 값 변경
		
		        // 선택된 사용자 하이라이트 표시
		        $('.user-item').removeClass('active');
		        $(this).addClass('active');

		        // 새 데이터 로드
		        selectData();
		    });
		//채팅 등록
		$('#chatting_form').submit(function(event){
			if($('#message').val().trim()==''){
				alert('내용을 입력하세요!');
				$('#message').val('').focus();
				return false;
			}
			//form 이하의 태그에 입력한 데이터를 모두 읽어옴
			let form_data = $(this).serialize();
			//서버와 통신
			$.ajax({
				url:'writeChatOne.do',
				type:'post',
				data:form_data,
				dataType:'json',
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인해야 작성할 수 있습니다.');
					}else if(param.result == 'success'){
						//폼 초기화
						$('#message').val('').focus();
						selectData();
						message_socket.send('one:');
					}else{
						alert('채팅 등록 오류 발생');
					}
				},
				error:function(){
					alert('친구를 클릭해주세요');
				}
			});
			//기본 이벤트 제거
			event.preventDefault();
		});
		//초기 데이터 호출
		selectData();		
	});
	
	
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<jsp:include page="/WEB-INF/views/common/asideFriend.jsp" />
	<div class="main-wrap">
	<div id="user_list">
		<div id="chat1">
		

			<div class="search-box">
				<img src="${pageContext.request.contextPath}/images/Search.png"
					style="float: left;">

				<form action="">

					<input type="text"
						style="float: left; margin-left: 23px; height: 42px; width: 220px;">
				</form>

			</div>
			
			<div style="width:300px; height:150px;">
				
					<div class="user-profile"></div>
	
					<div class="name-content"><a  href="#" class="user-item" data-recv-num="35">운영자</a></div>

			</div>
	
		</div>
	</div>
	<div id="chatting_message"></div>

	<div class="chat3">
		<form action="" id="chatting_form">
  <input type="hidden" id="recv_num" name="recv_num"  />
        <textarea id="message" name="message" style="width: 600px; height: 80px;"></textarea>
		
			 <button type="submit" style="height:80px; width:80px;"><img alt="" src="${pageContext.request.contextPath}/images/Send.png"
			width="48px" height="48px"></button>
		
		</form>



		



        
	</div>
	
			
	</div>

	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	<script type="text/javascript">

	</script>
</body>
</html>