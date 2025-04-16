package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.member.dao.MyPageDAO;

public class RefreshRecentPostsAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String nickName = (String) session.getAttribute("nick_name");

        if (nickName == null) {
            request.setAttribute("notice_msg", "로그인이 필요합니다.");
            request.setAttribute("notice_url", request.getContextPath() + "/member/loginForm.do");
            return "common/alert_view.jsp";
        }

        // DAO를 통해 게시글 갱신
        MyPageDAO dao = MyPageDAO.getInstance();
        List<BoardVO> recentPosts = dao.getListByNickname(nickName);

        // 세션 갱신
        session.setAttribute("recentPosts", recentPosts);

        // 결과 반환
        request.setAttribute("notice_msg", "게시글 목록이 갱신되었습니다.");
        request.setAttribute("notice_url", request.getContextPath() + "/member/myPage.do");
        return "common/alert_view.jsp";
    }
}
