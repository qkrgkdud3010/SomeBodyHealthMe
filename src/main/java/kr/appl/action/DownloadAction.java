package kr.appl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.appl.dao.ApplDAO;
import kr.controller.Action;

public class DownloadAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		Integer status = (Integer)session.getAttribute("status");

		if(user_num == null) {//비로그인
			return "redirect:/member/loginForm.do";
		}else if(status != 4) {//접근권한없음
			return "common/notice.jsp";
		}		

		//지원번호 받기
		long appl_num = Long.parseLong(request.getParameter("appl_num"));
		ApplDAO dao  = ApplDAO.getInstance();
		String realName = dao.getAppl(appl_num).getAppl_attachment();

		//파일이 없는 경우
		if(realName == null || "".equals(realName)) {
			request.setAttribute("notice_msg", "첨부파일이 없는 지원 정보입니다.");
			request.setAttribute("notice_url", request.getContextPath()+"/appl/detailByAdmin.do?appl_num="+appl_num);
			return "common/alert_view.jsp";
		}

		if(ApplFile.downloadFile(request, response, realName) == 1) {
			request.setAttribute("notice_msg", "서버에 저장된 파일이 없습니다.");
			request.setAttribute("notice_url", request.getContextPath()+"/appl/detailByAdmin.do?appl_num="+appl_num);
			return "common/alert_view.jsp";
		}
		
		return null;
		
	}
}
