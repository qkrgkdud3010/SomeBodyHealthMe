package kr.mydiet.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.mydiet.dao.DietPlanDAO;
import kr.mydiet.vo.DietPlanVO;

public class InsertDietAction implements Action{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 세션에서 user_num을 가져오기
        HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");

        if (user_num == null) {
            return "redirect:/member/loginForm.do";
        }

        // 로그인된 경우
        request.setCharacterEncoding("utf-8");

        // 자바빈(VO) 생성
        DietPlanVO diet = new DietPlanVO();
        diet.setUserNum(user_num);  // 세션에서 가져온 user_num을 VO에 설정

        // 사용자 입력 값 받아오기
        String foodName = request.getParameter("foodName");
        String calories = request.getParameter("calories");
        String protein = request.getParameter("protein");
        String carbohydrate = request.getParameter("carbohydrate");
        String fat = request.getParameter("fat");
        String minerals = request.getParameter("minerals");

        // 입력 값 VO에 세팅
        diet.setFoodName(foodName);
        diet.setCalories(Double.parseDouble(calories));  // 칼로리는 숫자이므로 Integer로 변환
        diet.setProtein(Double.parseDouble(protein));    // 단백질은 숫자
        diet.setCarbohydrate(Double.parseDouble(carbohydrate)); // 탄수화물은 숫자
        diet.setFat(Double.parseDouble(fat));            // 지방은 숫자
        diet.setMinerals(minerals != null ? Double.parseDouble(minerals) : 0); // 미네랄은 선택적 (빈 값 처리)

        // MyBodyDAO 객체를 통해 데이터베이스에 데이터 삽입
        DietPlanDAO dao = DietPlanDAO.getInstance();
        dao.insertDietPlan(diet);

        // 완료 메시지 설정
        request.setAttribute("notice_url", request.getContextPath() + "/mydiet/customDietForm.do");
        request.setAttribute("notice_msg", "식단 등록이 완료되었습니다!");
        

        return "common/alert_view.jsp";
    }
}
