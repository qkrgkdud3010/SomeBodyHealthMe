package kr.mybody.action;

import java.sql.Date;  // java.sql.Date import
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.mybody.dao.MyBodyDAO;
import kr.mybody.vo.InbodyStatusVO;

public class InbodyStatusInsertAction implements Action {

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
        InbodyStatusVO inbodyStatus = new InbodyStatusVO();
        inbodyStatus.setUserNum(user_num);  // 세션에서 가져온 user_num을 VO에 설정

        // 측정 날짜 받기
        String measurementDateStr = request.getParameter("measurementDate");
        
        // 날짜 형식 처리 
        if (measurementDateStr != null && !measurementDateStr.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date measurementDate = new Date(sdf.parse(measurementDateStr).getTime());  // String을 java.sql.Date로 변환
            inbodyStatus.setMeasurementDate(measurementDate);  // VO에 설정
        }

        // 근육량, 체지방률 등을 VO에 설정
        String muscleMassStr = request.getParameter("muscleMass");
        String bodyFatPercentageStr = request.getParameter("bodyFatPercentage");
        
        if (muscleMassStr != null && !muscleMassStr.isEmpty()) {
            inbodyStatus.setMuscleMass(Double.parseDouble(muscleMassStr));
        }
        if (bodyFatPercentageStr != null && !bodyFatPercentageStr.isEmpty()) {
            inbodyStatus.setBodyFatPercentage(Double.parseDouble(bodyFatPercentageStr));
        }

        // MyBodyDAO 객체를 통해 데이터베이스에 데이터 삽입
        MyBodyDAO dao = MyBodyDAO.getInstance();
        dao.insertInbodyStatus(inbodyStatus);

        // 완료 메시지 설정
        request.setAttribute("notice_msg", "등록 완료!");
        request.setAttribute("notice_url", request.getContextPath() + "/mybody/myStatus.do");

        return "common/alert_view.jsp";
    }
}
