package kr.cart.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.cart.dao.CartDAO;
import kr.cart.vo.CartVO;
import kr.controller.Action;
import kr.goods.dao.GoodsDAO;
import kr.goods.vo.GoodsVO;
import kr.util.StringUtil;

public class ModifyCartAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {
			mapAjax.put("result", "logout");
		}else {
			request.setCharacterEncoding("utf-8");
			long goods_num = Long.parseLong(request.getParameter("goods_num"));
			int order_quantity = Integer.parseInt(request.getParameter("order_quantity"));
			GoodsDAO goodsDao = GoodsDAO.getInstance();
			GoodsVO goods = goodsDao.getGoods(goods_num);
			if(goods.getGoods_status()==1) {//상품 미표시
				mapAjax.put("result", "noSale");
			}else if(goods.getGoods_quantity() < order_quantity) {
				//상품 재고 수량보다 장바구니에 담은 구매 수량이 더 많음 
				mapAjax.put("result", "overQuantity");
			}else {//표시 상품이며 재고가 부족하지 않음
				CartVO cart = new CartVO();
				cart.setCart_num(Long.parseLong(request.getParameter("cart_num")));
				cart.setOrder_quantity(order_quantity);
				
				CartDAO cartDao = CartDAO.getInstance();
				//구매 수량 변경
				cartDao.updateCart(cart);
				mapAjax.put("result", "success");
			}
			
		}
		return StringUtil.parseJSON(request, mapAjax);
	}

}
