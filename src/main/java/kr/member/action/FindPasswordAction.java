package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.member.dao.MemberDAO;

public class FindPasswordAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String loginId = request.getParameter("login_id");
        String email = request.getParameter("email");

        MemberDAO dao = MemberDAO.getInstance();
        String foundPassword = dao.findPasswordByLoginIdAndEmail(loginId, email);

        if (foundPassword != null) {
            request.setAttribute("foundPassword", foundPassword);
        } else {
            request.setAttribute("errorMessage", "입력한 정보와 일치하는 계정을 찾을 수 없습니다.");
        }

        return "/member/findPasswordForm.jsp";
    }
}