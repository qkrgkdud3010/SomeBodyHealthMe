package kr.entry.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.entry.dao.EntryUserDAO;
import kr.entry.vo.EntryVO;

public class MyEntryLogsListAction implements Action {

    private static final int ROWS_PER_PAGE = 10; // 한 페이지당 표시할 행 수

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");

        if (user_num == null) {
            return "redirect:/member/loginForm.do";
        }

        // 현재 페이지 번호 가져오기
        String pageParam = request.getParameter("page");
        int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;

        // DAO 호출
        EntryUserDAO entryDAO = EntryUserDAO.getInstance();
        int totalEntries = entryDAO.getEntryCountByUser(user_num); // 해당 사용자의 총 출입 기록 수
        int totalPages = (int) Math.ceil((double) totalEntries / ROWS_PER_PAGE);
        int startRow = (currentPage - 1) * ROWS_PER_PAGE;

        List<EntryVO> entryLogs = entryDAO.getEntryLogsByUser(user_num, startRow, ROWS_PER_PAGE);

        // JSP로 데이터 전달
        request.setAttribute("entryLogs", entryLogs);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        return "member/myEntryLogsList.jsp";
    }
}
