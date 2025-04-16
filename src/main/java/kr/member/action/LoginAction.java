package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.dao.MyPageDAO;
import kr.member.vo.MemberVO;
import kr.board.vo.BoardVO;

public class LoginAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        String loginId = request.getParameter("login_id");
        String password = request.getParameter("password");

        MemberDAO dao = MemberDAO.getInstance();
        MemberVO member = dao.checkLogin(loginId, password);

        if (member != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user_num", member.getUser_num()); // 사용자 번호 저장
            session.setAttribute("user_name", member.getName());   // 사용자 이름 저장
            session.setAttribute("status", member.getStatus());    // 사용자 권한 저장
            session.setAttribute("nick_name", member.getNick_name()); // 닉네임 저장

            // 내가 쓴 글 데이터를 초기화하여 세션에 저장
            MyPageDAO myPageDAO = MyPageDAO.getInstance();
            List<BoardVO> recentPosts = myPageDAO.getListByNickname(member.getNick_name());
            session.setAttribute("recentPosts", recentPosts);

            // 성공 메시지와 리디렉션 설정
            request.setAttribute("notice_msg", member.getName() + "님, 환영합니다!");
            request.setAttribute("notice_url", request.getContextPath() + "/main/main.do");

            return "common/alert_view.jsp"; // 성공 시 알림창 표시
        } else {
            // 로그인 실패 시 처리
            request.setAttribute("errorMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "member/loginForm.jsp";
        }
    }
}
