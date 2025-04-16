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

public class UpdateReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 타입 지정
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		//댓글번호
		long re_num = Long.parseLong(request.getParameter("re_num"));
		
		GoodsDAO dao = GoodsDAO.getInstance();
		GoodsReviewVO db_reply = dao.getGoodsReview(re_num);
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		if (user_num == null) { // 로그인이 되지 않은 경우
            mapAjax.put("result", "logout");
        } else if(user_num!=null && user_num == db_reply.getUser_num()) {
        	//로그인한 회원번호와 작성자 회원번호 일치  자바빈(vO) 생성
        	GoodsReviewVO reply = new GoodsReviewVO();
        	reply.setRe_num(re_num);
        	reply.setRe_content(request.getParameter("re_content"));
        	reply.setRe_rating(Integer.parseInt(request.getParameter("re_rating")));
        	
        	//댓글수정
            dao.updateGoodsReview(reply);
            mapAjax.put("result", "success");
        } else {//로그인한 회원번호와 작성자 회원번호 불일치
            mapAjax.put("result", "wrongAccess");
        }
        return StringUtil.parseJSON(request, mapAjax);
    }
	

}
