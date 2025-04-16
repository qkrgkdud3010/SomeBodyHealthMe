package kr.mydiet.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.mydiet.dao.MealLogDAO;
import kr.mydiet.vo.DietPlanVO;
import kr.mydiet.vo.MealLogVO;

public class MakeDietFormAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");

        if (user_num == null) {
            return "redirect:/member/loginForm.do";
        }

        MealLogDAO dao = MealLogDAO.getInstance();

        // 끼니별 식사 기록 조회
        List<MealLogVO> breakfastLogs = dao.getMealLogsByUserAndMealType(user_num, "아침");
        List<MealLogVO> lunchLogs = dao.getMealLogsByUserAndMealType(user_num, "점심");
        List<MealLogVO> dinnerLogs = dao.getMealLogsByUserAndMealType(user_num, "저녁");
        List<MealLogVO> snackLogs = dao.getMealLogsByUserAndMealType(user_num, "간식");

        // 영양 성분 합산
        Map<String, Double> breakfastSummary = calculateNutritionSummary(breakfastLogs, dao);
        Map<String, Double> lunchSummary = calculateNutritionSummary(lunchLogs, dao);
        Map<String, Double> dinnerSummary = calculateNutritionSummary(dinnerLogs, dao);
        Map<String, Double> snackSummary = calculateNutritionSummary(snackLogs, dao);

        // 총 영양 성분 합계 계산
        Map<String, Double> totalSummary = new HashMap<>();
        totalSummary.put("calories", breakfastSummary.get("calories") + lunchSummary.get("calories")
                + dinnerSummary.get("calories") + snackSummary.get("calories"));
        totalSummary.put("protein", breakfastSummary.get("protein") + lunchSummary.get("protein")
                + dinnerSummary.get("protein") + snackSummary.get("protein"));
        totalSummary.put("carbohydrate", breakfastSummary.get("carbohydrate") + lunchSummary.get("carbohydrate")
                + dinnerSummary.get("carbohydrate") + snackSummary.get("carbohydrate"));
        totalSummary.put("fat", breakfastSummary.get("fat") + lunchSummary.get("fat")
                + dinnerSummary.get("fat") + snackSummary.get("fat"));

        // JSP에 데이터 저장
        request.setAttribute("breakfastLogs", breakfastLogs);
        request.setAttribute("lunchLogs", lunchLogs);
        request.setAttribute("dinnerLogs", dinnerLogs);
        request.setAttribute("snackLogs", snackLogs);
        request.setAttribute("breakfastSummary", breakfastSummary);
        request.setAttribute("lunchSummary", lunchSummary);
        request.setAttribute("dinnerSummary", dinnerSummary);
        request.setAttribute("snackSummary", snackSummary);
        request.setAttribute("totalSummary", totalSummary);

        return "mydiet/makeDietForm.jsp";
    }

    private Map<String, Double> calculateNutritionSummary(List<MealLogVO> logs, MealLogDAO dao) {
        Map<String, Double> summary = new HashMap<>();
        summary.put("calories", 0.0);
        summary.put("protein", 0.0);
        summary.put("carbohydrate", 0.0);
        summary.put("fat", 0.0);

        for (MealLogVO log : logs) {
            DietPlanVO dietPlan = dao.getDietPlanByFoodName(log.getFoodName());
            if (dietPlan != null) {
                summary.put("calories", summary.get("calories") + dietPlan.getCalories());
                summary.put("protein", summary.get("protein") + dietPlan.getProtein());
                summary.put("carbohydrate", summary.get("carbohydrate") + dietPlan.getCarbohydrate());
                summary.put("fat", summary.get("fat") + dietPlan.getFat());
            }
        }

        return summary;
    }
}
