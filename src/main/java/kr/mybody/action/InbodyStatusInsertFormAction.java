package kr.mybody.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class InbodyStatusInsertFormAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {
			//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		return "mybody/inbodyStatusInsertForm.jsp";
	}
	
}
