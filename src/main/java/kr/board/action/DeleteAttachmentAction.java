package kr.board.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.FileUtil;
import kr.util.StringUtil;

public class DeleteAttachmentAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = new HashMap<String,String>();
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인이 된 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			//전송된 데이터 반환
			long board_num = Long.parseLong(request.getParameter("board_num"));
			
			BoardDAO dao = BoardDAO.getInstance();
			BoardVO db_board = dao.getBoard(board_num);
			//로그인한 회원번호와 작성자 회원번호 일치 여부 체크
			if(user_num!=db_board.getUser_num()) {
				mapAjax.put("result", "wrongAccess");
			}else {
				dao.deleteAttachment(board_num);
				//파일 삭제
				FileUtil.removeFile(request, 
						               db_board.getBoard_attachment());
				mapAjax.put("result", "success");
			}
		}
		//JSON 데이터로 변환
		return StringUtil.parseJSON(request, mapAjax);
	}
}
