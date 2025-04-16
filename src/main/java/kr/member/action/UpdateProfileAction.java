package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class UpdateProfileAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");
        Integer status = (Integer) session.getAttribute("status");

        // 로그인 여부 확인
        if (user_num == null || status == null) {
            return "redirect:/member/loginForm.do";
        }

        request.setCharacterEncoding("utf-8");

        // 사용자 입력 데이터 가져오기
        String nick_name = request.getParameter("nick_name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String birth_date = request.getParameter("birth_date");

        MemberDAO dao = MemberDAO.getInstance();
        MemberVO member = new MemberVO();
        member.setUser_num(user_num);
        member.setNick_name(nick_name != null && !nick_name.isEmpty() ? nick_name : null);
        member.setEmail(email != null && !email.isEmpty() ? email : null);
        member.setPhone(phone != null && !phone.isEmpty() ? phone : null);
        member.setBirth_date(birth_date != null && !birth_date.isEmpty() ? birth_date : null);

        // 동적 업데이트
        dao.updateUserProfileDynamic(member);

        // 권한에 따른 리다이렉션
        if (status == 4) { // 마스터 관리자
            return "redirect:/member/adminPage.do";
        } else if (status == 2 || status == 3) { // 매니저(사무직, 트레이너)
            return "redirect:/member/managerPage.do";
        } else if (status == 1) { // 일반 사용자
            return "redirect:/member/myPage.do";
        } else {
            // 예기치 않은 접근
            request.setAttribute("notice_msg", "잘못된 요청입니다.");
            request.setAttribute("notice_url", request.getContextPath() + "/main/main.do");
            return "common/alert_view.jsp";
        }
    }
}	