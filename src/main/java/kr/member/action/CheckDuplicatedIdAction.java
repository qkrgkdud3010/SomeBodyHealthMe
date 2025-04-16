package kr.member.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.util.StringUtil;
//
public class CheckDuplicatedIdAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 전송된 데이터 인코딩 처리
        request.setCharacterEncoding("utf-8");
        
        // 전송된 데이터 반환
        String loginId = request.getParameter("value");
        MemberDAO dao = MemberDAO.getInstance();
        
        // 아이디 중복 체크
        boolean isDuplicate = dao.isDuplicateId(loginId);
        
        Map<String, String> mapAjax = new HashMap<>();
        
        // 중복 체크 후 응답 설정
        if (isDuplicate) {
            mapAjax.put("result", "idDuplicated");  // 아이디 중복
        } else {
            mapAjax.put("result", "idNotFound");  // 사용 가능한 아이디
        }

        // 응답 로그 추가
        System.out.println("Server response: " + mapAjax.get("result"));  // 서버에서 보내는 응답을 로그로 찍기
        
        return StringUtil.parseJSON(request, mapAjax);
    }
}