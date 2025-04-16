package kr.mydiet.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.mydiet.dao.DietPlanDAO;
import kr.mydiet.vo.DietPlanVO;

public class AdminDietPlanAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");

        if (user_num == null) {
            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/member/loginForm.do";
        }
    	
        DietPlanDAO dao = DietPlanDAO.getInstance();

        // 모든 DietPlan 데이터 가져오기
        List<DietPlanVO> dietPlans = dao.getAllDietPlans();
        request.setAttribute("dietPlans", dietPlans);

        return "mydiet/adminDietPlan.jsp";
    }
}
