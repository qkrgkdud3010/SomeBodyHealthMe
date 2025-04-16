package kr.membership.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.membership.dao.MembershipDAO;

public class OrderAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int typeId=Integer.parseInt(request.getParameter("typeId"));
		int price=Integer.parseInt(request.getParameter("price"));
		int payment=Integer.parseInt(request.getParameter("payment"));
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		
		int receive_phone = Integer.parseInt(request.getParameter("receive_phone"));
		MembershipDAO dao = MembershipDAO.getInstance();
		dao.MembershipOrder(typeId, user_num,price, payment,receive_phone);
		String url = request.getContextPath()+"/main/main.do";
		response.addHeader("Refresh", "2;url="+url);
		request.setAttribute("result_title", "상품주문 완료");
		request.setAttribute("result_msg", 
				                     "주문이 완료되었습니다.");
		request.setAttribute("result_url", url);
		return "common/result_view.jsp";
	}

}
