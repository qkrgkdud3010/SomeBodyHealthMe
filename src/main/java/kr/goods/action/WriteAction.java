package kr.goods.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.goods.dao.GoodsDAO;
import kr.goods.vo.GoodsVO;
import kr.util.FileUtil;

public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num =(Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		Integer status = (Integer)session.getAttribute("status");
		if(status != 4) {//관리자로 로그인하지 않은 경우
			return "common/notice.jsp";
		}

		//로그인된 경우
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//자바빈(VO) 생성
		GoodsVO goods = new GoodsVO();
		goods.setGoods_name(request.getParameter("goods_name"));
		goods.setGoods_price(Integer.parseInt(request.getParameter("goods_price")));
		goods.setGoods_info(request.getParameter("goods_info"));
		goods.setGoods_category(request.getParameter("goods_category"));
		goods.setGoods_img1(FileUtil.uploadFile(request, "goods_img1"));
		goods.setGoods_img2(FileUtil.uploadFile(request, "goods_img2"));
		goods.setGoods_quantity(Integer.parseInt(request.getParameter("goods_quantity")));
		goods.setGoods_status(Integer.parseInt(request.getParameter("goods_status")));
		
		GoodsDAO dao = GoodsDAO.getInstance();
		dao.insertGoods(goods);
		
		request.setAttribute("notice_msg", "글쓰기 완료!");
		request.setAttribute("notice_url", request.getContextPath()+"/goods/adminlist.do");
		
		return "common/alert_view.jsp";
	}

}
