package kr.goods.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.goods.dao.GoodsDAO;
import kr.goods.vo.GoodsReviewVO;
import kr.util.StringUtil;

public class WriteReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인이 된 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			GoodsReviewVO reply = new GoodsReviewVO();
			reply.setUser_num(user_num);
			reply.setRe_content(request.getParameter("re_content"));
			reply.setRe_rating(Integer.parseInt(request.getParameter("re_rating")));
			reply.setRe_ip(request.getRemoteAddr());
			reply.setGoods_num(Long.parseLong(request.getParameter("goods_num")));//댓글의 부모 글 번호
			
			GoodsDAO dao = GoodsDAO.getInstance();
			dao.insertGoodsReview(reply);
			
			mapAjax.put("result", "success");
		}
		//json  데이터로 반환
		return StringUtil.parseJSON(request, mapAjax);
	}

}
