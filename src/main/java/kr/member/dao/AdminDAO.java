package kr.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.appl.vo.ApplVO;
import kr.board.vo.BoardVO;
import kr.membership.vo.MembershipVO;
import kr.mydiet.vo.DietPlanVO;
import kr.order.vo.OrderVO;
import kr.util.DBUtil;

public class AdminDAO {
    private static AdminDAO instance = new AdminDAO();

    public static AdminDAO getInstance() {
        return instance;
    }

    private AdminDAO() {}

    // 최신 게시글 5개 가져오기
    public List<BoardVO> getRecentPosts() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BoardVO> list = new ArrayList<>();

        String sql = "SELECT * FROM ( " +
                     "    SELECT b.*, ROWNUM rnum " +
                     "    FROM ( " +
                     "        SELECT * " +
                     "        FROM board " +
                     "        ORDER BY board_regdate DESC " +
                     "    ) b " +
                     "    WHERE ROWNUM <= 5 " +
                     ") WHERE rnum <= 5";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BoardVO board = new BoardVO();
                board.setBoard_num(rs.getLong("board_num"));
                board.setBoard_title(rs.getString("board_title"));
                board.setBoard_regdate(rs.getString("board_regdate"));
                list.add(board);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("게시글 조회 중 오류 발생: " + e.getMessage());
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        return list;
    }

    // 최근 지원 신청 5개 가져오기
    public List<ApplVO> getRecentApplications() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ApplVO> list = new ArrayList<>();

        // application과 suser_detail 테이블을 조인하여 name 열을 가져옴
        String sql = "SELECT appl.appl_num, appl.field, appl.career, appl.appl_center, " +
                     "       appl.appl_regdate, appl.appl_status, detail.name " +
                     "FROM ( " +
                     "    SELECT a.*, ROWNUM rnum " +
                     "    FROM ( " +
                     "        SELECT * " +
                     "        FROM application a " +
                     "        ORDER BY a.appl_regdate DESC " +
                     "    ) a " +
                     "    WHERE ROWNUM <= 5 " +
                     ") appl " +
                     "JOIN suser_detail detail ON appl.user_num = detail.user_num";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ApplVO appl = new ApplVO();
                appl.setAppl_num(rs.getLong("appl_num"));
                appl.setField(rs.getInt("field"));
                appl.setCareer(rs.getInt("career"));
                appl.setAppl_center(rs.getInt("appl_center"));
                appl.setAppl_regdate(rs.getDate("appl_regdate"));
                appl.setAppl_status(rs.getInt("appl_status"));
                appl.setName(rs.getString("name"));
                list.add(appl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("지원 신청 조회 중 오류 발생: " + e.getMessage());
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        return list;
    }
    public List<MembershipVO> getAllMemberships() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MembershipVO> list = new ArrayList<>();

        String sql = "SELECT " +
                     "    mo.order_num, " +
                     "    mo.user_num, " +
                     "    mo.typeId, " +
                     "    mo.price, " +
                     "    mo.order_date, " +
                     "    mt.DURATION_MONTHS AS duration_months, " +
                     "    sd.name AS user_name " +
                     "FROM membershipOrder mo " +
                     "JOIN membership_types mt ON mo.typeId = mt.TYPE_ID " +
                     "JOIN suser_detail sd ON mo.user_num = sd.user_num";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                MembershipVO vo = new MembershipVO();
                vo.setMem_num(rs.getLong("order_num"));
                vo.setUser_num(rs.getLong("user_num"));
                vo.setMem_type(rs.getInt("typeId"));
                vo.setMem_price(rs.getInt("price"));
                vo.setMem_startdate(rs.getDate("order_date"));
                vo.setDuration_months(rs.getInt("duration_months"));
                vo.setUser_name(rs.getString("user_name"));
                list.add(vo);	
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("회원권 조회 중 오류 발생", e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        return list;
    }

    public int getMembershipCount() throws Exception {
        String sql = "SELECT COUNT(*) FROM membershipOrder";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("회원권 개수 조회 중 오류 발생", e);
        }
        return 0;
    }
    public List<MembershipVO> getMembershipsByPage(int startRow, int pageSize) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MembershipVO> list = new ArrayList<>();

        String sql = "SELECT * " +
                     "FROM ( " +
                     "    SELECT inner_query.*, ROWNUM rnum " +
                     "    FROM ( " +
                     "        SELECT mo.order_num, " +
                     "               mo.user_num, " +
                     "               mo.typeId, " +
                     "               mo.price, " +
                     "               mo.order_date, " +
                     "               mt.DURATION_MONTHS AS duration_months, " +
                     "               sd.name AS user_name " +
                     "        FROM membershipOrder mo " +
                     "        JOIN membership_types mt ON mo.typeId = mt.TYPE_ID " +
                     "        JOIN suser_detail sd ON mo.user_num = sd.user_num " +
                     "        ORDER BY mo.order_date DESC " +
                     "    ) inner_query " +
                     "    WHERE ROWNUM <= ? " +
                     ") " +
                     "WHERE rnum > ?";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, startRow + pageSize); // 페이지 끝 범위
            pstmt.setInt(2, startRow); // 페이지 시작 범위

            rs = pstmt.executeQuery();
            while (rs.next()) {
                MembershipVO vo = new MembershipVO();
                vo.setMem_num(rs.getLong("order_num"));
                vo.setUser_num(rs.getLong("user_num"));
                vo.setMem_type(rs.getInt("typeId"));
                vo.setMem_price(rs.getInt("price"));
                vo.setMem_startdate(rs.getDate("order_date"));
                vo.setDuration_months(rs.getInt("duration_months"));
                vo.setUser_name(rs.getString("user_name"));
                list.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("회원권 조회 중 오류 발생", e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        return list;
    }
    public List<OrderVO> getRecentOrdersForAdmin() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<OrderVO> list = new ArrayList<>();
        String sql = null;

        try {
            conn = DBUtil.getConnection();
            sql = "SELECT * FROM (SELECT o.order_num, o.order_total, o.reg_date, "
                + "o.status, od.goods_name FROM orders o "
                + "JOIN (SELECT order_num, LISTAGG(goods_name, ',') WITHIN GROUP (ORDER BY goods_name) goods_name "
                + "FROM order_detail GROUP BY order_num) od ON o.order_num = od.order_num "
                + "ORDER BY o.reg_date DESC) WHERE rownum <= 5";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderVO order = new OrderVO();
                order.setOrder_num(rs.getLong("order_num"));
                order.setOrder_total(rs.getInt("order_total"));
                order.setReg_date(rs.getDate("reg_date"));
                order.setStatus(rs.getInt("status"));
                order.setGoods_name(rs.getString("goods_name"));
                list.add(order);
            }
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        return list;
    }
    public List<DietPlanVO> getRecentDietPlans() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DietPlanVO> recentDietPlans = new ArrayList<>();
        String sql = "SELECT DIETID, FOODNAME, USER_NUM, DIET_COMMENT "
                   + "FROM (SELECT * FROM DIETPLAN ORDER BY DIETID DESC) "
                   + "WHERE ROWNUM <= ?";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 5); // 미리보기 5개
            rs = pstmt.executeQuery();

            while (rs.next()) {
                DietPlanVO dietPlan = new DietPlanVO();
                dietPlan.setDietId(rs.getLong("DIETID"));
                dietPlan.setFoodName(rs.getString("FOODNAME"));
                dietPlan.setUserNum(rs.getLong("USER_NUM"));
                dietPlan.setDietComment(rs.getInt("DIET_COMMENT"));
                recentDietPlans.add(dietPlan);
            }
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        return recentDietPlans;
    }
}

