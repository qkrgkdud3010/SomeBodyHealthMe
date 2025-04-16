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

public class WriteLikeAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> mapAjax = 
				              new HashMap<String,Object>();
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인이 된 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			//전송된 데이터 반환
			long goods_num = Long.parseLong(
					    request.getParameter("goods_num"));
			
			GoodsLikeVO likeVO = new GoodsLikeVO();
			likeVO.setGoods_num(goods_num);
			likeVO.setUser_num(user_num);
			
			GoodsDAO dao = GoodsDAO.getInstance();
			//좋아요 등록 여부 체크
			GoodsLikeVO db_like = dao.selectLike(likeVO);
			if(db_like!=null) {//좋아요 등록 O
				//좋아요 삭제
				dao.deleteLike(db_like);
				mapAjax.put("status", "noLike");
			}else {//좋아요 등록 X
				//좋아요 등록
				dao.insertLike(likeVO);
				mapAjax.put("status", "yesLike");
			}
			mapAjax.put("result", "success");
			mapAjax.put("count", dao.selectLikeCount(goods_num));
		}
		//JSON 데이터로 변환
		return StringUtil.parseJSON(request, mapAjax);
	}

}


