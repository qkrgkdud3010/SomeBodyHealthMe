package kr.order.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.cart.dao.CartDAO;
import kr.cart.vo.CartVO;
import kr.controller.Action;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderDetailVO;
import kr.order.vo.OrderVO;
import kr.util.StringUtil;

public class UserModifyFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		long order_num = Long.parseLong(request.getParameter("order_num"));
		OrderDAO dao = OrderDAO.getInstance();
		OrderVO order = dao.getOrder(order_num);
		
		if(order.getUser_num()!=user_num) {
			//구매자 회원번호와 로그인한 회원번호가 불일치할 경우
			return "common/notice.jsp";
		}
		List<OrderDetailVO> detailList = dao.getListOrderDetail(order_num);
		request.setAttribute("order", order);
		request.setAttribute("detailList", detailList);
		
		return "order/user_modifyForm.jsp";
	}

}





