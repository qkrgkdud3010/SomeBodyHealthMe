package kr.mybody.action;

import java.sql.Date; // java.sql.Date를 사용합니다.
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import kr.controller.Action;
import kr.mybody.dao.MyBodyDAO;
import kr.mybody.vo.InbodyStatusVO;

public class InbodyStatusModifyAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 세션에서 user_num을 가져오기
        HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");

        if (user_num == null) {
            // 로그인되지 않은 경우, 로그인 페이지로 리다이렉트
            return "redirect:/member/loginForm.do";
        }

        // 로그인된 경우
        // 전송된 데이터 인코딩 처리
        request.setCharacterEncoding("utf-8");

        // 요청 파라미터에서 데이터 가져오기
        String measurementDateString = request.getParameter("measurementDate");  // 문자열로 받아오기
        String muscleMassStr = request.getParameter("muscleMass");
        String bodyFatPercentageStr = request.getParameter("bodyFatPercentage");

        // 날짜 변환 (String -> java.sql.Date)
        Date measurementDate = null;
        try {
            // 문자열을 java.sql.Date 객체로 변환
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(measurementDateString);  // 문자열을 java.util.Date 객체로 변환
            measurementDate = new Date(utilDate.getTime());  // java.sql.Date로 변환
        } catch (Exception e) {
            // 잘못된 날짜 형식인 경우 예외 처리
            request.setAttribute("error_msg", "날짜 형식이 잘못되었습니다.");
            return "common/error.jsp";  // 오류 페이지로 이동
        }

        // InbodyStatusVO 객체에 값 설정
        InbodyStatusVO inbodyStatus = new InbodyStatusVO();
        inbodyStatus.setUserNum(user_num);  // 세션에서 가져온 user_num 설정
        inbodyStatus.setMeasurementDate(measurementDate);  // 변환된 measurementDate 설정
        inbodyStatus.setMuscleMass(Double.parseDouble(muscleMassStr));  // 근육량
        inbodyStatus.setBodyFatPercentage(Double.parseDouble(bodyFatPercentageStr));  // 체지방률

        // MyBodyDAO를 사용하여 데이터베이스 업데이트
        MyBodyDAO dao = MyBodyDAO.getInstance();
        dao.updateInbodyStatus(inbodyStatus);  // DB에 업데이트 수행

        // 완료 메시지 설정
        request.setAttribute("notice_msg", "수정 완료!");
        request.setAttribute("notice_url", request.getContextPath() + "/mybody/myStatus.do");

        // 완료 메시지 후, 결과 페이지로 포워딩
        return "common/alert_view.jsp";
    }
}
