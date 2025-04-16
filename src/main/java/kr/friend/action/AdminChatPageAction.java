package kr.friend.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.friend.vo.FriendVO;
import kr.util.PagingUtil;

public class AdminChatPageAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		HttpSession session = request.getSession();
		Long user_num = 
				(Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			response.setContentType("text/html; charset=UTF-8");
			 response.getWriter().write("<script>alert('로그인후 이용해주세요.');"
		             + "window.location.href='../member/loginForm.do';</script>");
					return null;
					
				}
		if(user_num != 35) {//로그인이 되지 않은 경우
			
			response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("<script>alert('접근이 불가능한 페이지입니다. 관리자만 접근할 수 있습니다.');"
                    + "window.location.href='../main/main.do';</script>");
            return null;  // 더 이상 후속 처리를 하지 않도록 함
			
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
				          20,10,"adminChatPage.do");
		List<FriendVO> list = null;
	
			list = dao.getReceivedMessages(user_num);	
		

		request.setAttribute("list", list);

		
		return "friendSearch/adminChatPage.jsp";
	}

}
