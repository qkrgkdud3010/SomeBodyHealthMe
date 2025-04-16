package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.member.dao.MemberDAO;

public class FindAccountAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        MemberDAO dao = MemberDAO.getInstance();
        String foundAccount = dao.findAccountByNameAndEmail(name, email);

        if (foundAccount != null) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("<script>");
            response.getWriter().write("alert('당신의 아이디는 " + foundAccount + " 입니다');");
            response.getWriter().write("location.href='" + request.getContextPath() + "/member/findAccountForm.do';");
            response.getWriter().write("</script>");
            return null;
        } else {
            request.setAttribute("errorMessage", "입력하신 정보로 아이디를 찾을 수 없습니다.");
            return "/member/findAccountForm.jsp";
        }
    }
}