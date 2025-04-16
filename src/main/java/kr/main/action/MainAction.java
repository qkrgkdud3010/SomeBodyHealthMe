package kr.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;

import kr.controller.Action;
import kr.goods.dao.GoodsDAO;
import kr.goods.vo.GoodsVO;
import kr.util.PagingUtil;

public class MainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//JSP 경로 반환
		HttpSession session = request.getSession();

		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null)pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		BoardDAO dao = BoardDAO.getInstance(); 
		int count = dao.getBoardCount(keyfield, keyword,null);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword, Integer.parseInt(pageNum),count, 2,2,"list.do");
		
		List<BoardVO> list = null;
		if(count > 0) {
			list = dao.getListBoard(page.getStartRow(), page.getEndRow(), keyfield,keyword,null);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		
		String pageNum1 =request.getParameter("pageNum");
		if(pageNum1 == null) pageNum1 ="1";
		
		String keyfield1 = request.getParameter("keyfield");
		String keyword1 = request.getParameter("keyword");
		
		GoodsDAO dao1 = GoodsDAO.getInstance();
		int count1 = dao1.getGoodsCount(keyfield1, keyword1, 0);
		
		//페이지 처리
		PagingUtil page1 = new PagingUtil(keyfield1, keyword1, Integer.parseInt(pageNum),count1,20,10,"list.do");
		
		List<GoodsVO> list1 = null;
		if(count > 0 ) {
			list1 = dao1.getListGoods(page1.getStartRow(), page1.getEndRow(), keyfield1, keyword1, 1);
		}
		
		request.setAttribute("count1", count1);
		request.setAttribute("list1", list1);
		request.setAttribute("page1", page1.getPage());
		return "main/main.jsp";
	}
}



