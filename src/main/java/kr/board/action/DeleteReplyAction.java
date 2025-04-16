package kr.board.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.Board_replyVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class DeleteReplyAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		Integer status = (Integer)session.getAttribute("status");
		
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		long re_num = Long.parseLong(request.getParameter("re_num"));
		System.out.println("re_num = " + re_num);
		BoardDAO dao = BoardDAO.getInstance();
		Board_replyVO db_reply = dao.getReplyBoard(re_num);
		System.out.println("1번자리");
		if(user_num == null) {
			mapAjax.put("result", "logout");
		}else if(status == 4 || db_reply.getUser_num() == user_num) {//최고관리자만 삭제 가능?
			//삭제 작업(로그인 && (작성자 == 삭제자 || 관리자))
			dao.deleteReply(re_num);
			mapAjax.put("result", "success");
		}else {//작성자 != 삭제자
			mapAjax.put("result", "wrongAccess");
		}	
		//JSON 변환
		return StringUtil.parseJSON(request, mapAjax);
	}
}














