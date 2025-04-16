package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import kr.controller.Action;
import kr.member.dao.AdminDAO;
import kr.order.vo.OrderVO;

public class AdminRecentOrdersAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Integer status = (Integer) session.getAttribute("status");

        if (status == null || status != 4) { // 관리자 로그인 확인
            return "redirect:/member/loginForm.do";
        }

        AdminDAO dao = AdminDAO.getInstance();
        List<OrderVO> recentOrders = dao.getRecentOrdersForAdmin();

        // 데이터를 request 객체에 설정
        request.setAttribute("recentOrders", recentOrders);

        // 관리자 페이지로 이동
        return "member/adminPage.jsp";
    }
}
