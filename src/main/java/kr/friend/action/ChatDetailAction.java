package kr.friend.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import kr.controller.Action;
import kr.friend.vo.FriendVO;
import kr.util.PagingUtil;

public class ChatDetailAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		HttpSession session = request.getSession();
		Long user_num = 
				(Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		ChatOneDAO dao = ChatOneDAO.getInstance();
		int count = 1;
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,
				          Integer.parseInt(pageNum),count,
				          20,10,"chatDetail.do");
		List<FriendVO> list = null;
	
			list = dao.getChatList(page.getStartRow(),
					               page.getEndRow(),user_num);	
		

		request.setAttribute("list", list);

		

		
		return "friendSearch/chatDetail.jsp";
	}

}
