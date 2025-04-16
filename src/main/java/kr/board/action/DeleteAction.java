package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class DeleteAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		Integer status = (Integer)session.getAttribute("status");
		
		if(user_num == null) {//비로그인
			return "redirect:member/loginForm.do";
		}
		//로그인시 
		//전송된 데이터 인코딩
		request.setCharacterEncoding("utf-8");
		//글번호 반환
		long board_num = Long.parseLong(request.getParameter("board_num"));
		
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO db_board = dao.getBoard(board_num);
		
		//등록자 삭제자 동일인 체크
		if(db_board.getUser_num() != user_num && status !=4 ) {
			return "common/notice.jsp";
		}
		
		//게시글 삭제				
		dao.deleteBoard(board_num);
		//파일삭제
		FileUtil.removeFile(request, db_board.getBoard_attachment());
		
		request.setAttribute("notice_msg", "글 삭제 완료");
		request.setAttribute("notice_url", request.getContextPath() + "/board/list.do");
		
		
		return "common/alert_view.jsp";
	}
}
