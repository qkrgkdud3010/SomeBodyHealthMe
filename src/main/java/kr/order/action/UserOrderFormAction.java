package kr.order.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.cart.dao.CartDAO;
import kr.cart.vo.CartVO;
import kr.controller.Action;
import kr.goods.dao.GoodsDAO;
import kr.goods.vo.GoodsVO;

public class UserOrderFormAction implements Action{

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
			return "common/notice.jsp";
		}
		
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
			}
		}
		
		request.setAttribute("list", cartList);
		request.setAttribute("all_total", all_total);
		
		return "order/user_orderForm.jsp";
	}

}





