package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;

public class DeactivateUserAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Long userNum = (Long) session.getAttribute("user_num");

        if (userNum == null) {
            return "redirect:/member/loginForm.do";
        }

        MemberDAO dao = MemberDAO.getInstance();
        dao.deactivateUser(userNum);

        session.invalidate(); // 세션 초기화
        request.setAttribute("notice_msg", "회원 탈퇴가 완료되었습니다.");
        request.setAttribute("notice_url", request.getContextPath() + "/main/main.do");

        return "common/alert_view.jsp";
    }
}