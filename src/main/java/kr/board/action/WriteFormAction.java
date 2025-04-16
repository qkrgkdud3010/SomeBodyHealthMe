package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class WriteFormAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		String board_category = request.getParameter("board_category");
		if(board_category != null && !"".equals(board_category)) request.setAttribute("cate", board_category);
		
		if(user_num == null) {//비로그인
			return "redirect:/member/loginForm.do";
		}		
		return "board/writeForm.jsp";
	}
}
