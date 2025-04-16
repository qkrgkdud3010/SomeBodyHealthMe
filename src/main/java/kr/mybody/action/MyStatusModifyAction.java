package kr.mybody.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.mybody.dao.MyBodyDAO;
import kr.mybody.vo.MyBodyStatusVO;

public class MyStatusModifyAction implements Action {

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

        // 자바빈(VO) 생성
        MyBodyStatusVO myBodyStatus = new MyBodyStatusVO();
        myBodyStatus.setUserNum(user_num);  // 세션에서 가져온 user_num을 VO에 설정
        myBodyStatus.setHeight(Integer.parseInt(request.getParameter("height")));
        myBodyStatus.setWeight(Integer.parseInt(request.getParameter("weight")));
        myBodyStatus.setAge(Integer.parseInt(request.getParameter("age")));
        myBodyStatus.setGoal(request.getParameter("goal"));  // height -> goal 수정
        myBodyStatus.setGender(request.getParameter("gender"));

        // MyBodyDAO 객체를 통해 데이터베이스에 데이터 삽입
        MyBodyDAO dao = MyBodyDAO.getInstance();
        dao.updateMyBodyStatus(myBodyStatus);

        // 완료 메시지 설정
        request.setAttribute("notice_msg", "수정 완료!");
        request.setAttribute("notice_url", request.getContextPath() + "/mybody/myStatus.do");

        // 포워딩할 JSP 페이지 반환 (null로 두면 기본적으로 리다이렉트 처리)
        return "common/alert_view.jsp";
    }


}
