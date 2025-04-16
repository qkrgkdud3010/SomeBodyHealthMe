package kr.friend.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.util.StringUtil;

public class ChatMessageListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String,Object> mapAjax = 
		           new HashMap<String,Object>();
HttpSession session = request.getSession();
Long user_num = 
		 (Long)session.getAttribute("user_num");
if(user_num==null) {//로그인이 되지 않은 경우
	mapAjax.put("result", "logout");
}else {//로그인이 된 경우
	//전송된 데이터 인코딩 처리
	request.setCharacterEncoding("utf-8");
	String recv_num = request.getParameter("recv_num");
	if(recv_num == null || recv_num.isEmpty()) {
		recv_num="4"; //관리자와 채팅
	}
	
	ChatOneDAO dao = ChatOneDAO.getInstance();
	List<ChatOneVO> list = 
			dao.getChatDetail(user_num,
					    Long.parseLong(recv_num));
	if(list == null) list = Collections.emptyList();
	
	mapAjax.put("result", "success");
	mapAjax.put("list", list);
}		
//JSON 문자열 반환
return StringUtil.parseJSON(request, mapAjax);
	}

}
