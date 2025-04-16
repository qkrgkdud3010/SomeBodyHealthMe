package kr.appl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.appl.vo.ApplVO;
import kr.util.DBUtil;

public class ApplDAO {
	//싱글턴 패턴
	private static ApplDAO instance = new ApplDAO();

	public static ApplDAO getInstance() {
		return instance;
	}
	private ApplDAO() {}

	//지원 하기
	public void insertAppl(ApplVO appl) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql;

		try {
			conn = DBUtil.getConnection();

			sql = "INSERT INTO application"
					+ "(appl_num, field, career, source, appl_center, "
					+ " content, appl_attachment, user_num) "
					+ "VALUES(appl_seq.nextval,?,?,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, appl.getField());
			pstmt.setInt(2, appl.getCareer());
			pstmt.setString(3, appl.getSource());
			pstmt.setInt(4, appl.getAppl_center());
			pstmt.setString(5, appl.getContent());
			pstmt.setString(6, appl.getAppl_attachment());
			pstmt.setLong(7, appl.getUser_num());

			pstmt.executeUpdate();


		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}

	}
	
	//나의 지원 목록 보기
	public List<ApplVO> getMyApplList(long user_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ApplVO> list = new ArrayList<ApplVO>();
		String sql;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM application WHERE user_num = ? ORDER BY appl_num DESC";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, user_num);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ApplVO appl = new ApplVO();
				appl.setAppl_num(rs.getLong("appl_num"));//지원번호
				appl.setField(rs.getInt("field"));//지원분야
				appl.setAppl_center(rs.getInt("appl_center"));//지원지점
				appl.setAppl_regdate(rs.getDate("appl_regdate"));//등록일
				appl.setAppl_status(rs.getInt("appl_status"));//관리자 확인상태
				appl.setAppl_modifydate(rs.getDate("appl_modifydate"));//변경일	
				String content = rs.getString("content");
				if(content.length() >= 15) content = content.substring(0,15) + "....";
				appl.setContent(content);
				list.add(appl);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;		
	}
	
	//나의 미확인 지원 개수
	public ApplVO getMyUncheckedappl(long user_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ApplVO unchecked= null;
		String sql;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT APPL_NUM FROM application WHERE user_num = ? AND appl_status = 0";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, user_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				unchecked = new ApplVO();
				unchecked.setAppl_num(rs.getLong("appl_num"));				
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return unchecked;
	}
	
	//지원 사항 수정하기
	public void updateAppl(ApplVO appl) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql;
		String sub_sql ="";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			if(appl.getAppl_attachment() != null && !"".equals(appl.getAppl_attachment())) {
				sub_sql = " ,appl_attachment = ? ";
			}
			
			sql = "UPDATE application SET field = ?, career= ?, content=?, source=?, appl_center=? " + sub_sql + " , appl_modifydate = SYSDATE "
					+ "WHERE appl_num = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(++cnt, appl.getField());
			pstmt.setInt(++cnt, appl.getCareer());
			pstmt.setString(++cnt, appl.getContent());
			pstmt.setString(++cnt, appl.getSource());
			pstmt.setInt(++cnt, appl.getAppl_center());
			if(appl.getAppl_attachment() != null && !"".equals(appl.getAppl_attachment())) {
				pstmt.setString(++cnt, appl.getAppl_attachment());
			}
			pstmt.setLong(++cnt, appl.getAppl_num());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//지원하기 수정 파일 삭제 처리
	public void deleteAttachment(long appl_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "UPDATE application SET appl_attachment = '' WHERE appl_num = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, appl_num);
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	

	//총 지원 개수 구하기 - 관리자
	public int getApplicationCount(String name,ArrayList<String> keys) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		String sub_sql = "";
		int cnt = 0;
		int count = 0;

		try {
			conn = DBUtil.getConnection();
			
			if(name == null) name ="";
			sub_sql += "WHERE name LIKE '%' || ? || '%' " ;
			for(int i=1 ; i<keys.size(); i += 2) {
				if(keys.get(i) != null) {//숫자가 넘어 왔어
					int keyValue = Integer.parseInt(keys.get(i));
					 if(keyValue != 9)  sub_sql += "AND " + keys.get(i-1) + " =  ? ";
				}
			}			
			
			sql = "SELECT COUNT(*) FROM application JOIN (SELECT * FROM suser LEFT OUTER JOIN suser_detail USING(user_num)) USING(user_num) " + sub_sql;
			
			pstmt = conn.prepareStatement(sql);				
			
			
			pstmt.setString(++cnt, name);
			
			for(int i=1 ; i<keys.size(); i += 2) {
				if(keys.get(i) != null) {//숫자가 넘어 왔어
					int keyValue = Integer.parseInt(keys.get(i));
					if(keyValue != 9) pstmt.setInt(++cnt, keyValue);			
				}
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}				
		return count;
	}


	//지원 목록 보기 관리자
	public List<ApplVO> getListByAdmin(int start,int end,String name,ArrayList<String> keys) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		int cnt = 0;
		String sub_sql="";
		List<ApplVO> list = new ArrayList<ApplVO>();


		try {
			conn = DBUtil.getConnection();			
			
			for(int i=1 ; i<keys.size(); i += 2) {
				if(keys.get(i) != null) {//숫자가 넘어 왔어
					int keyValue = Integer.parseInt(keys.get(i));
					 if(keyValue != 9)  sub_sql += "AND " + keys.get(i-1) + " = ?" ;	
				}
			}	
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM application JOIN "
					+ "(SELECT * FROM suser LEFT OUTER JOIN suser_detail USING(user_num)) USING(user_num) WHERE name LIKE '%' || ? || '%'" + sub_sql + " ORDER BY appl_num DESC) a)"
							+ " WHERE rnum >= ? AND rnum <= ?";

			pstmt = conn.prepareStatement(sql);
			
			if(name != null) {
				pstmt.setString(++cnt, name);
			}else {
				pstmt.setString(++cnt, "");
			}
			
			
			for(int i=1 ; i<keys.size(); i += 2) {
				if(keys.get(i) != null) {//숫자가 넘어 왔어
					int keyValue = Integer.parseInt(keys.get(i));
					if(keyValue != 9) {
						pstmt.setInt(++cnt, keyValue);		
						System.out.println("cnt = " + cnt + ", value = " + keyValue);
					}
				}
			}
		
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<ApplVO>();

			while(rs.next()) {
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

		}catch(Exception e) {
			throw new Exception(e);
		}finally{
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

	//상세 지원 보기
	public ApplVO getAppl(long appl_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ApplVO appl = null;
		String sql;

		try {
			conn = DBUtil.getConnection();

			sql = "SELECT * FROM application JOIN (SELECT * FROM suser LEFT OUTER JOIN suser_detail USING(user_num)) USING(user_num) WHERE appl_num = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, appl_num);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				appl = new ApplVO();
				appl.setAppl_attachment(rs.getString("appl_attachment"));
				appl.setAppl_center(rs.getInt("appl_center"));
				appl.setCareer(rs.getInt("career"));
				appl.setContent(rs.getString("content"));
				appl.setField(rs.getInt("field"));
				appl.setSource(rs.getString("source"));
				appl.setAppl_status(rs.getInt("appl_status"));
				appl.setAppl_num(rs.getLong("appl_num"));
				appl.setAppl_regdate(rs.getDate("appl_regdate"));
				appl.setAppl_modifydate(rs.getDate("appl_modifydate"));
				appl.setName(rs.getString("name"));
				appl.setPhone(rs.getString("phone"));
				appl.setBirth_date(rs.getString("birth_date"));
				appl.setUser_num(rs.getLong("user_num"));
				appl.setStatus(rs.getInt("status"));
				appl.setLogin_id(rs.getString("login_id"));
			}			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return appl;
	}


	//관리자 확인, 관리자 전환된 회원은 (appl_status 변경x )
	public void updateAppl_status(long appl_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql;

		try {
			conn = DBUtil.getConnection();

			sql = "UPDATE application SET appl_status = 1  WHERE appl_num = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, appl_num);

			pstmt.executeUpdate();


		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//관리자 전환
	public void updateStatus(ApplVO appl) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		String sql;

		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			//관리자로 전환시 appl_status(확인상태)를 3으로 바꿈(지원내용을 관리자가 선택한 내용으로 바꿈)
			sql = "UPDATE application SET field = ?, appl_center = ?, career =?, appl_status = 3 WHERE appl_num = ?";
			
			pstmt = conn.prepareStatement(sql);			
			pstmt.setInt(1, appl.getField());
			pstmt.setInt(2, appl.getAppl_center());
			pstmt.setInt(3, appl.getCareer());
			pstmt.setLong(4, appl.getAppl_num());
			
			pstmt.executeUpdate();
			
			//등급 변환
			sql = "UPDATE suser SET status = ? WHERE user_num=? ";

			pstmt2 = conn.prepareStatement(sql);

			pstmt2.setInt(1, appl.getField());
			pstmt2.setLong(2, appl.getUser_num());

			pstmt2.executeUpdate();
			
			//센터번호 변환
			sql ="UPDATE suser_detail SET center_num = ? WHERE user_num=?";
			pstmt3 = conn.prepareStatement(sql);
			
			pstmt3.setInt(1, appl.getAppl_center());
			pstmt3.setLong(2, appl.getUser_num());
			
			//전환시 다른 지원목록 전환된 회원이라고 표시
			sql = "UPDATE application SET appl_status = 4 WHERE user_num =? AND appl_num != ?";
			pstmt4 = conn.prepareStatement(sql);
			pstmt4.setLong(1, appl.getUser_num());
			pstmt4.setLong(2, appl.getAppl_num());
			pstmt4.executeUpdate();
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}



}
