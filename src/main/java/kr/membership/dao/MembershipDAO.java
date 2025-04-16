package kr.membership.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.membership.vo.MembershipVO;
import kr.util.DBUtil;

public class MembershipDAO {
    // 싱글턴 패턴 구현
    private static MembershipDAO instance = new MembershipDAO();

    public static MembershipDAO getInstance() {
        return instance;
    }

    private MembershipDAO() {}

    // 회원권 구매 프로시저 호출
    public String buyMembershipByType(long user_num, int typeId) throws Exception {
        String sql = "{ call BUY_MEMBERSHIP_BY_TYPE(?, ?, sysdate) }";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
        	conn.setAutoCommit(false); 
            // 프로시저 매개변수 설정
            cstmt.setLong(1, user_num);  // p_user_id
            cstmt.setInt(2, typeId);  // p_type_id

            // 프로시저 실행
            int rowsAffected =cstmt.executeUpdate();
           

            // 4. 실행 결과에 따른 응답
            String isRequestSent="";
			if (rowsAffected>0) {
                isRequestSent = "success";
            } else {
                isRequestSent = "duple";
            }
			conn.commit();
			return isRequestSent;
        } catch (Exception e) {
            // 에러 로깅
            e.printStackTrace(); 
            throw new Exception("회원권 구매 중 오류 발생", e);
        }
    }
    public void MembershipOrder(int typeId, long user_num, int Price,int payment,int receive_phone)throws Exception{
    	Connection conn=null;
    	String sql=null;
    	PreparedStatement pstmt=null;
    	try {
			conn=DBUtil.getConnection();
			sql="INSERT INTO membershipOrder (order_num, user_num, typeId, receive_phone, price, order_date, payment) VALUES (membershipOrder_seq.NEXTVAL, ?, ?, ?, ?, SYSDATE, ?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, user_num);
			pstmt.setInt(2, typeId);
			pstmt.setInt(3, receive_phone);
			pstmt.setInt(4, Price);
			pstmt.setInt(5, payment);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
    }
    public List<MembershipVO> getMembershipsByUser(long user_num, int startRow, int pageSize) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MembershipVO> list = new ArrayList<>();

        String sql = "SELECT * " +
                     "FROM ( " +
                     "    SELECT mo.order_num, mo.typeId, mo.price, mo.order_date, " +
                     "           mt.DURATION_MONTHS AS duration_months " +
                     "    FROM membershipOrder mo " +
                     "    JOIN membership_types mt ON mo.typeId = mt.TYPE_ID " +
                     "    WHERE mo.user_num = ? " +
                     "    ORDER BY mo.order_date DESC " +
                     " ) " +
                     "WHERE ROWNUM BETWEEN ? AND ?";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, user_num);
            pstmt.setInt(2, startRow + 1); // 1-based index
            pstmt.setInt(3, startRow + pageSize);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                MembershipVO vo = new MembershipVO();
                vo.setMem_num(rs.getLong("order_num"));
                vo.setMem_type(rs.getInt("typeId"));
                vo.setMem_price(rs.getInt("price"));
                vo.setMem_startdate(rs.getDate("order_date"));
                vo.setDuration_months(rs.getInt("duration_months"));
                list.add(vo);
            }
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        return list;
    }
}
