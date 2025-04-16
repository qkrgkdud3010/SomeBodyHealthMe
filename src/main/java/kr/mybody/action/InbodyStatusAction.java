package kr.mybody.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.mybody.dao.MyBodyDAO;
import kr.mybody.vo.MyBodyStatusVO;
import kr.mybody.vo.InbodyStatusVO;
import java.util.List;

public class InbodyStatusAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");
        
        if (user_num == null) {
            // 로그인이 되지 않은 경우
            return "redirect:/member/loginForm.do";
        }

        // 로그인 된 경우
        MyBodyDAO dao = MyBodyDAO.getInstance();
        
        // 1. 개인 인바디 상태 가져오기 (예: 키, 체중 등)
        MyBodyStatusVO mybodystatus = dao.getMyBodyStatus(user_num);
        request.setAttribute("mybodystatus", mybodystatus);

        // 2. 월별 인바디 데이터 가져오기 (평균 근육량, 평균 체지방률)
        List<InbodyStatusVO> inbodyData = dao.getMonthlyInBodyData(user_num);
        request.setAttribute("inbodyData", inbodyData);
        
        // JSP 경로 반환
        return "mybody/inbodyStatus.jsp";
    }
}
