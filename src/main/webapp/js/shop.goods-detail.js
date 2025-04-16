$(function(){
	/* ======================
	 * 주문수량 변경
	 * ====================== */
	$('#order_quantity').on('input',function(){
		if($('#order_quantity').val()==''){
			$('#goods_total_txt').text('0원');
			return;
		}
		if($('#order_quantity').val() <= 0){
			$('#order_quantity').val('');
			$('#goods_total_txt').text('0원');
			return;
		}
		if(Number($('#goods_quantity').val()) < $('#order_quantity').val()){
			alert('수량이 부족합니다.');
			$('#order_quantity').val('');
			$('#goods_total_txt').text('0원');
			return;
		}
		let total = $('#goods_price').val() * $('#order_quantity').val();
		$('#goods_total_txt').text(total.toLocaleString()+'원');
	});
		/* =====================
		 * 장바구니 상품 담기
		 *====================== */	
		$('#cartgoods').click(function(event){
			if($('#order_quantity').val()==''){
				alert('수량을 입력하세요!');
				$('#order_quantity').focus();
				return false;
			}
			
			let form_data = $('#goods_buy').serialize();
			
			$.ajax({
				url:'../cart/write.do',
				type:'post',
				data:form_data,
				dataType:'json',
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인 후 사용하세요');
					}else if(param.result == 'success'){
						alert('장바구니에 상품을 담았습니다.');
						location.href='../cart/list.do';
					}else if(param.result == 'overQuantity'){
						alert('기존에 주문한 상품입니다. 개수를 추가하면 재고가 부족합니다.');
					}else{
						alert('장바구니 담기 오류');
					}
				},
				error:function(){
					alert('네트워크 오류 발생21');
				}
			});
			
			//기본 이벤트 제거
			event.preventDefault();
		});
		
		
		/* 바로 구매하기 */
		$('#buygoods').click(function(event){
					if($('#order_quantity').val()==''){
						alert('수량을 입력하세요!');
						$('#order_quantity').focus();
						return false;
					}
				});
		
});



