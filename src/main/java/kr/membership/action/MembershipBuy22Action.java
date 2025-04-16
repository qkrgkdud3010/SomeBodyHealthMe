package kr.membership.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.friend.action.ChatOneDAO;
import kr.membership.dao.MembershipDAO;
import kr.util.StringUtil;

public class MembershipBuy22Action implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = 
				new HashMap<String,String>();
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		MembershipDAO dao = MembershipDAO.getInstance();
		int typeId = Integer.parseInt(request.getParameter("typeId"));

		String result=dao.buyMembershipByType(user_num,typeId);
		   mapAjax.put("isRequestSent", result);
		return StringUtil.parseJSON(request, mapAjax);
	}

}
