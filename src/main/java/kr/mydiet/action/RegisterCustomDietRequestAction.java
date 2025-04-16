package kr.mydiet.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import kr.controller.Action;
import kr.mydiet.dao.DietPlanDAO;
import kr.mydiet.vo.DietPlanVO;
import kr.member.vo.MemberVO;

public class RegisterCustomDietRequestAction implements Action {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 세션에서 사용자 정보 가져오기
    	HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");

        if (user_num == null) {
            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/member/loginForm.do";
        }
        
        // 요청 파라미터에서 dietId 가져오기
        long dietId = Long.parseLong(request.getParameter("dietId"));

        // DAO 인스턴스 생성
        DietPlanDAO dietPlanDAO = DietPlanDAO.getInstance();

        // 식단 상세 정보 가져오기
        DietPlanVO dietPlan = dietPlanDAO.getDietPlanById(dietId, user_num);

        if (dietPlan == null) {
            // 식단이 존재하지 않거나 사용자와 매칭되지 않으면 에러 메시지 설정
            session.setAttribute("message", "해당 식단이 존재하지 않거나 접근 권한이 없습니다.");
            return "redirect:/mydiet/selectCustomDiet.do";
        }

        // 이미 등록 요청이 되어 있는지 확인
        if (dietPlan.getDietComment() == 1) {
            session.setAttribute("message", "이미 등록 요청된 식단입니다.");
            return "redirect:/mydiet/selectCustomDiet.do";
        }

        // dietComment를 1로 설정하여 등록 요청 표시
        dietPlan.setDietComment(1);

        // dietComment 업데이트
        try {
            dietPlanDAO.updateDietComment(dietId, 1);
            session.setAttribute("message", "식단 등록 요청이 성공적으로 처리되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", "식단 등록 요청에 실패하였습니다. 다시 시도해주세요.");
        }

        // 적절한 페이지로 리다이렉트
        return "redirect:/mydiet/selectCustomDiet.do";
    }
}
