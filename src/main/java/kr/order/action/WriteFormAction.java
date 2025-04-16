package kr.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.goods.dao.GoodsDAO;
import kr.goods.vo.GoodsVO;
import kr.order.vo.OrderDetailVO;

public class WriteFormAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}

		//POST 방식의 접근만 허용
		if(request.getMethod().toUpperCase().equals("GET")) {
			return "common/notice.jsp";
		}

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
		
		//자바빈 객체 생성 및 데이터 담기
		OrderDetailVO cart = new OrderDetailVO();
		cart.setGoods_num(goods_num);
		cart.setGoods_name(db_goods.getGoods_name());
		cart.setOrder_quantity(order_quantity);
		cart.setGoods_price(goods_price);
		cart.setGoods_total(goods_total);
		
		request.setAttribute("cart", cart);
		request.setAttribute("photo", db_goods.getGoods_img1());

		return "/order/writeForm.jsp";
	}
}
















