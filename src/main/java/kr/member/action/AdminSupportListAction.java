package kr.member.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.AdminDAO;
import kr.appl.vo.ApplVO;

public class AdminSupportListAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Integer status = (Integer) session.getAttribute("status");

        // 권한 체크
        if (status == null || status != 4) {
            request.setAttribute("notice_msg", "잘못된 접근입니다.");
            request.setAttribute("notice_url", request.getContextPath() + "/main/main.do");
            return "/common/alert_view.jsp";
        }

        // 최근 지원 신청 목록 가져오기
        AdminDAO dao = AdminDAO.getInstance();
        List<ApplVO> recentApplications = dao.getRecentApplications();

        // 데이터 전달
        request.setAttribute("recentApplications", recentApplications);

        // JSP 반환
        return "/member/adminPage.jsp";
    }
}
