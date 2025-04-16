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

public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();

        Long user_num = (Long)session.getAttribute("user_num");
        if (user_num == null) {
            mapAjax.put("result", "logout");
        }else {//로그인 된 경우
        	   request.setCharacterEncoding("utf-8");
        	   //자바빈 생성
        	   CartVO cart = new CartVO();
        	   cart.setGoods_num(Long.parseLong(request.getParameter("goods_num")));
        	   cart.setOrder_quantity(Integer.parseInt(request.getParameter("order_quantity")));
        	   cart.setUser_num(user_num);
        	   
        	   CartDAO dao = CartDAO.getInstance();
        	   CartVO db_cart = dao.getCart(cart);
        	   if(db_cart==null) {//동일상품이 없을 경우
        		   dao.insertCart(cart);
        		   mapAjax.put("result", "success");
        	   }else {//동일상품이 있을경우
        		   GoodsDAO goodsDao = GoodsDAO.getInstance();
        		   GoodsVO goods = goodsDao.getGoods(db_cart.getGoods_num());
        		   
        		   //구매수량 합산(기존 장바구니에 저장된 구매수량 + 새로 입력한 구매수량)
        		   int order_quantity = db_cart.getOrder_quantity() + cart.getOrder_quantity();
        		   if(goods.getGoods_quantity()<order_quantity) {
        			   //상품재고 수량보다 장바구니에 담은 구매수량이 더 많음
        			    mapAjax.put("result", "overQuantity");
        		   }else {
        			   cart.setOrder_quantity(order_quantity);
        			   dao.updateCartByGoods_num(cart);
        			   mapAjax.put("result", "success");
        		   }
        	   }
        	   
        }
     
        
		return StringUtil.parseJSON(request, mapAjax);
	}

}
