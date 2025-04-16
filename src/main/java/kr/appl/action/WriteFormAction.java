package kr.appl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.appl.dao.ApplDAO;
import kr.appl.vo.ApplVO;
import kr.controller.Action;

public class WriteFormAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		Integer status = (Integer)session.getAttribute("status");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		if(status != 1) {//일반회원이 아닌 사람이 지원한 경우
			request.setAttribute("notice_msg", "일반회원만 지원할 수 있습니다.");
			request.setAttribute("notice_url", request.getContextPath()+"/main/main.do");
			return "common/alert_view.jsp";
		}
		
		//미확인 지원이 있다면 상세페이지로 가자고
		ApplDAO dao = ApplDAO.getInstance();
		ApplVO unchecked = dao.getMyUncheckedappl(user_num);
		if(unchecked != null) {
			request.setAttribute("notice_msg", "관리자가 미확인한 지원내역이 있습니다.");
			request.setAttribute("notice_url", request.getContextPath()+"/appl/detail.do?appl_num=" + unchecked.getAppl_num());
			return "common/alert_view.jsp";
		}
		
		return "/appl/writeForm.jsp";
	}
}
