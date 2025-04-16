package kr.order.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.goods.dao.GoodsDAO;
import kr.goods.vo.GoodsVO;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderDetailVO;
import kr.order.vo.OrderVO;

public class WriteAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = 
				(Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}

		//POST 방식의 접근만 허용
		if(request.getMethod().toUpperCase().equals("GET")) {
			return "redirect:/goods/list.do";
		}

		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");

		//데이터 인코딩 , 데이터 반환
		request.setCharacterEncoding("utf-8");
		long goods_num = Long.parseLong(request.getParameter("goods_num"));
		int order_quantity = Integer.parseInt(request.getParameter("order_quantity"));
		int goods_price = Integer.parseInt(request.getParameter("goods_price"));
		int goods_total = order_quantity * goods_price;

		//조건체크
		GoodsDAO goodsDao = GoodsDAO.getInstance();
		GoodsVO db_goods = goodsDao.getGoods(goods_num);
		if(order_quantity >  db_goods.getGoods_quantity() || goods_total <=0) {
			request.setAttribute("notice_msg","정상적인 주문이 아니거나 상품의 수량이 부족합니다.");
			request.setAttribute("notice_url",request.getContextPath()+"/goods/list.do");
			return "common/alert_view.jsp";
		}
		if(db_goods.getGoods_status()==1) {
			//상품 미표시
			request.setAttribute("notice_msg","["+db_goods.getGoods_name()+"]상품 판매 중지");
			request.setAttribute("notice_url",request.getContextPath()+"/goods/list.do");
			return "common/alert_view.jsp";
		}

		if(db_goods.getGoods_quantity() < order_quantity) {
			//재고수량 부족
			request.setAttribute("notice_msg","["+db_goods.getGoods_name()+"]재고수량 부족으로 주문 불가");
			request.setAttribute("notice_url",request.getContextPath()+"/goods/list.do");
			return "common/alert_view.jsp";
		}

		//주문상세정보를 자바빈에 담기
		OrderDetailVO orderDetail = new OrderDetailVO();
		orderDetail.setGoods_num(goods_num);
		orderDetail.setGoods_name(db_goods.getGoods_name());
		orderDetail.setGoods_price(db_goods.getGoods_price());
		orderDetail.setOrder_quantity(order_quantity);
		orderDetail.setGoods_total(goods_total);




		//주문정보 담기
		OrderVO order = new OrderVO();
		order.setOrder_total(goods_total);
		order.setPayment(Integer.parseInt(
				request.getParameter("payment")));
		order.setReceive_name(
				request.getParameter("receive_name"));
		order.setReceive_post(
				request.getParameter("receive_post"));
		order.setReceive_address1(
				request.getParameter("receive_address1"));
		order.setReceive_address2(
				request.getParameter("receive_address2"));
		order.setReceive_phone(
				request.getParameter("receive_phone"));
		order.setNotice(request.getParameter("notice"));
		order.setUser_num(user_num);

		OrderDAO orderDao = OrderDAO.getInstance();
		//상품주문 메서서(한개) 호출
		orderDao.insertOrder(order, orderDetail);

		//Refresh 정보를 응답 헤더에 추가
		String url = request.getContextPath()+"/order/orderList.do";
		response.addHeader("Refresh", "2;url="+url);
		request.setAttribute("result_title", "상품주문 완료");
		request.setAttribute("result_msg","주문이 완료되었습니다.");
		request.setAttribute("result_url", url);

		return "common/result_view.jsp";	
	}
}
