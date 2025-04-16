package kr.mybody.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.mybody.dao.MyBodyDAO;
import kr.mybody.vo.InbodyStatusVO;

public class InbodyStatusModifyFormAction implements Action {
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
            MyBodyDAO dao = MyBodyDAO.getInstance();
            // user_num에 해당하는 모든 인바디 데이터 조회
            List<InbodyStatusVO> inbodyStatusList = dao.getAllInbodyData(user_num);
            
            // 조회된 데이터 JSP로 전달
            request.setAttribute("inbodyStatusList", inbodyStatusList);
        } catch (Exception e) {
            // 예외 처리, 로그 출력 또는 사용자에게 오류 메시지 제공
            e.printStackTrace();
            request.setAttribute("error", "인바디 데이터 조회 중 오류가 발생했습니다.");
            return "/error.jsp";  // 오류 페이지로 리다이렉트
        }
        
        // JSP 경로 반환 (inbodyData 데이터를 사용할 수 있는 JSP로 이동)
        return "/mybody/inbodyStatusModifyForm.jsp";
    }
}
