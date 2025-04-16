package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.member.dao.MyPageDAO;

public class MyPageBoardListAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String nickName = (String) session.getAttribute("nick_name");

        if (nickName == null) {
            request.setAttribute("notice_msg", "로그인이 필요합니다.");
            request.setAttribute("notice_url", request.getContextPath() + "/member/loginForm.do");
            return "common/alert_view.jsp";
        }

        MyPageDAO dao = MyPageDAO.getInstance();
        List<BoardVO> recentPosts = dao.getListByNickname(nickName);

        if (recentPosts == null || recentPosts.isEmpty()) {
            System.out.println("[DEBUG] recentPosts가 비어 있습니다.");
        } else {
            System.out.println("[DEBUG] recentPosts 사이즈: " + recentPosts.size());
        }

        // JSP에 전달
        request.setAttribute("recentPosts", recentPosts);
        return "/member/myPage.jsp";
    }
}

