package kr.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.PagingUtil;

public class ListAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null)pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		String board_category = request.getParameter("board_category");
		
		BoardDAO dao = BoardDAO.getInstance(); 
		int count = dao.getBoardCount(keyfield, keyword, board_category);
		
		String addKey = "";
		if(board_category != null) addKey += "&board_category=" + board_category;
		
		
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword, Integer.parseInt(pageNum),count, 15,10,"list.do",addKey);
		
		List<BoardVO> list = null;
		if(count > 0) {
			list = dao.getListBoard(page.getStartRow(), page.getEndRow(), keyfield, keyword, board_category);
		}
		
		request.setAttribute("cate", board_category);
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		
		
		//JSP 경로 반환/
		return "board/list.jsp";
	}
}
