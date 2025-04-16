package kr.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.member.dao.AdminDAO;
import kr.membership.vo.MembershipVO;

public class MembershipListAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AdminDAO adminDAO = AdminDAO.getInstance();
        List<MembershipVO> memberships = adminDAO.getAllMemberships();

        request.setAttribute("memberships", memberships);

        // JSP 경로 수정: member 하위로 변경
        return "member/membershipList.jsp";
    }
}
