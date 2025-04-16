package kr.goods.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.goods.dao.GoodsDAO;
import kr.goods.vo.GoodsVO;
import kr.util.StringUtil;

public class DetailAction implements Action{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		Integer status = (Integer)session.getAttribute("status");
		// 상품번호 반환
		long goods_num = Long.parseLong(request.getParameter("goods_num"));
		
		GoodsDAO dao = GoodsDAO.getInstance();
		
		int recount = dao.getGoodsReviewCount(goods_num);
		
		
		GoodsVO goods = dao.getGoods(goods_num);
		
		//상품설명 줄바꿈 처리(HTML 태그 허용)
		goods.setGoods_info(StringUtil.useBrHtml(goods.getGoods_info()));
		
		
		request.setAttribute("goods", goods);
		request.setAttribute("recount", recount);
		//JSP 경로 반환
		return "goods/detail.jsp";
	}
}
