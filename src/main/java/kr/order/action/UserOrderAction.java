package kr.order.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.cart.dao.CartDAO;
import kr.cart.vo.CartVO;
import kr.controller.Action;
import kr.goods.dao.GoodsDAO;
import kr.goods.vo.GoodsVO;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderDetailVO;
import kr.order.vo.OrderVO;

public class UserOrderAction implements Action{

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
		
		CartDAO dao = CartDAO.getInstance();
		int all_total = dao.getTotalByUser_num(user_num);
		if(all_total<=0) {
			request.setAttribute("notice_msg", 
			  "정상적인 주문이 아니거나 상품의 수량이 부족합니다.");
			request.setAttribute("notice_url", 
			   request.getContextPath()+"/goods/list.do");
			return "common/alert_view.jsp";
		}
		
		//장바구니에 담겨있는 상품 정보 호출
		List<CartVO> cartList = dao.getListCart(user_num);
		
		//개별 상품 정보 담기
		List<OrderDetailVO> orderDetailList = 
				            new ArrayList<OrderDetailVO>();
		
		GoodsDAO goodsDao = GoodsDAO.getInstance();
		for(CartVO cart : cartList) {
			GoodsVO goods = goodsDao.getGoods(cart.getGoods_num());
			
			if(goods.getGoods_status()==1) {
				//상품 미표시
				request.setAttribute("notice_msg", 
						"["+goods.getGoods_name()+"]상품 판매 중지");
				request.setAttribute("notice_url", 
				   request.getContextPath()+"/cart/list.do");
				return "common/alert_view.jsp";
			}
			
			if(goods.getGoods_quantity() < cart.getOrder_quantity()) {
				//재고수량 부족
				request.setAttribute("notice_msg", 
						"["+goods.getGoods_name()+"]재고수량 부족으로 주문 불가");
				request.setAttribute("notice_url", 
				   request.getContextPath()+"/cart/list.do");
				return "common/alert_view.jsp";
			}//end of if
			
			//주문상세정보를 자바빈에 담기
			OrderDetailVO orderDetail = new OrderDetailVO();
			orderDetail.setGoods_num(cart.getGoods_num());
			orderDetail.setGoods_name(cart.getGoodsVO().getGoods_name());
			orderDetail.setGoods_price(cart.getGoodsVO().getGoods_price());
			orderDetail.setOrder_quantity(cart.getOrder_quantity());
			orderDetail.setGoods_total(cart.getSub_total());
			
			orderDetailList.add(orderDetail);
			
		}//end of for
		
		//주문정보 담기
		OrderVO order = new OrderVO();
		order.setOrder_total(all_total);
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
		orderDao.insertOrder(order, orderDetailList);
		
		//Refresh 정보를 응답 헤더에 추가
		String url = request.getContextPath()+"/main/main.do";
		response.addHeader("Refresh", "2;url="+url);
		request.setAttribute("result_title", "상품주문 완료");
		request.setAttribute("result_msg", 
				                     "주문이 완료되었습니다.");
		request.setAttribute("result_url", url);
		
		return "common/result_view.jsp";
		
		
	}

}





