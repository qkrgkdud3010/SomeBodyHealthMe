package kr.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderVO;

public class AdminModifyStatusAction implements Action{

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
		
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		OrderVO order = new OrderVO();
		order.setStatus(Integer.parseInt(request.getParameter("status")));
		order.setOrder_num(Long.parseLong(request.getParameter("order_num")));
		
		OrderDAO dao = OrderDAO.getInstance();
		OrderVO db_order = dao.getOrder(order.getOrder_num());
		
		//사용자가 배송상태를 5로 변경했을 경우
		if(db_order.getStatus()==5) {
			request.setAttribute("notice_msg", 
					"사용자가 배송상태를 주문취소로 변경해서 "
					+ "관리자가 배송상태를 수정할 수 없습니다.");
			request.setAttribute("notice_url", 
					request.getContextPath()
					+"/order/adminDetail.do?order_num="+order.getOrder_num());
			return "common/alert_view.jsp";
		}
		dao.updateOrderStatus(order);
		
		request.setAttribute("notice_msg", "정상적으로 수정되었습니다.");
		request.setAttribute("notice_url", request.getContextPath()
				+"/order/adminDetail.do?order_num="+order.getOrder_num());
		
		return "common/alert_view.jsp";
	}

}
