package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;

import java.io.File;
import java.util.UUID;

public class UploadPhotoAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");
        Integer status = (Integer) session.getAttribute("status");

        // 로그인 여부 확인
        if (user_num == null || status == null) {
            return "redirect:/member/loginForm.do";
        }

        try {
            // 파일 업로드 처리
            String uploadPath = request.getServletContext().getRealPath("/upload");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir(); // 디렉토리가 없으면 생성
            }

            // 업로드된 파일 가져오기
            String originalFileName = request.getPart("photo").getSubmittedFileName();
            if (originalFileName == null || originalFileName.isEmpty()) {
                throw new Exception("업로드된 파일이 없습니다.");
            }

            // 파일 이름 중복 방지 (UUID 사용)
            String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;
            File uploadFile = new File(uploadPath, uniqueFileName);
            request.getPart("photo").write(uploadFile.getAbsolutePath());

            // DB에 업로드된 파일 정보 저장
            MemberDAO dao = MemberDAO.getInstance();
            dao.updateUserPhoto(user_num, uniqueFileName);

            // 권한에 따른 리다이렉션
            if (status == 4) { // 마스터 관리자
                return "redirect:/member/adminPage.do";
            } else if (status == 2 || status == 3) { // 매니저(사무직, 트레이너)
                return "redirect:/member/managerPage.do";
            } else if (status == 1) { // 일반 사용자
                return "redirect:/member/myPage.do";
            } else {
                throw new Exception("잘못된 접근입니다.");
            }
        } catch (Exception e) {
            request.setAttribute("notice_msg", "사진 업로드 중 오류가 발생했습니다: " + e.getMessage());
            request.setAttribute("notice_url", request.getContextPath() + "/member/myPage.do");
            return "common/alert_view.jsp";
        }
    }
}