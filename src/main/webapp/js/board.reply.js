$(function(){
	let rowCount = 5;
	let currentPage;
	let count;
	/*================ 
	 * 댓글 목록
	 *================*/
	//댓글 목록 
	function selectList(pageNum){
		currentPage = pageNum;
		
		//로딩 이미지 노출
		$('#loading').show();
		
		//서버와 통신
		$.ajax({
			url:'listReply.do', 
			type:'post',
			data:{pageNum:pageNum,rowCount:rowCount,board_num:$('#board_num').val()},
			dataType:'json',
			success:function(param){
				//로딩 이미지 숨김
				$('#loading').hide();
				count = param.count;
				
				if(pageNum==1){
					//처음 호출 시 댓글 이전 목록 제거
					$('#output').empty();
				}
				
				$('#reply_count').text(count);				
				
				
				if(param.count == 0){
					let output = '<div class="re-none"> 등록된 댓글이 없습니다.</div>';
					$('#output').append(output);
				}
				
								
				
				$(param.list).each(function(index,item){
					let photo;
					if(item.photo != null) photo = item.photo;
					else photo = 'User.png'; 
					let output =  '<div class="item">';
					output += '<div class="sub-item">';
					output += '<div class="re-profile">';
					output += '<img src="../upload/' + photo + '" width="50" height="50">';
					
					output += '<div class="list-nick_name">' + item.nick_name +'</div>';
					output += '</div>';
					output += '<p>' + item.re_content +'</p>' ;
					output += '<div class="re-date">';
							
					if(item.re_modifydate){
						output += '<span class="modify-date" style="font-size: 14px;">수정 : ' + item.re_modifydate + '</span>';
					}else{
						output += '<span class="re-regdate" style="font-size: 14px;">등록 : ' + item.re_regdate + '</span>';
					}
					
					output +='<div style="margin-bottom:5px">';	
					//로그인한 회원번호와 작성자의 회원번호 일치 여부 체크
					if(param.user_num == item.user_num){						
					//로그인한 회원번호와 작성자 회원번호 일치
					output += ' <input type="button" data-renum="'+item.re_num+'" value="수정" class="modify-btn">';
					output += ' <input type="button" data-renum="'+item.re_num+'" value="삭제" class="delete-btn">';
						}	
					//관리자가 타인의 댓글을 삭제할 수 있는 기능
					if(param.user_num != item.user_num && Number(param.status) == 4){
						output += ' <input type="button" data-renum="'+item.re_num+'" value="삭제" class="delete-btn">';
					}			
																					
				    output += "</div>";
					output += '</div>';
					output += '</div>';
					output += '</div>';				
					output += '<hr size="2" width="100%">';
					
					//댓글 목록 삽입
					$('#output').append(output);				
				});
				
				
				//페이지 버튼 처리
				if(currentPage>=Math.ceil(count/rowCount)){
				//다음 페이지가 없음
					$('.paging-button').hide();
				}else{
				//다음 페이지가 존재
					$('.paging-button').show();
				}				
			},error:function(){
				$('#loading').hide();
				alert('네트워크 오류 발생');
			}			
		});		
	}
	//페이지 처리 이벤트 연결(다음 댓글 보기 버튼 클릭시 데이터 추가)
	$('.paging-button input').click(function(){
		selectList(currentPage + 1);
	});
	
	
	/*=======================
	 * 댓글 등록
	 *=======================*/
	//댓글 등록 이벤트 연결
	$('#re_form').submit(function(event){
		if($('#re_content').val().trim()==''){
			alert('내용을 입력하세요');
			$('#re_content').val('').focus();
			return false;
		}
		//form 이하의 태그에 입력한 값 모두 읽어 쿼리 스트링으로 반환
		let form_data = $(this).serialize();
		//서버와 통신
		$.ajax({
			url:'writeReply.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 작성할 수 있습니다.');
				}else if(param.result == 'success'){
					//폼 초기화
					initForm();
					//댓글 작성 성공 시 등록글 포함 목록을 보여줌
					selectList(1);
				}else{
					alert('댓글 등록 오류 발생');
				}
			},error:function(){
				alert('네트워크 오류 발생');
			}			
		});
		//기본 이벤트 제거
		event.preventDefault();
	});
	
	//작성 폼 초기화 함수
	function initForm(){
		$('textarea').val('');
		$('#re_count').text('300/300');
	}
	
	/*==================
  	 댓글 등록 & 수정 공통	
	*===================*/
	$(document).on('keyup','textarea',function(){
		//글자수 구하기
		let inputLegnth = $(this).val().length;
		
		if(inputLegnth>300){//300자 초과 뒷자리 잘라버림
			$(this).val($(this).val().substring(0,300));						
		}else{//초과 전
			let remain = 300 - inputLegnth;
			remain += '/300';
			if($(this).attr('id') == 're_content'){
				//등록폼 글자수
				$('#re_count').text(remain);
			}else{
				$('#mre_count').text(remain);
			}
		}		
	});
	

	/* ================================
	 * 댓글 수정
	 * ================================ */
	//댓글 수정 버튼 클릭시 수정폼 노출
		$(document).on('click','.modify-btn',function(){
			//댓글 번호
			let re_num = $(this).attr('data-renum');

			//댓글 내용
			let content = $(this).closest('.sub-item').find('p').html().replace(/<br>/gi,'\n');// g:지정문자열 모두 ,i:대소문자 무시
			let mre_photo = $(this).closest('.sub-item').find('img').attr('src');
			let mre_nick_name = $(this).closest('.sub-item').find('.list-nick_name').text();
			
			//댓글 수정폼 UI
			let modifyUI = '<h4 class="mre-title">댓글 수정</h4>';	
			modifyUI += '<div class="sub-item" style="background-color : #ecebeb;">'; 
			modifyUI += '<form id="mre_form">';			
			modifyUI += '<div class="mre-profile">';		
			modifyUI += '<img src="' + mre_photo +'" width="50" height="50">';								
			modifyUI += '<div class="list-nick_name">' + mre_nick_name +'</div>';
			modifyUI += '</div>';              
			
			modifyUI += '<input type="hidden" name="re_num" id="mre_num" value="'+re_num+'">';
			modifyUI += '<textarea name="re_content" id="mre_content" class="rep-content">'+content+'</textarea>';     
			modifyUI += '<div id="mre_first">'       
			modifyUI += '<input type="submit" value="수정">';			                        
			modifyUI += '<input type="button" value="취소" class="re-reset"> <br>';    
			modifyUI += '<span style="margin-left: 2px;" class="letter-count" id="mre_count">300/300</span>'
		    modifyUI += '</div>'
			modifyUI += '</form>'
		    modifyUI += '</div>'
			
			//이전에 이미 수정하는 댓글이 있을 경우 수정버튼을 클릭하면 
			//숨김 sub-item 클래스로 지정한 div를 환원시키고 수정폼 제거
			initModifyForm();
			
			//지금 클릭해서 수정하고자 하는 데이터는 감추기
			//수정버튼은 감싸고 있는 div를 감춤
			$(this).closest('.sub-item').hide();
			
			//수정폼을 수정하고자 하는 데이터가 있는 div에 노출
			$(this).parents('.item').append(modifyUI);
			
			//입력한 글자수 셋팅
			let inputLength = $('#mre_content').val().length;
			let remain = 300 - inputLength;
			remain += '/300';
			//문서 객체에 반영
			$('#mre_first .letter-count').text(remain);
			
					
		})
		//댓글 수정폼 초기화
		function initModifyForm(){
			$('.sub-item').show();
			$('#mre_form').remove();
			$('.mre-title').remove();
		}
		
		//수정폼에서 취소 버튼 클릭시 수정폼 초기화
		$(document).on('click','.re-reset',function(){
			initModifyForm();
		});
		
		
		//댓글 수정
		$(document).on('submit','#mre_form',function(event){
			if($('#mre_content').val().trim()==''){
				alert('내용을 입력하세요!');
				$('#mre_content').val('').focus();
				return false;
			}
			//폼에 입력한 데이터 반환 serialize():form 이하의 모든 데이터 가져옴
			let form_data =$(this).serialize();
			//서버와 통신
			$.ajax({
				url:'updateReply.do',
				type:'post',
				data:form_data,
				dataType:'json',
				success:function(param){
					if(param.result=='logout'){
						alert('로그인 해야 수정할 수 있습니다.');
					}else if(param.result=='success'){
						$('#mre_form').parents('.item').find('p').html($('#mre_content').val().replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/\n/g, '<br>'));
						$('#mre_form').parents('.item').find('.modify-date').text('수정 : 5초미만'); 
						//수정폼 삭제 및 초기화
						initModifyForm();
					}else if(param.result=='wrongAccess'){
						alert('타인의 글을 수정할 수 없습니다.');
					}else{
						alert('댓글 수정 오류 발생!');
					}
				},
				error:function(){
					alert('네트워크 오류 발생!');
				}			
			});
			//기본 이벤트 제거
			event.preventDefault();
		});
	
	
	/* ================================
	 * 댓글 삭제
	 * ================================ */
	$(document).on('click','.delete-btn',function(){
		//댓글 번호
		let re_num = $(this).attr('data-renum');
		//서버와 통신
		$.ajax({
			url:'deleteReply.do',
			type:'post',
			data:{re_num:re_num},
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 삭제할 수 있습니다.');
				}else if(param.result =='wrongAccess'){
					alert('타인의 글은 삭제할 수 없습니다.')
				}else if(param.result == 'success'){
					alert('댓글 삭제 완료');
					selectList(1);
				}else{
					alert('댓글 삭제 오류 발생!');
				}
				
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
			
		});
		
	});
	
	/* ================================
	 * 초기 데이터(목록) 호출
	 * ================================ */	
	selectList(1);	
	
	
});















