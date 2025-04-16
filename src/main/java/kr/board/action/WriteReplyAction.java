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

public class WriteReplyAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Map<String, String> mapAjax = new HashMap<String, String>();
		Long user_num = (Long)session.getAttribute("user_num");
		
		if(user_num == null) {//로그인 x
			mapAjax.put("result", "logout");
		}else {
			//전송된 데이터 인코딩처리
			request.setCharacterEncoding("utf-8");
			//자바빈 생성
			Board_replyVO reply = new Board_replyVO();
			reply.setBoard_num(Long.parseLong(request.getParameter("board_num")));//글번호(히든으로 넘김)
			reply.setUser_num(user_num);
			reply.setRe_content(request.getParameter("re_content"));
			
			BoardDAO dao = BoardDAO.getInstance();
			dao.insertReplyBoard(reply);
			mapAjax.put("result", "success");
		}
		//JSON 데이터로 변환
		return StringUtil.parseJSON(request, mapAjax);
	}
}

























