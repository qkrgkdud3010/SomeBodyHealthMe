package kr.friend.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.friend.dao.FriendDAO;
import kr.friend.vo.FriendVO;
import kr.util.StringUtil;

public class SulagAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		  Map<String, Object> mapAjax = new HashMap<String, Object>();
		  
		HttpSession session = request.getSession();
		Long receiver   = (Long)session.getAttribute("user_num");
		  Long user_num = Long.parseLong(request.getParameter("receiverNum"));

		    FriendDAO dao = FriendDAO.getInstance();
		    String isRequestSent = dao.sulag(user_num, receiver);

		    // 결과를 mapAjax에 저장
		    mapAjax.put("isRequestSent", isRequestSent);

		    // JSON 응답으로 반환
		    return StringUtil.parseJSON(request, mapAjax);
		
		
		
	
	}

}
