package kr.member.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.util.StringUtil;
//
public class CheckDuplicatedPhoneAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 전송된 데이터 인코딩 처리
        request.setCharacterEncoding("utf-8");

        // 전송된 데이터 반환
        String phone = request.getParameter("value");  // 'value' 파라미터로 전화번호 값을 받음
        MemberDAO dao = MemberDAO.getInstance();

        // 휴대전화 중복 여부 체크
        boolean isDuplicate = dao.isDuplicatePhone(phone);

        // 결과를 맵에 저장
        Map<String, String> mapAjax = new HashMap<>();
        if (!isDuplicate) {  // 중복되지 않은 경우
            mapAjax.put("result", "idNotFound");
        } else {  // 중복된 경우
            mapAjax.put("result", "idDuplicated");
        }

        // StringUtil을 사용해 JSON 형식으로 변환하여 반환
        return StringUtil.parseJSON(request, mapAjax);
    }
}