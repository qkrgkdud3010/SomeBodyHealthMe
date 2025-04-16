package kr.mybody.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.mybody.dao.MyBodyDAO;
import kr.mybody.vo.MyBodyStatusVO;

public class MyStatusAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {
			//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인이 된 경우
		
		MyBodyDAO dao = MyBodyDAO.getInstance();
		MyBodyStatusVO mybodystatus = dao.getMyBodyStatus(user_num);
		
		request.setAttribute("mybodystatus", mybodystatus);
		//JSP 경로 반환
		
		return "mybody/myStatus.jsp";
	}
}
