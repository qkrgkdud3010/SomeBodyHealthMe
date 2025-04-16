package kr.membership.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.membership.dao.MembershipDAO;

public class MembershipOrderFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//비로그인
			return "redirect:/member/loginForm.do";
		}		
		return "membership/membershipOrderForm.jsp";
	}

}
