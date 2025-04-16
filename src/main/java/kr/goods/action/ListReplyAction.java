package kr.goods.action;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.goods.dao.GoodsDAO;
import kr.goods.vo.GoodsReviewVO;
import kr.order.dao.OrderDAO;
import kr.util.PagingUtil;
import kr.util.StringUtil;

public class ListReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String pageNum =request.getParameter("pageNum");
		if(pageNum == null) pageNum ="1";
		
		String rowCount = request.getParameter("rowCount");
		if(rowCount==null) rowCount ="10";
		
		long goods_num = Long.parseLong(request.getParameter("goods_num"));
		
		GoodsDAO dao = GoodsDAO.getInstance();
		OrderDAO odao = OrderDAO.getInstance();
		int count = dao.getGoodsReviewCount(goods_num);
		
		//currentPage, count, rowCount
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, Integer.parseInt(rowCount));
		
		List<GoodsReviewVO> list = null;
		
		if(count>0) {
			list = dao.getListGoodsReview(page.getStartRow(),page.getEndRow(),goods_num);
		}else {
			list = Collections.emptyList();
		}
		
		HttpSession session = request.getSession();
		Integer status = (Integer)session.getAttribute("status");
		Long user_num = (Long)session.getAttribute("user_num");
		
		boolean isReviewed = dao.checkReview(user_num, goods_num);
		boolean checkBuy = odao.checkBuyGoods(user_num, goods_num);
		
		
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("count", count);
		mapAjax.put("list", list);
		//로그인한 사람이 작성자인지 체크하기 위해서 로그인한 회원번호를 전송
		mapAjax.put("user_num", user_num);
		mapAjax.put("isReviewed", isReviewed);
		mapAjax.put("checkBuy", checkBuy);
		mapAjax.put("status", status);
		
		return StringUtil.parseJSON(request, mapAjax);
	}

}
