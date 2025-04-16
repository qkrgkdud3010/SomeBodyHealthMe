package kr.friend.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.friend.dao.FriendDAO;
import kr.friend.vo.FriendVO;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
import kr.util.PagingUtil;


public class FriendList2Action implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		List<FriendVO> list = null;
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		int center_num=Integer.parseInt(request.getParameter("center_num"));
		FriendDAO dao = FriendDAO.getInstance();
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		int count = dao.getMemberCountByAdmin(keyfield, keyword);
		
		PagingUtil page = new PagingUtil(keyfield,keyword,
			    Integer.parseInt(pageNum),count,20,10,
			                         "friendList.do");

	if(count > 0) {
		list = dao.centerGetMember(
				       page.getStartRow(),
				       page.getEndRow(),
				       keyfield,keyword,center_num,user_num);
	}
		
		
		

		request.setAttribute("list", list);
		return "friendSearch/friendList2.jsp";
	}

}
