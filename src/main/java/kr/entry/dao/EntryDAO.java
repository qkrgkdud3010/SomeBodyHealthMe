package kr.entry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.entry.vo.EntryVO;
import kr.util.DBUtil;

public class EntryDAO {
    private static EntryDAO instance = new EntryDAO();

    public static EntryDAO getInstance() {
        return instance;
    }

    private EntryDAO() {}

    // 핸드폰 번호로 회원 번호 조회
    public Long getUserNumByPhone(String phone) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Long userNum = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT user_num FROM suser_detail WHERE phone = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                userNum = rs.getLong("user_num");
            }
            System.out.println("조회된 user_num: " + userNum); // 디버깅용 로그
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }

        return userNum;
    }

    // 입장 기록 저장
    public void insertEntryLog(Long userNum, String phone) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO entry_logs (entry_id, user_num, phone_number) VALUES (entry_logs_seq.NEXTVAL, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, userNum);
            pstmt.setString(2, phone);

            pstmt.executeUpdate();
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }

    // 최근 출입 내역 가져오기 (최대 5개)
    public List<EntryVO> getRecentEntries(int limit) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<EntryVO> list = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM ("
                       + "  SELECT e.entry_time, d.name, d.phone "
                       + "  FROM entry_logs e "
                       + "  JOIN suser_detail d ON e.user_num = d.user_num "
                       + "  ORDER BY e.entry_time DESC"
                       + ") WHERE ROWNUM <= ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, limit);
            rs = pstmt.executeQuery();

            list = new ArrayList<>();
            while (rs.next()) {
                EntryVO entry = new EntryVO();
                entry.setEntryTime(rs.getTimestamp("entry_time"));
                entry.setName(rs.getString("name"));
                entry.setPhone(rs.getString("phone"));
                list.add(entry);
            }
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }

        return list;
    }

    // 전체 출입 내역 가져오기 (페이징 처리)
    public List<EntryVO> getAllEntryLogs1() throws Exception {
        List<EntryVO> entryLogs = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT d.name, d.phone, e.entry_time FROM suser_detail d " +
                         "JOIN entry_logs e ON d.user_num = e.user_num " +
                         "ORDER BY e.entry_time DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                EntryVO entry = new EntryVO();
                entry.setName(rs.getString("name"));
                entry.setPhone(rs.getString("phone"));
                entry.setEntryTime(rs.getTimestamp("entry_time"));
                entryLogs.add(entry);
            }
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }

        return entryLogs;
    }
    // 전체 출입 내역 개수 조회
    public int getEntryCount() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        String sql = "SELECT COUNT(*) FROM entry_logs";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        return count;
    }
    public List<EntryVO> getAllEntryLogs() throws Exception {
        List<EntryVO> entryLogs = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT d.name, d.phone, e.entry_time FROM suser_detail d " +
                         "JOIN entry_logs e ON d.user_num = e.user_num " +
                         "ORDER BY e.entry_time DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                EntryVO entry = new EntryVO();
                entry.setName(rs.getString("name"));
                entry.setPhone(rs.getString("phone"));
                entry.setEntryTime(rs.getTimestamp("entry_time")); // 설정
                entryLogs.add(entry);
            }
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }

        return entryLogs;
    }
    public List<EntryVO> getEntryLogsByPage(int page, int rowsPerPage) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<EntryVO> entryLogs = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM ("
                       + "  SELECT e.entry_time, d.name, d.phone, ROWNUM AS rnum"
                       + "  FROM entry_logs e"
                       + "  JOIN suser_detail d ON e.user_num = d.user_num"
                       + "  WHERE ROWNUM <= ?"
                       + ") WHERE rnum > ?";
            pstmt = conn.prepareStatement(sql);

            int startRow = (page - 1) * rowsPerPage;
            pstmt.setInt(1, page * rowsPerPage); // 끝나는 번호
            pstmt.setInt(2, startRow); // 시작 번호

            rs = pstmt.executeQuery();
            while (rs.next()) {
                EntryVO entry = new EntryVO();
                entry.setEntryTime(rs.getTimestamp("entry_time"));
                entry.setName(rs.getString("name"));
                entry.setPhone(rs.getString("phone"));
                entryLogs.add(entry);
            }
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }

        return entryLogs;
    }

    // 총 출입 기록 개수 가져오기
    public int getEntryCount1() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM entry_logs";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }

        return count;
    }
}
