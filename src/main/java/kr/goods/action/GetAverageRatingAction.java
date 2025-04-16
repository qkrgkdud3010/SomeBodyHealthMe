package kr.goods.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.goods.dao.GoodsDAO;
import kr.util.StringUtil;

public class GetAverageRatingAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long goods_num = Long.parseLong(request.getParameter("goods_num"));
        
        GoodsDAO dao = GoodsDAO.getInstance();
        double avg_rating = dao.getAverageRating(goods_num);
        
        Map<String, Object> mapAjax = new HashMap<String, Object>();
        mapAjax.put("result", "success");
		
		
		
		mapAjax.put("avg_rating", avg_rating);
		return StringUtil.parseJSON(request, mapAjax);
	}

}
