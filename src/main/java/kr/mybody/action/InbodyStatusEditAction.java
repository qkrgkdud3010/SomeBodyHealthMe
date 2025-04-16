package kr.mybody.action;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.mybody.dao.MyBodyDAO;
import kr.mybody.vo.InbodyStatusVO;

public class InbodyStatusEditAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 세션에서 사용자 정보 가져오기
        HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");

        if (user_num == null) {
            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/member/loginForm.do";
        }

        // 사용자 선택한 측정 날짜 가져오기
        String measurementDateStr = request.getParameter("measurementDate");

        if (measurementDateStr == null || measurementDateStr.isEmpty()) {
            request.setAttribute("message", "인바디 데이터를 선택하세요.");
            return "/mybody/inbodyStatusModifyForm.jsp";
        }

        // 날짜를 Date 형식으로 변환
        Date measurementDate = Date.valueOf(measurementDateStr);

        try {
            // DAO를 통해 해당 날짜의 인바디 데이터 조회
            MyBodyDAO dao = MyBodyDAO.getInstance();
            InbodyStatusVO inbodyStatus = dao.getInbodyDataByDate(user_num, measurementDate);
            // user_num에 해당하는 모든 인바디 데이터 조회
            List<InbodyStatusVO> inbodyStatusList = dao.getAllInbodyData(user_num);

            if (inbodyStatus != null) {
                // 조회된 데이터를 request에 저장
                request.setAttribute("inbodyStatus", inbodyStatus);
            } else {
                request.setAttribute("message", "선택한 날짜에 대한 인바디 데이터가 존재하지 않습니다.");
            }
            
            // 조회된 데이터 JSP로 전달
            request.setAttribute("inbodyStatusList", inbodyStatusList);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "인바디 데이터 조회 중 오류가 발생했습니다.");
            return "/error.jsp";  // 오류 페이지로 리다이렉트
        }

        // 데이터를 화면에 출력할 수 있는 JSP로 이동
        return "/mybody/inbodyStatusModifyForm.jsp";
    }
}
