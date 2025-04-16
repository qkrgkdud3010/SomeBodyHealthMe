package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
//
public class RegisterUserAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");

        MemberVO member = new MemberVO();
        member.setLogin_id(request.getParameter("loginId"));
        member.setNick_name(request.getParameter("nickname"));
        member.setPassword(request.getParameter("password"));
        member.setEmail(request.getParameter("email"));
        member.setName(request.getParameter("name"));
        member.setBirth_date(request.getParameter("birthdate"));
        member.setPhone(request.getParameter("phone"));

        // 관리자 코드 처리: "MASTER" 입력 시 관리자 설정
        String adminCodeParam = request.getParameter("adminCode");

        int status = 1; // 기본 상태를 일반 사용자로 설정

        if (adminCodeParam != null && adminCodeParam.trim().equalsIgnoreCase("MASTER")) {
            status = 4; // "MASTER" 입력 시 관리자 권한 부여
           // System.out.println("Admin code detected, setting status to 4"); // 확인용 해결함
       // } else {
        //    System.out.println("No admin code or invalid admin code, setting status to 1"); // 확인용 해결함
        }
        member.setStatus(status);  // status 필드에 설정

        // 센터 코드 유효성 검사
        String centerCodeParam = request.getParameter("centerCode");
        if (centerCodeParam == null || centerCodeParam.isEmpty()) {
            throw new Exception("센터 코드를 선택해주세요.");
        }
        member.setCenter_num(Integer.parseInt(centerCodeParam));

        MemberDAO dao = MemberDAO.getInstance();
        dao.insertMember(member);

        request.setAttribute("result_title", "회원 가입 완료");
        request.setAttribute("result_msg", "회원 가입이 완료되었습니다.");
        request.setAttribute("result_url", request.getContextPath() + "/main/main.do");
        return "common/result_view.jsp";
    }
}