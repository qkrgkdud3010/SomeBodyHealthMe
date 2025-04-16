package kr.goods.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import kr.controller.Action;
import kr.goods.dao.GoodsDAO;
import kr.goods.vo.GoodsLikeVO;
import kr.util.StringUtil;

public class GetLikeAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		long goods_num = Long.parseLong(request.getParameter("goods_num"));
		
		Map<String,Object> mapAjax = 
				             new HashMap<String,Object>();
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		GoodsDAO dao = GoodsDAO.getInstance();
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("status", "noLike");
		}else {//로그인이 된 경우
			GoodsLikeVO goodsLike = 
					dao.selectLike(new GoodsLikeVO(goods_num,user_num));
			if(goodsLike!=null) {
				//로그인한 회원이 좋아요 클릭
				mapAjax.put("status", "yesLike");
			}else {
				//로그인한 회원이 좋아요 미클릭
				mapAjax.put("status", "noLike");
			}
		}
		//좋아요 개수
		mapAjax.put("count", dao.selectLikeCount(goods_num));
		
		//JSON 데이터로 변환
		return StringUtil.parseJSON(request, mapAjax);
	}

}





