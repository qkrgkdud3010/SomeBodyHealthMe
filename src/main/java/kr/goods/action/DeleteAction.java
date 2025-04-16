package kr.goods.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.goods.dao.GoodsDAO;
import kr.goods.vo.GoodsVO;
import kr.util.FileUtil;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인이 된 경우
		Integer status = (Integer)session.getAttribute("status");
		if(status != 4) {//관리자로 로그인하지 않은 경우
			return "common/notice.jsp";
		}
		long goods_num = Long.parseLong(
	             request.getParameter("goods_num"));
		GoodsDAO dao = GoodsDAO.getInstance();
		GoodsVO db_goods = dao.getGoods(goods_num);
		
		
		dao.deleteGoods(goods_num);
		//파일 삭제
		FileUtil.removeFile(request, db_goods.getGoods_img1());
		FileUtil.removeFile(request, db_goods.getGoods_img2());
		
		request.setAttribute("notice_msg", "글 삭제 완료!");
		request.setAttribute("notice_url", 
				 request.getContextPath()+"/goods/adminlist.do");		
		
		return "common/alert_view.jsp";
	}

}




