package kr.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.board.vo.BoardVO;
import kr.util.DBUtil;

public class MyPageDAO {
    private static MyPageDAO instance = new MyPageDAO();

    public static MyPageDAO getInstance() {
        return instance;
    }

    private MyPageDAO() {}

    public List<BoardVO> getListByNickname(String nickName) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BoardVO> list = new ArrayList<>();
        String sql = "SELECT * FROM board WHERE user_num IN " +
                     "(SELECT user_num FROM suser_detail WHERE LOWER(nick_name) = LOWER(?)) " +
                     "ORDER BY board_regdate DESC";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nickName);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                BoardVO board = new BoardVO();
                board.setBoard_num(rs.getLong("board_num"));
                board.setBoard_title(rs.getString("board_title"));
                board.setBoard_regdate(rs.getString("board_regdate"));

                list.add(board);
            }
            System.out.println("[DEBUG] 가져온 게시글 수: " + list.size());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("게시글 조회 중 오류 발생: " + e.getMessage());
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        return list;
    }

}
