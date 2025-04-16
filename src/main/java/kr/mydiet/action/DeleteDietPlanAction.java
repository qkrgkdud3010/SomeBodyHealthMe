package kr.mydiet.action;

import kr.controller.Action;
import kr.mydiet.dao.DietPlanDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteDietPlanAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 세션에서 사용자 정보 가져오기
        HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");

        if (user_num == null) {
            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/member/loginForm.do";
        }

        try {
            // 요청 파라미터에서 dietId 가져오기
            long dietId = Long.parseLong(request.getParameter("dietId"));

            // DAO를 통해 식단 삭제
            DietPlanDAO dao = DietPlanDAO.getInstance();
            dao.deleteDietPlan(dietId, user_num);

            // 삭제 완료 후 메시지를 설정하고 식단 목록 페이지로 리다이렉트
            session.setAttribute("message", "식단이 성공적으로 삭제되었습니다.");
            return "redirect:/mydiet/selectCustomDiet.do";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "잘못된 요청입니다.");
            return "/WEB-INF/views/error.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "식단 삭제 중 오류가 발생했습니다.");
            return "/WEB-INF/views/error.jsp";
        }
    }
}
