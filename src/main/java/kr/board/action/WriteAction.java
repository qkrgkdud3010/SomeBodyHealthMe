package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.FileUtil;
import kr.util.StringUtil;

public class WriteAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		Integer status = (Integer)session.getAttribute("status");
		
		if(user_num == null) {//로그인인 되지 않은 경우
			return "redirect:/member/loginForm.do";		
		}
		
		//로그인이 된 경우
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8"); 
		
		int board_category = Integer.parseInt(request.getParameter("board_category"));
		
		if(board_category == 1 && status != 4) {
			return "common/notice.jsp";
		}
		
		//VO생성
		BoardVO board = new BoardVO();
		board.setBoard_category(board_category);
		board.setBoard_title(request.getParameter("board_title"));
		board.setBoard_attachment(FileUtil.uploadFile(request, "board_attachment"));
		board.setBoard_content(request.getParameter("board_content"));
		board.setUser_num(user_num);
		
		BoardDAO dao = BoardDAO.getInstance();
		dao.insertBoard(board);		
		
		request.setAttribute("notice_msg", "글쓰기 완료!");
		request.setAttribute("notice_url", 
				   request.getContextPath()+"/board/list.do?board_category="+board_category);
		
		return "common/alert_view.jsp";
	}
}






















