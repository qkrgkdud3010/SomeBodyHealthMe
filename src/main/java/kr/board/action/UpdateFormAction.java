package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class UpdateFormAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		
		if(user_num == null) {//비로그인
			return "redirect:member/loginForm.do";
		}
		//로그인
		//전송된 데이터 인코딩
		request.setCharacterEncoding("utf-8"); 
		long board_num = Long.parseLong(request.getParameter("board_num"));
		
		BoardDAO dao = BoardDAO.getInstance();
		
		BoardVO board = dao.getBoard(board_num);//글번호로 vo추출
		if(board.getUser_num() != user_num) {//작성자 != 수정자
			return "common/notice.jsp";
		}
		//**해야할것 큰 따옴표 처리
		board.setBoard_title(StringUtil.parseQuot(board.getBoard_title()));
		
		request.setAttribute("board", board);
		
		
		return "board/updateForm.jsp";
	}
}























