package kr.order.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderVO;
import kr.util.PagingUtil;

public class AdminListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		Integer status = 
				(Integer)session.getAttribute("status");
		if(status!=4) {//관리자로 로그인하지 않은 경우
			return "common/notice.jsp";
		}
		//관리자로 로그인 한 경우
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum = "1";
		}
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		OrderDAO dao = OrderDAO.getInstance();
		int count = dao.getOrderCount(keyfield, keyword);
		//페이지 처리
		PagingUtil page = 
				new PagingUtil(keyfield,keyword,
						 Integer.parseInt(pageNum),
						 count,20,10,"adminList.do");
		List<OrderVO> list = null;
		if(count > 0) {
			list = dao.getListOrder(page.getStartRow(),
					                page.getEndRow(),
					                keyfield,keyword);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "order/admin_list.jsp";
	}

}
