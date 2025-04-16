package kr.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.member.dao.AdminDAO;
import kr.membership.vo.MembershipVO;

public class AdminMembershipListAction implements Action {
    private static final int PAGE_SIZE = 10; // 한 페이지당 표시할 회원권 수

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AdminDAO adminDAO = AdminDAO.getInstance();

        // 현재 페이지 번호 가져오기 (기본값: 1)
        String pageParam = request.getParameter("page");
        int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;

        // DAO에서 회원권 목록과 전체 회원권 개수 가져오기
        int totalMemberships = adminDAO.getMembershipCount();
        int startRow = (currentPage - 1) * PAGE_SIZE;
        List<MembershipVO> memberships = adminDAO.getMembershipsByPage(startRow, PAGE_SIZE);

        // 전체 페이지 계산
        int totalPages = (int) Math.ceil((double) totalMemberships / PAGE_SIZE);

        // JSP에 전달할 데이터 설정
        request.setAttribute("memberships", memberships);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        return "member/membershipList.jsp";
    }
}
