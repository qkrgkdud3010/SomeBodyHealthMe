$(function(){
	let rowCount = 10;
	let currentPage;
	let count;

	/*===========================
		댓글 목록
	===========================*/
	function selectList(pageNum){
		currentPage = pageNum;
		
		$('#loading').show();
		$.ajax({
			url: 'listReply.do',
			type: 'post',
			data: {pageNum: pageNum, rowCount: rowCount, goods_num: $('#goods_num').val(), status: $('#status').val()},
			dataType: 'json',
			success: function(param){
				$('#loading').hide();
				count = param.count;
				
				if(pageNum == 1){
					$('#output').empty();
				}
				$(param.list).each(function(index, item){
					
					let output = '<div class="item">';
					// 평점에 맞는 이미지를 동적으로 표시 (각 평점에 맞는 이미지를 보여주는 부분)
					let rating = item.re_rating;  // 각 리뷰의 평점 값 가져오기
					output += '<div class="rating-value" data-rating="' + rating + '">';
					// 평점에 맞는 이미지를 보여주는 코드
					let starImage;
					switch (parseInt(rating)) {
						case 1: starImage = contextPath + '/images/star1.png'; break;
						case 2: starImage = contextPath + '/images/star2.png'; break;
						case 3: starImage = contextPath + '/images/star3.png'; break;
						case 4: starImage = contextPath + '/images/star4.png'; break;
						case 5: starImage = contextPath + '/images/star5.png'; break;
						default: starImage = contextPath + '/images/star.png'; break;  // 평점이 없는 경우
						}
						// 이미지로 평점 표시 (별을 클릭할 수 없도록 표시)
						output += '<img src="' + starImage + '" class="star-image" />';
						output += '</div>';
					output += '<h4>' + item.nick_name + '</h4>';
					output += '<div class="sub-item">';
					output += '<p>' + item.re_content + '</p>';
					
					
					
					if(item.re_mdate){
						output += '<span class="modify-date">최근 수정일: ' + item.re_mdate + '</span>';
					}else{
						output += '<span class="modify-date">등록일: ' + item.re_date + '</span>';
					}
					
					if(param.user_num == item.user_num){
						output += '<div id="button-container">';
						output += '<input type="button" data-renum="'+item.re_num+'" value="수정" class="modify-btn">';						
						output += '<input type="button" data-renum="'+item.re_num+'" value="삭제" class="delete-btn">';
						output += '</div>';
					}else if(param.status == 4){
						output += '<div class="button-container">';
						output += '<input type="button" data-renum="'+item.re_num+'" value="삭제" class="delete-btn">';
						output += '</div>';
					}
					
					output += '</div>';
					output += '</div>';
					
					$('#output').append(output);
				});

				if(currentPage >= Math.ceil(count / rowCount)){
					$('.paging-button').hide();
				}else{
					$('.paging-button').show();
				}
				
				if(param.isReviewed || !param.user_num || !param.checkBuy) {
					$('#re_content').prop('disabled', true); 
					$('#re_content').attr('placeholder', '이미 리뷰를 작성하셨거나 구매 후 리뷰를 작성할 수 있습니다.');
				} else {
					$('#re_content').prop('disabled', false); 
					$('#re_content').attr('placeholder', '리뷰를 입력하세요');
				}
			},
			error: function(){
				$('#loading').hide();
				alert('네트워크 오류발생1');
			}
		});
	}

	$('.paging-button input').click(function(){
		selectList(currentPage + 1);
	});

	/*===========================
		댓글 등록
	===========================*/
	$('#re_form').submit(function(event){
		if($('#re_content').val().trim() == ''){
			alert('내용을 입력하세요!');
			$('#re_content').val('').focus();
			return false;
		}

		// 평점이 선택되지 않았으면 오류 메시지
		let selectedRating = $('.rating-value').attr('data-rating');
		if (!selectedRating || selectedRating === 'null') {
			alert("평점을 선택해주세요!");
			return false;
		}

		let form_data = $(this).serialize() + '&re_rating=' + selectedRating;
		
		$.ajax({
			url: 'writeReply.do',
			type: 'post',
			data: form_data,
			dataType: 'json',
			success: function(param){
				if(param.result == 'logout'){
					alert('로그인해야 작성할 수 있습니다.')
				}else if(param.result == 'success'){
					initForm();
					selectList(1);
				}else{
					alert('댓글 등록 오류 발생');
				}
			},
			error: function(){
				alert('네트워크 오류발생 15');
			}
		});
		
		event.preventDefault();
	});

	function initForm(){
		$('textarea').val('');
		$('#re_first.letter-count').text('300/300');
	}
	
	

	/*===========================
		별점
	===========================*/
	$(document).ready(function(){
	    $('.star').on('click', function() {
	        var rating = $(this).data('rating');
	        $('.star').attr('src', function() {
	            return $(this).attr('src').replace(contextPath + '/images/star.png', contextPath + '/images/emptystar.png');
	        });

	        for (var i = 1; i <= rating; i++) {
	            $('#star' + i).attr('src', contextPath + '/images/star.png');
	        }

	        $('.rating-value').attr('data-rating', rating); // 평점 값 설정
	    });
	});

	$(document).on('click', '.star', function() {
	    var rating = $(this).data('rating');
	    var ratingContainer = $(this).closest('.rating-value');
	    ratingContainer.attr('data-rating', rating);

	    ratingContainer.find('.star').attr('src', function() {
	        return $(this).attr('src').replace(contextPath + '/images/star.png', contextPath + '/images/emptystar.png');
	    });

	    for (var i = 1; i <= rating; i++) {
	        ratingContainer.find('#star' + i).attr('src', contextPath + '/images/star.png');
	    }
	});

	/*===========================
		댓글 수정
	===========================*/
	// 댓글 수정 버튼 클릭 시
	$(document).on('click', '.modify-btn', function () {
	    let re_num = $(this).attr('data-renum');
	    let content = $(this).parent().closest('.item').find('.sub-item p').html().replace(/<br>/gi, '\n'); 
	    let rating = $(this).parent().find('.rating-value').attr('data-rating');  // 기존 평점 값 가져오기

	    // 기존 수정 폼이 있을 경우 삭제
	    $(this).closest('.item').find('#mre_form').remove();

	    // 수정 UI를 생성
	    let modifyUI = '<form id="mre_form">';
	    modifyUI += '<input type="hidden" name="re_num" id="mre_num" value="' + re_num + '">';
	    modifyUI += '<div class="rating-value" data-rating="' + rating + '">';
	    for (let i = 1; i <= 5; i++) {
	        modifyUI += '<label>';
	        modifyUI += '<img src="' + (i <= rating ? contextPath + '/images/star.png' : contextPath + '/images/emptystar.png') + '" class="star" data-rating="' + i + '" id="star' + i + '" />';
	        modifyUI += '</label>';
	    }
	    modifyUI += '</div>';
	    modifyUI += '<textarea rows="3" cols="50" name="re_content" id="mre_content" class="rep-content">' + content + '</textarea>';
	    modifyUI += '<div id="mre_first"><span class="letter-count">300/300</span></div>';
	    modifyUI += '<div id="mre_second" class="align-right">';
	    modifyUI += ' <input type="submit" value="수정" id="my-btn">';
	    modifyUI += ' <input type="button" value="취소" class="re-reset">';
	    modifyUI += "</div>";
	    modifyUI += '<hr size="1" noshade width="96%">';
	    modifyUI += '</form>';

	    // 수정 버튼을 눌렀을 때 삭제 버튼 숨기기
	    $(this).hide(); // 수정 버튼 숨기기
	    $(this).parent().find('.delete-btn').hide(); // 삭제 버튼 숨기기

	    // 수정 UI를 추가
	    $(this).parents('.item').append(modifyUI);

	    // 평점 설정
	    setRatingForModifyForm(rating);

	    // 글자 수 제한
	    let inputLength = $('#mre_content').val().length;
	    let remain = 300 - inputLength;
	    remain += '/300';
	    $('#mre_first .letter-count').text(remain);
	});

	function setRatingForModifyForm(rating) {
	    var ratingContainer = $('#mre_form .rating-value');
	    
	    // 수정 폼의 평점 값 설정
	    ratingContainer.attr('data-rating', rating);

	    // 평점 UI 업데이트
	    for (var i = 1; i <= 5; i++) {
	        var starImg = ratingContainer.find('.star[data-rating="'+i+'"]');
	        if (i <= rating) {
	            starImg.attr('src', contextPath + '/images/star.png'); // 선택된 별
	        } else {
	            starImg.attr('src', contextPath + '/images/emptystar.png'); // 선택되지 않은 별
	        }
	    }
	}

	// 별점 클릭 이벤트 처리
	// 수정폼 내 별점 클릭 이벤트
	$(document).on('click', '#mre_form .star', function() {
	    var rating = $(this).data('rating'); // 클릭된 별점 (1~5)
	    var ratingContainer = $(this).closest('.rating-value'); // 평점 컨테이너 찾기
	    
	    // 평점 값 변경
	    ratingContainer.attr('data-rating', rating);
	    
	    // 별 이미지의 src 속성 변경 (모든 별을 빈 별로 초기화)
	    ratingContainer.find('.star').attr('src', function() {
	        return $(this).attr('src').replace(contextPath + '/images/star.png', contextPath + '/images/emptystar.png');
	    });

	    // 선택된 별에 대해 채워진 별 이미지로 변경
	    for (var i = 1; i <= rating; i++) {
	        ratingContainer.find('#star' + i).attr('src', contextPath + '/images/star.png');
	    }
	});

	$(document).on('submit', '#mre_form', function(event) {
	    let selectedRating = $('#mre_form .rating-value').attr('data-rating');
	    
	    // 평점이 없으면 오류 메시지 표시
	    if (!selectedRating || selectedRating === 'undefined') {
	        alert("평점을 선택해주세요!");
	        return false;
	    }

	    let form_data = $(this).serialize() + '&re_rating=' + selectedRating;

	    $.ajax({
	        url: 'updateReply.do', 
	        type: 'post',
	        data: form_data,
	        dataType: 'json',
	        success: function(param) {
	            if (param.result == 'success') {
	                // 성공시 동작
	                $(this).parent().find('.modify-btn').show();
	                $(this).parent().find('.delete-btn').show();
	                $(this).parent().find('.modify-date').text(param.modifyDate);
	                $(this).remove();
	                selectList(currentPage);
	            } else {
	                alert('댓글 수정 오류 발생');
	            }
	        },
	        error: function(xhr, status, error) {
	            console.log("AJAX Error:", error);
	            alert('네트워크 오류발생 19');
	        }
	    });
	    event.preventDefault();
	});

	// 취소 버튼 클릭 시
	$(document).on('click', '.re-reset', function() {
	    // 수정 폼을 삭제하고 기존 UI로 돌아가기
	    var item = $(this).closest('.item'); // 부모 요소인 .item을 찾음
	    item.find('#mre_form').remove(); // 수정 폼 삭제
	    item.find('.modify-btn').show(); // 수정 버튼 다시 표시
	    item.find('.delete-btn').show(); // 삭제 버튼 다시 표시
	    item.find('.modify-date').show(); // 수정 날짜 표시
	});

	/*===========================
		댓글 삭제
	===========================*/
	$(document).on('click', '.delete-btn', function(){
		let re_num = $(this).data('renum');
		let deleteConfirm = confirm("정말 삭제하시겠습니까?");
		
		if(deleteConfirm){
			$.ajax({
				url: 'deleteReply.do',
				type: 'post',
				data: {re_num: re_num},
				dataType: 'json',
				success: function(param){
					if(param.result == 'success'){
						selectList(currentPage);
					}else{
						alert('댓글 삭제 오류');
					}
				},
				error: function(){
					alert('네트워크 오류발생3');
				}
			});
		}
	});
	

	
	$(document).ready(function(){
	    // 페이지가 로드될 때 서버에서 평균 rating 값을 가져와서 표시
	    $.ajax({
	        url: 'getAverageRating.do',  // 평균 점수를 가져오는 서버의 URL
	        type: 'post',
	        data: {goods_num: $('#goods_num').val()},
	        dataType: 'json',
	        success: function(param) {
	            if (param.result == 'success') {
	                $('#avg_rating_value').text(param.avg_rating.toFixed(1));  // 평균값 소수점 한 자리까지 표시
	            } else {
	                $('#avg_rating_value').text('없음');
	            }
	        },
	        error: function() {
	            alert('평균 점수 로딩 오류');
	        }
	    });
	});
	
	
		
	/*===========================
		* 초기 데이터(목록) 호출
	================================*/
	selectList(1);
	
	
	
});