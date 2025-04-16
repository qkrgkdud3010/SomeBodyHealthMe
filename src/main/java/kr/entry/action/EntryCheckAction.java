package kr.entry.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.controller.Action;
import kr.entry.dao.EntryDAO;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EntryCheckAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String phone = request.getParameter("phone").replaceAll("-", "").trim();
        System.out.println("입력된 전화번호: " + phone); // 디버깅용 로그

        EntryDAO dao = EntryDAO.getInstance();
        Long userNum = dao.getUserNumByPhone(phone);
        System.out.println("DAO에서 반환된 userNum: " + userNum); // 디버깅용 로그

        if (userNum == null) {
            request.setAttribute("message", "등록되지 않은 번호입니다.");
        } else {
            dao.insertEntryLog(userNum, phone);
            request.setAttribute("message", "출입이 확인되었습니다.");
        }

        return "entry/entryResult.jsp";
    }
}
