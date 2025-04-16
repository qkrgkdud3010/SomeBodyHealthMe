package kr.entry.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.entry.dao.EntryDAO;
import kr.entry.vo.EntryVO;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class EntryLogsListAction implements Action {
    private static final int ROWS_PER_PAGE = 10;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");

        // 로그인 여부 확인
        if (user_num == null) {
            return "redirect:/member/loginForm.do";
        }

        MemberDAO memberDAO = MemberDAO.getInstance();
        MemberVO member = memberDAO.getUserProfile(user_num);

        // 관리자 권한 확인
        if (member == null || member.getStatus() != 4) {
            request.setAttribute("notice_msg", "잘못된 접근입니다.");
            request.setAttribute("notice_url", request.getContextPath() + "/main/main.do");
            return "common/alert_view.jsp";
        }

        // 현재 페이지 번호 가져오기
        String pageStr = request.getParameter("page");
        int page = (pageStr != null) ? Integer.parseInt(pageStr) : 1;

        EntryDAO entryDAO = EntryDAO.getInstance();

        // 페이징 데이터 가져오기
        int totalCount = entryDAO.getEntryCount(); // 총 데이터 개수
        int totalPages = (int) Math.ceil(totalCount / (double) ROWS_PER_PAGE);

        List<EntryVO> entryLogs = entryDAO.getEntryLogsByPage(page, ROWS_PER_PAGE);

        // 데이터 전달
        request.setAttribute("entryLogs", entryLogs);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);

        return "member/entryLogsList.jsp";
    }
}
