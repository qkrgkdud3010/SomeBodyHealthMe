package kr.goods.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.goods.dao.GoodsDAO;
import kr.goods.vo.GoodsVO;
import kr.util.PagingUtil;
import kr.controller.Action;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		
		String pageNum =request.getParameter("pageNum");
		if(pageNum == null) pageNum ="1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		GoodsDAO dao = GoodsDAO.getInstance();
		int count = dao.getGoodsCount(keyfield, keyword, 1);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield, keyword, Integer.parseInt(pageNum),count,10,10,"list.do");
		
		List<GoodsVO> list = null;
		if(count > 0 ) {
			list = dao.getListGoods(page.getStartRow(), page.getEndRow(), keyfield, keyword, 1);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		//jsp 경로 반환
		return "goods/list.jsp";
	}
	
}
