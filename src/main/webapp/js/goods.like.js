$(function(){
	/*======================================
	 * 좋아요 선택 여부와 선택한 총 개수를 읽기
	 *====================================== */
	function selectLike(){
		//서버와 통신
		$.ajax({
			url:'getLike.do',
			type:'post',
			data:{goods_num:$('#output_like').attr('data-num')},
			dataType:'json',
			success:function(param){
				displayLike(param);
			},
			error:function(){
				alert('네트워크 오류 발생1');
			}
		});
	}
	/*======================================
	 * 좋아요 등록 (및 삭제) 이벤트 연결
	 *====================================== */	
	$('#output_like').click(function(){
		//서버와 통신
		$.ajax({
			url:'writeLike.do',
			type:'post',
			data:{goods_num:$(this).attr('data-num')},
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인 후 좋아요를 눌러주세요');
				}else if(param.result=='success'){
					displayLike(param);
				}else{
					alert('좋아요 등록/삭제 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생2');
			}
		});
	});
	/*======================================
	 * 좋아요 표시 함수
	 *====================================== */		
	function displayLike(param){
		let output;
		if(param.status == 'yesLike'){//좋아요 선택
			output = '../images/like02.png';
		}else{//좋아요 미선택
			output = '../images/like01.png';
		}
		//문서 객체에 설정
		$('#output_like').attr('src',output);
		$('#output_lcount').text(param.count);
	}
	/*======================================
     * 좋아요 표시 함수
	 *====================================== */	
	selectLike();
});




