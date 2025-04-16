package kr.mydiet.action;

import kr.controller.Action;
import kr.mydiet.dao.DietPlanDAO;
import kr.mydiet.vo.DietPlanVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RegisterCustomDietRequestFormAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 세션에서 사용자 정보 가져오기
        HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");

        if (user_num == null) {
            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/member/loginForm.do";
        }
        
        request.setCharacterEncoding("utf-8");

        try {
            DietPlanDAO dao = DietPlanDAO.getInstance();
            List<DietPlanVO> dietList = dao.selectDietPlansWithDietShowZero(user_num);

            // dietList를 request에 저장
            request.setAttribute("dietList", dietList);

            // 선택된 식단 ID 가져오기
            String dietIdStr = request.getParameter("dietIdSelect");
            if (dietIdStr != null && !dietIdStr.isEmpty()) {
                long dietId = Long.parseLong(dietIdStr);
                DietPlanVO selectedDietPlan = dao.getDietPlanById(dietId, user_num);

                if (selectedDietPlan != null) {
                    // 선택된 식단 정보를 request에 저장
                    request.setAttribute("selectedDietPlan", selectedDietPlan);
                } else {
                    request.setAttribute("message", "선택한 식단을 찾을 수 없습니다.");
                }
            }
            return "/mydiet/registerCustomDietRequestForm.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "식단 조회에 실패했습니다.");
            return "/WEB-INF/views/error.jsp";
        }
    }
}
