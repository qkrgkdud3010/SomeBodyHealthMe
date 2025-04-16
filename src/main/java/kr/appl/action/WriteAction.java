package kr.appl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.appl.dao.ApplDAO;
import kr.appl.vo.ApplVO;
import kr.controller.Action;

public class WriteAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		Integer status = (Integer)session.getAttribute("status");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		if(status != 1) {
			request.setAttribute("notice_msg", "일반회원만 지원할 수 있습니다.");
			request.setAttribute("notice_url", request.getContextPath()+"/main/main.do");
			return "common/alert_view.jsp";
		}
		
		
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		ApplDAO dao = ApplDAO.getInstance();
		ApplVO unchecked = dao.getMyUncheckedappl(user_num);
		if(unchecked != null) {
			request.setAttribute("notice_msg", "관리자가 미확인한 지원내역이 있습니다.");
			request.setAttribute("notice_url", request.getContextPath()+"/appl/detail.do?appl_num=" + unchecked.getAppl_num());
			return "common/alert_view.jsp";
		}
		
		//vo 생성 및 전송된 데이터 담기
		ApplVO appl =  new ApplVO();
		appl.setAppl_attachment(ApplFile.uploadFile(request, "appl_attachment"));
		appl.setCareer(Integer.parseInt(request.getParameter("career")));
		appl.setContent(request.getParameter("content"));
		appl.setField(Integer.parseInt(request.getParameter("field")));
		appl.setSource(request.getParameter("source"));
		appl.setAppl_center(Integer.parseInt(request.getParameter("appl_center")));
		appl.setUser_num(user_num);		
		
		//지원 신청
		dao.insertAppl(appl);	
		
		request.setAttribute("notice_msg", "지원 신청 완료");
		request.setAttribute("notice_url", 
				   request.getContextPath()+"/appl/listByUser.do");
		
		
		return "/common/alert_view.jsp";
	}
}















