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

public class DeleteReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전송된 데이터 인코딩 타입 지정
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		long re_num = Long.parseLong(request.getParameter("re_num"));
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		GoodsDAO dao = GoodsDAO.getInstance();
		GoodsReviewVO db_reply = dao.getGoodsReview(re_num);
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num ==null ) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else if(user_num != null && user_num == db_reply.getUser_num() || user_num == 26) {
			//로그인한 회원번호와 작성자 회원번호가 일치
			dao.deleteGoodsReview(re_num);
			mapAjax.put("result", "success");
		}else {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			mapAjax.put("result", "wrongAccess");
		}
			
		return StringUtil.parseJSON(request, mapAjax);
	}

}
