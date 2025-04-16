package kr.appl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.appl.dao.ApplDAO;
import kr.appl.vo.ApplVO;
import kr.controller.Action;

public class UpdateStatusAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		Integer status = (Integer)session.getAttribute("status");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		if(status != 4) {//관리자가 아닌 경우
			return "/common/notice.jsp";
		}
		
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		int appl_status = Integer.parseInt(request.getParameter("field"));//지원분야 (member의 status를)
		int center_num = Integer.parseInt(request.getParameter("appl_center"));//지원 지점
		long appl_num = Long.parseLong(request.getParameter("appl_num"));//지원 번호
		int career = Integer.parseInt(request.getParameter("career"));//경력 유무
		
		//유효성 체크(member.status ==1 )
		ApplDAO dao = ApplDAO.getInstance();
		ApplVO db_suser = dao.getAppl(appl_num);
		
		if(db_suser.getStatus() != 1) {
			request.setAttribute("notice_msg", "일반회원이 아닌 경우 전환이 불가능 합니다.");
			request.setAttribute("notice_url", request.getContextPath()+"/appl/listByAdmin.do");
			return "/common/alert_view.jsp";
		}
		
		//최종관리자가 체크한 내용
		ApplVO appl = new ApplVO();
		appl.setAppl_num(appl_num);
		appl.setAppl_center(center_num);
		appl.setField(appl_status);	
		appl.setCareer(career);
		appl.setUser_num(db_suser.getUser_num());//suser,suser_detail 테이블 변경용
		
		//관리자 전환(appl_status 3으로 전환)
		dao.updateStatus(appl);
		
		request.setAttribute("notice_msg", "관리자로 전환되었습니다.");
		request.setAttribute("notice_url", request.getContextPath()+"/appl/listByAdmin.do");
		return "/common/alert_view.jsp";
	}
}



















