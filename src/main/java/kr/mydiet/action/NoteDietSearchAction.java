package kr.mydiet.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.mydiet.dao.DietPlanDAO;
import kr.mydiet.vo.DietPlanVO;
import kr.util.PagingUtil;

public class NoteDietSearchAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	request.setCharacterEncoding("UTF-8");
        // 요청 파라미터에서 검색어(keyword) 가져오기
    	
    	HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");

        if (user_num == null) {
            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/member/loginForm.do";
        }
        
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";  // 기본값 설정
        }

        // 페이지 관련 파라미터
        String pageNum = request.getParameter("pageNum");
        if (pageNum == null) {
            pageNum = "1";  // 기본 페이지 번호
        }

        // DAO 인스턴스 생성
        DietPlanDAO dao = DietPlanDAO.getInstance();

        // 총 검색된 음식 개수
        int count = dao.getDietCountByKeyword(keyword);

        // 페이징 처리
        int rowCount = 8;  // 한 페이지당 8개씩 표시
        int pageCount = 5;  // 한 화면에 보여줄 페이지 수 (페이지 번호)

        // 전체 페이지 수 계산 (Math.ceil을 사용하여 올림 처리)
        int totalPage = (int) Math.ceil((double) count / rowCount);

        // 현재 페이지 값 (정수로 변환)
        int currentPage = Integer.parseInt(pageNum);
        if (currentPage > totalPage) {
            currentPage = totalPage;  // 현재 페이지가 전체 페이지 수보다 크면 마지막 페이지로 설정
        }

        // PagingUtil 객체 생성 (현재 페이지, 총 게시물 수, 한 페이지당 게시물 수, 페이지 번호 수, 호출 페이지 URL)
        PagingUtil page = new PagingUtil(currentPage, count, rowCount, pageCount, 
        	    request.getContextPath() + "/mydiet/noteDietSearch.do");

        // 페이지 네비게이션 표시
        request.setAttribute("page", page.getPage());  // 페이지 네비게이션 문자열

        // totalPage와 currentPage 값을 request에 설정
        request.setAttribute("totalPage", totalPage);  // 전체 페이지 수
        request.setAttribute("currentPage", currentPage);  // 현재 페이지

        // 음식 목록 가져오기
        List<DietPlanVO> foodList = dao.searchDietByKeyword(keyword, page.getStartRow(), page.getEndRow());

        // 결과를 request에 저장
        request.setAttribute("foodList", foodList);
        request.setAttribute("count", count);
        request.setAttribute("keyword", keyword);

        // JSP 페이지로 이동
        return "mydiet/noteDietForm.jsp"; // 경로 변경
    }
}
