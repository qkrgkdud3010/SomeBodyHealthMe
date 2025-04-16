package kr.mydiet.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.mydiet.dao.DietPlanDAO;

public class AdminUpdateDietPlanAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");

        if (user_num == null) {
            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/member/loginForm.do";
        }
    	
        // 요청 파라미터 가져오기
        long dietId = Long.parseLong(request.getParameter("dietId"));
        int dietShow = Integer.parseInt(request.getParameter("dietShow"));
        int dietComment = Integer.parseInt(request.getParameter("dietComment"));

        // DAO 인스턴스를 통해 업데이트 수행
        DietPlanDAO dao = DietPlanDAO.getInstance();
        dao.updateDietPlan(dietId, dietShow, dietComment);

        // 알림 메시지와 이동 URL 설정
        request.setAttribute("notice_url", request.getContextPath() + "/mydiet/adminDietPlan.do");
        request.setAttribute("notice_msg", "업데이트가 완료되었습니다.");
        

        // alert_view.jsp로 포워딩
        return "common/alert_view.jsp";
    }
}
