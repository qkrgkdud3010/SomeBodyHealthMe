package kr.entry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.entry.vo.EntryVO;
import kr.util.DBUtil;

public class EntryUserDAO {
    private static EntryUserDAO instance = new EntryUserDAO();

    public static EntryUserDAO getInstance() {
        return instance;
    }

    private EntryUserDAO() {}

    // 특정 사용자의 출입 기록 개수 조회
    public int getEntryCountByUser(Long user_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM entry_logs WHERE user_num = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, user_num);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("사용자의 출입 기록 개수 조회 중 오류 발생", e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        return count;
    }

    // 특정 사용자의 최근 출입 기록 조회 (최대 n개)
    public List<EntryVO> getRecentEntryLogsByUser(Long user_num, int limit) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<EntryVO> entryLogs = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT entry_time " +
                         "FROM ( " +
                         "    SELECT e.entry_time, ROWNUM AS rnum " +
                         "    FROM entry_logs e " +
                         "    WHERE e.user_num = ? " +
                         "    ORDER BY e.entry_time DESC " +
                         ") " +
                         "WHERE rnum <= ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, user_num);
            pstmt.setInt(2, limit);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                EntryVO entry = new EntryVO();
                entry.setEntryTime(rs.getTimestamp("entry_time"));
                entryLogs.add(entry);
            }
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }

        return entryLogs;
    }

 // 특정 사용자의 출입 기록 조회 (페이징 처리)
    public List<EntryVO> getEntryLogsByUser(Long user_num, int startRow, int rowsPerPage) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<EntryVO> entryLogs = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT entry_time " +
                         "FROM ( " +
                         "    SELECT e.entry_time, ROW_NUMBER() OVER (ORDER BY e.entry_time DESC) AS rnum " +
                         "    FROM entry_logs e " +
                         "    WHERE e.user_num = ? " +
                         ") " +
                         "WHERE rnum BETWEEN ? AND ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, user_num);
            pstmt.setInt(2, startRow + 1); // 페이징 시작 (1-based index)
            pstmt.setInt(3, startRow + rowsPerPage);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                EntryVO entry = new EntryVO();
                entry.setEntryTime(rs.getTimestamp("entry_time"));
                entryLogs.add(entry);
            }
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }

        return entryLogs;
    }
}
