package kr.goods.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.goods.dao.GoodsDAO;
import kr.goods.vo.GoodsVO;
import kr.util.StringUtil;

public class UpdateFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		Integer status = (Integer)session.getAttribute("status");
		if(status != 4) {//관리자로 로그인하지 않은 경우
			return "common/notice.jsp";
		}
		
		long goods_num = Long.parseLong(request.getParameter("goods_num"));
		GoodsDAO dao = GoodsDAO.getInstance();
		GoodsVO goods = dao.getGoods(goods_num);
		//큰 따옴표 처리(수정폼의 input 태그에만 명시)
		goods.setGoods_name(
				StringUtil.parseQuot(goods.getGoods_name()));
		
		request.setAttribute("goods", goods);
		
		return "goods/updateForm.jsp";
	}

}





