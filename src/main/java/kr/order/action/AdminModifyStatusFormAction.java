package kr.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderVO;

public class AdminModifyStatusFormAction implements Action{

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
		
		long order_num = Long.parseLong(request.getParameter("order_num"));
		OrderDAO dao = OrderDAO.getInstance();
		OrderVO order = dao.getOrder(order_num);
		
		request.setAttribute("order", order);
		//JSP 경로 반환
		return "order/admin_detail.jsp";
	}

}
