package kr.appl.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.appl.dao.ApplDAO;
import kr.appl.vo.ApplVO;
import kr.controller.Action;

public class UpdateAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}		
		//전송된 데이터 인코딩처리
		request.setCharacterEncoding("utf-8");
		//히든으로 넘긴 번호 반환
		long appl_num = Long.parseLong(request.getParameter("appl_num"));
		
		ApplDAO dao = ApplDAO.getInstance();
		ApplVO db_appl = dao.getAppl(appl_num);
		
		if(db_appl.getUser_num() != user_num) {//작성자 수정자 다른 경우
			return "common/notice.jsp";
		}
		if(db_appl.getAppl_status() != 0) {//관리자가 확인한 경우
			request.setAttribute("notice_msg", "관리자가 확인한 지원정보입니다.");
			request.setAttribute("notice_url", request.getContextPath()+"/appl/detail.do?appl_num="+appl_num);			
			return "common/alert_view.jsp";
		}
		//받은 값 담을 vo 생성
		ApplVO appl = new ApplVO();
		appl.setAppl_num(appl_num);
		appl.setAppl_attachment(ApplFile.uploadFile(request, "appl_attachment"));
		appl.setAppl_center(Integer.parseInt(request.getParameter("appl_center")));
		appl.setField(Integer.parseInt(request.getParameter("field")));
		appl.setCareer(Integer.parseInt(request.getParameter("career")));
		appl.setContent(request.getParameter("content"));
		appl.setSource(request.getParameter("source"));	
		//수정처리
		dao.updateAppl(appl);
		
		//첨부파일 변경시
		if(appl.getAppl_attachment() != null && !"".equals(appl.getAppl_attachment())) {
			//예전 파일이 있다면 삭제를 해준다.
			ApplFile.removeFile(request, db_appl.getAppl_attachment());
		}			
		return "redirect:/appl/detail.do?appl_num=" + appl_num;
	
	}
}

















