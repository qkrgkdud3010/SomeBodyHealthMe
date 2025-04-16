package kr.friend.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.friend.dao.FriendDAO;
import kr.friend.vo.FriendVO;

public class ReciverListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		List<FriendVO> list = null;
		FriendDAO dao = FriendDAO.getInstance();
		list=dao.updateGetMember(user_num);
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		request.setAttribute("list", list);
		return "/friendSearch/reciverList.jsp";
	}

}
