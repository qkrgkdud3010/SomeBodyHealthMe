package kr.goods.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.goods.dao.GoodsDAO;
import kr.goods.vo.GoodsVO;

public class LikePageAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		GoodsDAO goodsDao = GoodsDAO.getInstance();
		List<GoodsVO> goodsList = goodsDao.getListGoodsLike(1,5,user_num);
		
		request.setAttribute("goodsList", goodsList);
		
		return "goods/likePage.jsp";
	}

}
