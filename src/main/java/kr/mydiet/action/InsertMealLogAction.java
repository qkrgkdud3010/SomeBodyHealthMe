package kr.mydiet.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.mydiet.dao.MealLogDAO;
import kr.mydiet.vo.MealLogVO;

public class InsertMealLogAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	request.setCharacterEncoding("UTF-8");
        // 세션에서 user_num 가져오기
        HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");

        if (user_num == null) {
            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/member/loginForm.do";
        }

        // 요청 파라미터 가져오기
        String foodName = request.getParameter("foodName");
        String mealType = request.getParameter("mealType");

        // VO 객체 생성 및 데이터 설정
        MealLogVO mealLog = new MealLogVO();
        mealLog.setFoodName(foodName);
        mealLog.setMealType(mealType);
        mealLog.setUserNum(user_num);

        // DAO를 통해 데이터 삽입
        MealLogDAO dao = MealLogDAO.getInstance();
        dao.insertMealLog(mealLog);

        // 성공 메시지와 리다이렉트 URL 설정
        request.setAttribute("notice_msg", "식사 로그가 성공적으로 저장되었습니다.");
        request.setAttribute("notice_url", request.getContextPath() + "/mydiet/makeDietForm.do");

        // alert_view.jsp로 포워딩
        return "common/alert_view.jsp";
    }
}
