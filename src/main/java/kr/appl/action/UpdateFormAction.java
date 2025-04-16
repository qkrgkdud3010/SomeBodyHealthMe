package kr.appl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.appl.dao.ApplDAO;
import kr.appl.vo.ApplVO;
import kr.controller.Action;

public class UpdateFormAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		long appl_num = Long.parseLong(request.getParameter("appl_num"));
		ApplDAO dao = ApplDAO.getInstance();
		ApplVO appl = dao.getAppl(appl_num);
		
		if(appl.getUser_num() != user_num) {//작성자 수정자 일치 여부
			return "redirect:/common/notice.jsp";
		}

		//파일명 가져오기
		String filename = appl.getAppl_attachment();
		if(filename != null && !"".equals(filename)) {
			int underbar = filename.indexOf('_');
			appl.setAppl_attachment(filename.substring(underbar + 1));
		}
		
		request.setAttribute("appl", appl);
		
		return "appl/updateForm.jsp";
	}
}
