package kr.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.board.vo.BoardVO;
import kr.board.vo.Board_replyVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;

public class BoardDAO {
	//싱글턴 패턴
	private static BoardDAO instance = new BoardDAO();
	public static BoardDAO getInstance() {
		return instance;
	}
	private BoardDAO() {}

	//글등록
	public void insertBoard(BoardVO board) throws Exception{
		Connection conn = null; 
		PreparedStatement pstmt = null; 
		String sql;

		try {
			conn = DBUtil.getConnection();

			sql = "INSERT INTO board(board_num,board_category,board_title,board_content,board_attachment,user_num) "
					+ "VALUES(board_seq.nextval,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, board.getBoard_category());
			pstmt.setString(2, board.getBoard_title());
			pstmt.setString(3, board.getBoard_content());
			pstmt.setString(4, board.getBoard_attachment());
			pstmt.setLong(5, board.getUser_num());

			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//글의 총 개수/ 검색글 개수
	public int getBoardCount(String keyfield,String keyword,String addkey) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		String sub_sql = "";
		int count = 0;
		int cnt = 0;


		try {
			conn = DBUtil.getConnection();
			if(addkey != null && !"".equals(addkey)) {
				sub_sql += " WHERE board_category = ?";
				if(keyword != null && !"".equals(keyword)) {
					if(keyfield.equals("1")) sub_sql += " AND board_title LIKE '%' || ? || '%'";//제목으로 검색
					if(keyfield.equals("2")) sub_sql += " AND board_content LIKE '%' || ? || '%'";//내용으로 검색
					if(keyfield.equals("3")) sub_sql += " AND nick_name LIKE '%' || ? || '%'";//닉네임으로 검색
				}
			}else {
				if(keyword != null && !"".equals(keyword)) {
					if(keyfield.equals("1")) sub_sql += " WHERE board_title LIKE '%' || ? || '%'";//제목으로 검색
					if(keyfield.equals("2")) sub_sql += " WHERE board_content LIKE '%' || ? || '%'";//내용으로 검색
					if(keyfield.equals("3")) sub_sql += " WHERE nick_name LIKE '%' || ? || '%'";//닉네임으로 검색
				}
			}
			sql = "SELECT COUNT(*) FROM board JOIN (SELECT * FROM suser LEFT OUTER JOIN suser_detail USING(user_num)) USING(user_num) " +  sub_sql;

			pstmt = conn.prepareStatement(sql);

			if(addkey != null && !"".equals(addkey)) {
				pstmt.setInt(++cnt, Integer.parseInt(addkey));
				if(keyword != null && !"".equals(keyword)) {
					pstmt.setString(++cnt, keyword);
				}
			}else {
				if(keyword != null && !"".equals(keyword)) {
					pstmt.setString(++cnt, keyword);
				}
			}

			rs = pstmt.executeQuery();
			if(rs.next()) count = rs.getInt(1);

		}catch(Exception e ) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return count;
	}
	//글 목록
	public List<BoardVO> getListBoard(int start, int end, String keyfield,String keyword,String addkey) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> list = new ArrayList<BoardVO>();
		int cnt = 0;
		String sql;
		String sub_sql = "";

		try {
			conn = DBUtil.getConnection();

			if(addkey != null && !"".equals(addkey)) {
				sub_sql += " WHERE board_category = ?";
				if(keyword != null && !"".equals(keyword)) {
					if(keyfield.equals("1")) sub_sql += " AND board_title LIKE '%' || ? || '%'";//제목으로 검색
					if(keyfield.equals("2")) sub_sql += " AND board_content LIKE '%' || ? || '%'";//내용으로 검색
					if(keyfield.equals("3")) sub_sql += " AND nick_name LIKE '%' || ? || '%'";//닉네임으로 검색
				}
			}else {
				if(keyword != null && !"".equals(keyword)) {
					if(keyfield.equals("1")) sub_sql += " WHERE board_title LIKE '%' || ? || '%'";//제목으로 검색
					if(keyfield.equals("2")) sub_sql += " WHERE board_content LIKE '%' || ? || '%'";//내용으로 검색
					if(keyfield.equals("3")) sub_sql += " WHERE nick_name LIKE '%' || ? || '%'";//닉네임으로 검색
				}
			}
			//SQL맞음 고치지 말아
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM (SELECT * FROM board LEFT OUTER JOIN (SELECT board_num,COUNT(*) recount FROM BOARD_REPLY GROUP BY board_num) USING(board_num)) JOIN (SELECT * FROM suser LEFT OUTER JOIN suser_detail USING(user_num)) USING (user_num) "  + sub_sql + " "
					+ "ORDER BY (CASE WHEN board_category=1 THEN 0 ELSE 1 END) , board_num DESC ) a) WHERE rnum >= ? AND rnum <= ?";

			pstmt = conn.prepareStatement(sql);

			if(addkey != null && !"".equals(addkey)) {
				pstmt.setInt(++cnt, Integer.parseInt(addkey));
				if(keyword != null && !"".equals(keyword)) {
					pstmt.setString(++cnt, keyword);
				}
			}else {
				if(keyword != null && !"".equals(keyword)) {
					pstmt.setString(++cnt, keyword);
				}
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				BoardVO board = new BoardVO();
				board.setBoard_num(rs.getLong("board_num"));

				String title = rs.getString("board_title");
				if(title.length() > 35) title= title.substring(0,35) + "...";				
				board.setBoard_title(title);
				board.setBoard_regdate(DurationFromNow.getTimeDiffLabel(rs.getString("board_regdate")));
				board.setBoard_count(rs.getLong("board_count"));
				board.setBoard_category(rs.getInt("board_category"));
				board.setNick_name(rs.getString("nick_name"));
				board.setLogin_id(rs.getString("login_id"));
				board.setRecount(rs.getInt("recount"));

				list.add(board);
			}

		}catch(Exception e ) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

	//글 상세 페이지
	public BoardVO getBoard(long board_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		BoardVO board = new BoardVO();

		try {
			conn = DBUtil.getConnection();

			sql = "SELECT * FROM board LEFT OUTER JOIN (SELECT * FROM suser LEFT OUTER JOIN suser_detail USING(user_num))USING(user_num) WHERE board_num =?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, board_num);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				board.setBoard_title(rs.getString("board_title"));
				board.setBoard_category(rs.getInt("board_category"));
				board.setBoard_content(rs.getString("board_content"));
				board.setBoard_num(rs.getLong("board_num"));
				board.setBoard_title(rs.getString("board_title"));
				board.setBoard_regdate(DurationFromNow.getTimeDiffLabel(rs.getString("board_regdate")));
				if(rs.getString("board_modifydate") != null) {
					board.setBoard_modifydate(DurationFromNow.getTimeDiffLabel(rs.getString("board_modifydate")));
				}
				board.setBoard_attachment(rs.getString("board_attachment"));
				board.setBoard_count(rs.getLong("board_count"));
				board.setNick_name(rs.getString("nick_name"));
				board.setLogin_id(rs.getString("login_id"));
				board.setUser_num(rs.getLong("user_num"));
				board.setPhoto(rs.getString("photo"));
			}			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {

		}

		return board;
	}

	//조회수 증가 
	public void updateReadCount(long board_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql;

		try {
			conn = DBUtil.getConnection();

			sql = "UPDATE board SET board_count = board_count+1 WHERE board_num = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, board_num);

			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		} 

	}
	//파일 삭제

	//글수정
	public void updateBoard(BoardVO board)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;

		try {
			conn = DBUtil.getConnection();
			
			if(board.getBoard_attachment()!=null 
					&& !"".equals(board.getBoard_attachment())) {
				sub_sql += ",board_attachment=?";
			}

			sql = "UPDATE board SET board_title=?, board_content=? " + sub_sql+ " ,board_modifydate=SYSDATE WHERE board_num=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(++cnt, board.getBoard_title());
			pstmt.setString(++cnt, board.getBoard_content());
			if(board.getBoard_attachment()!=null 
				    && !"".equals(board.getBoard_attachment())) {
			pstmt.setString(++cnt, board.getBoard_attachment());
		}
			pstmt.setLong(++cnt, board.getBoard_num());

			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}		
	}	
	
	public void deleteAttachment(long board_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE board SET board_attachment='' WHERE board_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, board_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	

	//글 삭제
	public void deleteBoard(long board_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);

			//댓글 삭제
			sql = "DELETE FROM board_reply WHERE board_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, board_num);
			pstmt.executeUpdate();

			//게시글 삭제
			sql= "DELETE FROM board WHERE board_num = ?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setLong(1, board_num);
			pstmt2.executeUpdate();

			conn.commit();//성공시 커밋
		}catch(Exception e) {
			conn.rollback();//실패시 롤백
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}		
	}


	//댓글
	//댓글 작성
	public void insertReplyBoard(Board_replyVO reply) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();

			sql = "INSERT INTO board_reply(re_num,re_content,re_regdate,user_num,board_num)"
					+ " VALUES(bd_reply_seq.nextval,?,SYSDATE,?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, reply.getRe_content());
			pstmt.setLong(2, reply.getUser_num());
			pstmt.setLong(3, reply.getBoard_num());

			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//댓글 개수
	public int getReplyCount(long board_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		int count = 0;

		try {
			conn = DBUtil.getConnection();

			sql = "SELECT COUNT(*) FROM board_reply WHERE board_num = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, board_num);

			rs = pstmt.executeQuery();
			if(rs.next()) count = rs.getInt(1);

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;		
	}
	//댓글 목록
	public List<Board_replyVO> getReply(int start, int end, long board_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		List<Board_replyVO> list = null;

		try {
			conn = DBUtil.getConnection();

			sql = "SELECT * FROM "
					+ "(SELECT rownum rnum ,a.* FROM "
					+ "(SELECT * FROM board_reply JOIN (SELECT * FROM suser JOIN suser_detail USING (user_num)) USING (user_num) WHERE board_num = ? ORDER BY re_regdate DESC) a) WHERE rnum >= ? AND rnum <= ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, board_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);

			rs = pstmt.executeQuery();
			list = new ArrayList<Board_replyVO>();
			while(rs.next()) {
				Board_replyVO reply = new Board_replyVO();
				reply.setRe_num(rs.getLong("re_num"));
				reply.setRe_regdate(DurationFromNow.getTimeDiffLabel(rs.getString("re_regdate")));
				if(rs.getString("re_modifydate") != null) {
					reply.setRe_modifydate(DurationFromNow.getTimeDiffLabel(rs.getString("re_modifydate")));
				}
				reply.setRe_content(StringUtil.useBrNoHtml(rs.getString("re_content")));
				if(rs.getString("photo") != null) reply.setPhoto(rs.getString("photo"));
				reply.setUser_num(rs.getLong("user_num"));
				reply.setLogin_id(rs.getString("login_id"));
				reply.setNick_name(rs.getString("nick_name"));
				list.add(reply);
			}

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;		
	}
	//댓글 상세
	public Board_replyVO getReplyBoard(long re_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Board_replyVO reply = null;
		String sql;

		try {
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql="SELECT * FROM board_reply WHERE re_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, re_num);
			//SQL문 실행
			rs = pstmt.executeQuery();

			if(rs.next()) {
				reply = new Board_replyVO();
				reply.setRe_num(rs.getLong("re_num"));
				reply.setUser_num(rs.getLong("user_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return reply;
	}

	//댓글 수정
	public void UpdateReply(Board_replyVO reply) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql;

		try {
			conn = DBUtil.getConnection();

			sql = "UPDATE board_reply SET re_content = ? , re_modifydate = SYSDATE WHERE re_num = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, reply.getRe_content());
			pstmt.setLong(2, reply.getRe_num());

			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//댓글 삭제
	public void deleteReply(long re_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql;

		try {
			conn = DBUtil.getConnection();

			sql = "DELETE board_reply WHERE re_num = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, re_num);

			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

}





























