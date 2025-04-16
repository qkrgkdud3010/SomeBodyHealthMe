package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
import kr.util.StringUtil;

public class DetailAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//상세페이지 글 번호
		Long board_num = Long.parseLong(request.getParameter("board_num"));
		
		BoardDAO dao = BoardDAO.getInstance();
		
		dao.updateReadCount(board_num);//조회수 증가
		BoardVO board = dao.getBoard(board_num);//데이터 가져오기
		//제목 및 내용 html 처리
		board.setBoard_title(StringUtil.useNoHtml(board.getBoard_title()));
		board.setBoard_content(StringUtil.useBrNoHtml(board.getBoard_content()));
		
		//댓글 폼 프로필 사진 처리
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num != null) {
			MemberDAO memberdao = MemberDAO.getInstance();
			MemberVO member = memberdao.getUserProfile(user_num);
			request.setAttribute("member", member);
			System.out.println("aa = "+member.getPhoto());
		}	
		 
		request.setAttribute("board", board);
		
		String cate = request.getParameter("board_category");
		if(cate != null && !"".equals(cate)) {
			request.setAttribute("cate", cate);
		}	
		
		return "/board/detail.jsp";
	}
}
