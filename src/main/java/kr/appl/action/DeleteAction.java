package kr.appl.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.appl.dao.ApplDAO;
import kr.appl.vo.ApplVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class DeleteAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		
		if(user_num == null) {//로그인이 안된 경우
			mapAjax.put("result", "logout");
		}else {//자신의 지원정보가 아닌 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			long appl_num = Long.parseLong(request.getParameter("appl_num"));
			
			ApplDAO dao = ApplDAO.getInstance();
			ApplVO db_appl = dao.getAppl(appl_num);

			//회원번호 일치 여부 체크
			if(user_num != db_appl.getUser_num()) {
				mapAjax.put("result", "wrongAccess");
			}else {				
				dao.deleteAttachment(appl_num);//파일 삭제(db에 저장된 파일명 삭제)
				//파일 삭제
				ApplFile.removeFile(request, db_appl.getAppl_attachment());
				mapAjax.put("result", "success");
			}			
		}
		
		return StringUtil.parseJSON(request, mapAjax);	

	}
}

























