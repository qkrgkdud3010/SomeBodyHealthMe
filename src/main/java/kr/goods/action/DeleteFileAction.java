package kr.goods.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.goods.dao.GoodsDAO;
import kr.goods.vo.GoodsVO;
import kr.util.FileUtil;
import kr.util.StringUtil;

public class DeleteFileAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = 
				           new HashMap<String,String>();
		
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
			
			GoodsDAO dao = GoodsDAO.getInstance();
			GoodsVO db_goods = dao.getGoods(goods_num);
			//로그인한 회원번호와 작성자 회원번호 일치 여부 체크
			Integer status = (Integer)session.getAttribute("status");
			if(status != 4) {//관리자로 로그인하지 않은 경우
				mapAjax.put("result", "wrongAccess");
			}else {
				dao.deleteGoods(goods_num);
				//파일 삭제
				FileUtil.removeFile(request, db_goods.getGoods_img1());
				FileUtil.removeFile(request, db_goods.getGoods_img2());
				mapAjax.put("result", "success");
			}
			
		}
		//JSON 데이터로 변환
		return StringUtil.parseJSON(request, mapAjax);
	}

}



