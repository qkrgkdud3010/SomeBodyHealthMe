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

public class UpdateReplyAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//댓글 번호(히든으로 re_num을 넘겨준다)
		long re_num = Long.parseLong(request.getParameter("re_num"));
		//댓글 정보 읽어 오기
		BoardDAO dao = BoardDAO.getInstance();
		Board_replyVO db_reply = dao.getReplyBoard(re_num);//댓글 정보
		
		
		
		HttpSession session = request.getSession();
		Map<String, String> mapAjax = new HashMap<String, String>();
		Long user_num = (Long)session.getAttribute("user_num");		
		
		if(user_num == null) {//로그인 x
			mapAjax.put("result", "logout");			
		}else if (db_reply.getUser_num() == user_num && user_num != null){//로그인 o && 자신의 글
			Board_replyVO reply = new Board_replyVO();//자바빈 생성
			//데이터 반환
			String re_content = request.getParameter("re_content");
			reply.setRe_content(re_content);
			reply.setRe_num(re_num);
			//수정작업
			dao.UpdateReply(reply);				
			mapAjax.put("result", "success");		
		}else {//작성자 != 수정자
			mapAjax.put("result", "wrongAccess");
		}		
		//JSON 반환
		return StringUtil.parseJSON(request, mapAjax);			
	}
}














