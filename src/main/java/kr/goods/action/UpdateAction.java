package kr.goods.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.goods.dao.GoodsDAO;
import kr.goods.vo.GoodsVO;
import kr.util.FileUtil;

public class UpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		Integer status = (Integer)session.getAttribute("status");
		if(status != 4) {//관리자로 로그인하지 않은 경우
			return "common/notice.jsp";
		}
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		long goods_num = Long.parseLong(
				request.getParameter("goods_num"));

		GoodsDAO dao = GoodsDAO.getInstance();
		GoodsVO db_goods = dao.getGoods(goods_num);

		GoodsVO goods = new GoodsVO();
		goods.setGoods_num(goods_num);
		goods.setGoods_name(request.getParameter("goods_name"));
		goods.setGoods_price(Integer.parseInt(request.getParameter("goods_price")));
		goods.setGoods_info(request.getParameter("goods_info"));
		goods.setGoods_category(request.getParameter("goods_category"));
		goods.setGoods_img1(FileUtil.uploadFile(request, "goods_img1"));
		goods.setGoods_img2(FileUtil.uploadFile(request, "goods_img2"));
		goods.setGoods_quantity(Integer.parseInt(request.getParameter("goods_quantity")));
		goods.setGoods_status(Integer.parseInt(request.getParameter("goods_status")));

		dao.updateGoods(goods);

		if(goods.getGoods_img1()!=null 
				&& !"".equals(goods.getGoods_img1())) {
			//새 파일로 교체할 때 원래 파일 제거
			FileUtil.removeFile(request, db_goods.getGoods_img1());
		}
		if(goods.getGoods_img2()!=null 
				&& !"".equals(goods.getGoods_img2())) {
			//새 파일로 교체할 때 원래 파일 제거
			FileUtil.removeFile(request, db_goods.getGoods_img2());
		}
		
		
		request.setAttribute("notice_msg", "정상적으로 수정되었습니다.");
		request.setAttribute("notice_url", 
				request.getContextPath()+"/goods/updateForm.do?goods_num="+goods_num);

		return "common/alert_view.jsp";
	}

}



