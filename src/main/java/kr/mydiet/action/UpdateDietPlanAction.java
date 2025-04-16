package kr.mydiet.action;

import kr.controller.Action;
import kr.mydiet.dao.DietPlanDAO;
import kr.mydiet.vo.DietPlanVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateDietPlanAction implements Action {
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
            // 요청 파라미터에서 수정된 식단 정보 가져오기
            long dietId = Long.parseLong(request.getParameter("dietId"));
            String foodName = request.getParameter("foodName");
            double calories = Double.parseDouble(request.getParameter("calories"));
            double protein = Double.parseDouble(request.getParameter("protein"));
            double carbohydrate = Double.parseDouble(request.getParameter("carbohydrate"));
            double fat = Double.parseDouble(request.getParameter("fat"));
            double minerals = Double.parseDouble(request.getParameter("minerals"));

            // DietPlanVO 객체 생성 및 데이터 설정
            DietPlanVO dietPlan = new DietPlanVO();
            dietPlan.setDietId(dietId);
            dietPlan.setFoodName(foodName);
            dietPlan.setCalories(calories);
            dietPlan.setProtein(protein);
            dietPlan.setCarbohydrate(carbohydrate);
            dietPlan.setFat(fat);
            dietPlan.setMinerals(minerals);
            dietPlan.setUserNum(user_num);

            // DAO를 통해 식단 정보 업데이트
            DietPlanDAO dao = DietPlanDAO.getInstance();
            dao.updateDietPlan(dietPlan);

            // 수정 완료 후 메시지를 설정하고 식단 목록 페이지로 리다이렉트
            request.getSession().setAttribute("message", "식단 정보가 성공적으로 수정되었습니다.");
            return "redirect:/mydiet/showCustomDietAction.do";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "입력 값이 올바르지 않습니다.");
            return "/WEB-INF/views/error.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "식단 정보 수정 중 오류가 발생했습니다.");
            return "/WEB-INF/views/error.jsp";
        }
    }
}
