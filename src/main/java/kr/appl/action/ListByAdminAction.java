package kr.appl.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.appl.dao.ApplDAO;
import kr.appl.vo.ApplVO;
import kr.controller.Action;
import kr.util.PagingUtil;

public class ListByAdminAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		Integer status = (Integer)session.getAttribute("status");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		if(status != 4) {//관리자가 아닌 경우
			return "/common/notice.jsp";
		}
		
		//페이지 처리
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null)pageNum = "1";
		
		
		String name = request.getParameter("name");		
		String appl_status=request.getParameter("appl_status");
		String field = request.getParameter("field");
		String career = request.getParameter("career");
		String appl_center = request.getParameter("appl_center");
		
		ArrayList<String> keys = new ArrayList<String>();
		keys.add("appl_status");
		keys.add(appl_status);
		keys.add("field");
		keys.add(field);
		keys.add("career");
		keys.add(career);
		keys.add("appl_center");
		keys.add(appl_center);
		
		String addKey = "";
		if(name != null) addKey += "&name=" + name;//질문
		for(int i = 1; i<keys.size(); i+=2) {
			if(keys.get(i) != null) addKey += "&" + keys.get(i-1) + "=" + keys.get(i);
		}
		
		System.out.println("addKey = "  + addKey);
			
		ApplDAO dao = ApplDAO.getInstance();
		int count = dao.getApplicationCount(name, keys);
		System.out.println("count = " + count);
		
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum) , count, 10, 10, "listByAdmin.do",addKey);
		System.out.println("endrow = " + page.getEndRow());
		
		//목록 담기
		List<ApplVO> list = null;
		if(count > 0) {
			list = dao.getListByAdmin(page.getStartRow(),page.getEndRow(),name, keys);
		}

		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());		
		
		//JSP 경로 반환/
		return "appl/listByAdmin.jsp";
	}
}






















