package kr.mydiet.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.mydiet.dao.DietPlanDAO;
import kr.mydiet.vo.DietPlanVO;

public class SelectCustomDietAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // HttpSession에서 userNum을 가져오기
    	 HttpSession session = request.getSession();
         Long user_num = (Long) session.getAttribute("user_num");
         
         if (user_num == null) {
             // 로그인이 되지 않은 경우
             return "redirect:/member/loginForm.do";
         }
         
         request.setCharacterEncoding("utf-8");

        // DIET_SHOW가 0인 식단 목록 조회
        DietPlanDAO dao = DietPlanDAO.getInstance();
        List<DietPlanVO> dietList = dao.selectDietPlansWithDietShowZero(user_num);  // userNum을 매개변수로 전달

        // 조회된 식단 목록을 request에 저장
        request.setAttribute("dietList", dietList);

        // selectCustomDiet.jsp로 포워딩
        return "mydiet/selectCustomDiet.jsp";  // JSP 페이지로 포워딩
    }
}
