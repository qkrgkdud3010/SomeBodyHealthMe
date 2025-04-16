package kr.member.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import kr.controller.Action;
import kr.membership.dao.MembershipDAO;
import kr.membership.vo.MembershipVO;

public class MyMembershipListAction implements Action {
    private static final int PAGE_SIZE = 10;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");

        if (user_num == null) {
            return "redirect:/member/loginForm.do";
        }

        // 현재 페이지 번호
        String pageParam = request.getParameter("page");
        int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;

        // DAO 호출
        MembershipDAO membershipDAO = MembershipDAO.getInstance();
        int startRow = (currentPage - 1) * PAGE_SIZE;
        List<MembershipVO> memberships = membershipDAO.getMembershipsByUser(user_num, startRow, PAGE_SIZE);

        // 전체 페이지 계산
        int totalMemberships = memberships.size(); // 임시: 추후 DB로 교체 필요
        int totalPages = (int) Math.ceil((double) totalMemberships / PAGE_SIZE);

        // JSP로 데이터 전달
        request.setAttribute("memberships", memberships);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        return "member/myMembershipList.jsp";
    }
}
