package kr.board.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.Board_replyVO;
import kr.controller.Action;
import kr.util.PagingUtil;
import kr.util.StringUtil;

public class ListReplyAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리 
		request.setCharacterEncoding("utf-8");
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum="1";
		String rowCount = request.getParameter("rowCount");
		if(rowCount==null) rowCount="10";
		
		long board_num = Long.parseLong(request.getParameter("board_num"));
		
		BoardDAO dao = BoardDAO.getInstance();
		int count = dao.getReplyCount(board_num);
		
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, Integer.parseInt(rowCount));
		
		//댓글 목록반환
		List<Board_replyVO> list = null;
		if(count > 0) {
			list = dao.getReply(page.getStartRow(), page.getEndRow(), board_num);			
		}else {
			list = Collections.emptyList();
		}
		
		for(int i = 0;i < list.size(); i++) {
			String photo = list.get(i).getPhoto(); 
			if(photo == null || "".equals(photo) || photo.equals("default_user_photo.png")) {
				list.get(i).setPhoto("User.png");
			}
		}
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		//관리자 삭제처리 기능
		Integer status = (Integer)session.getAttribute("status");
		
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		
		mapAjax.put("count", count);
		mapAjax.put("list", list);
		
		
		mapAjax.put("status", status);
		
		mapAjax.put("user_num", user_num);		
		return StringUtil.parseJSON(request, mapAjax);
	}
}



















