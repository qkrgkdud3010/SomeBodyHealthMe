package kr.friend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.friend.vo.FriendVO;

import kr.util.DBUtil;

public class FriendDAO {

	//싱글턴 패턴
	private static FriendDAO instance = new FriendDAO();

	public static FriendDAO getInstance() {
		return instance;
	}
	private FriendDAO() {}
	private Connection conn;
	private PreparedStatement pstmt;


	//받은 요청 확인하는 dao
	public List<Integer> getReceivedFriendRequests(int receiverNum) {
		List<Integer> friendRequests = new ArrayList<>();
		String sql = "SELECT sender_num FROM friend WHERE receiver_num = ? AND status = '1'";  // 상태가 '요청'인 친구 목록

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, receiverNum);  // 받은 사람 번호

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				// 보낸 사람 번호 (친구 요청을 보낸 사람)
				friendRequests.add(rs.getInt("sender_num"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return friendRequests;
	}





	// 친구 요청 추가 (보내는 사람과 받는 사람) 요청 보내기
	public String sendFriendRequest(Long user_num, Long receiver) throws Exception {
	    String isRequestSent = "";
	    ResultSet rs = null;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    PreparedStatement pstmt2 = null;
	    
	    try {
	        conn = DBUtil.getConnection();
	        
	        // 1. 중복 친구 요청 확인 쿼리
	        String sql2 = "SELECT * FROM friend WHERE user_num = ? AND receiver_num = ?";
	        pstmt2 = conn.prepareStatement(sql2);
	        pstmt2.setLong(1, user_num);
	        pstmt2.setLong(2, receiver);
	        rs = pstmt2.executeQuery();

	        // 2. 친구 관계가 없을 경우에만 친구 요청을 보낸다.
	        if (!rs.next()) {
	            // 3. 친구 요청 쿼리
	            String sql = """
	                INSERT INTO friend (friend_num, created_at, user_num, receiver_num, status)
	                SELECT seq_friend.nextval, SYSDATE, ?, ?, '1'
	                FROM DUAL
	                WHERE NOT EXISTS (
	                    -- 내가 상대방에게 요청을 보낸 경우 또는 상대방이 나에게 요청을 보낸 경우
	                    SELECT 1
	                    FROM friend
	                    WHERE (user_num = ? AND receiver_num = ? AND status = 1)  
	                       OR (user_num = ? AND receiver_num = ? AND status = 1)OR
	                       (user_num = ? AND receiver_num = ? AND status = 2)  
	                       OR (user_num = ? AND receiver_num = ? AND status = 2)  
	                )
	            """;

	            pstmt = conn.prepareStatement(sql);
	            pstmt.setLong(1, user_num);
	            pstmt.setLong(2, receiver);
	            pstmt.setLong(3, user_num);
	            pstmt.setLong(4, receiver);
	            pstmt.setLong(5, receiver);
	            pstmt.setLong(6, user_num);
	            pstmt.setLong(7, user_num);
	            pstmt.setLong(8, receiver);
	            pstmt.setLong(9, receiver);
	            pstmt.setLong(10, user_num);

	            int rowsAffected = pstmt.executeUpdate();

	            // 4. 실행 결과에 따른 응답
	            if (rowsAffected > 0) {
	                isRequestSent = "success";
	            } else {
	                isRequestSent = "duple";
	            }
	        } else {
	            isRequestSent = "duple";
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();  // 예외 메시지 출력
	        throw new Exception("Error while sending friend request: " + e.getMessage());
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (pstmt2 != null) pstmt2.close();
	            if (rs != null) rs.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return isRequestSent;
	}



	public String sendFriendRequest2(Long user_num, Long receiver) throws Exception {
		String isRequestSent = "";
		ResultSet rs=null;
		conn=  DBUtil.getConnection();

		String sql2= "delete from friend  where user_num=? and receiver_num=? ";

		PreparedStatement pstmt2=null;
		try {


			pstmt2= conn.prepareStatement(sql2);
			pstmt2.setLong(1, user_num);
			pstmt2.setLong(2, receiver);

			pstmt2.executeQuery();

			isRequestSent ="success";
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return isRequestSent;
	}


	// 친구 추가 메서드



	public FriendDAO(Connection conn) {
		this.conn = conn;
	}


	public boolean addFriend(int senderNum, int receiverNum, String status) {
		boolean isAdded = false;

		String sql = "INSERT INTO friend (friend_num,created_at,user_num, status) " +
				"VALUES (seq_friend.nextval, ?, ?, SYSDATE)";

		try {
			pstmt = conn.prepareStatement(sql);

			// 보낸 사람 (sender_num)
			pstmt.setInt(1, senderNum);

			// 친구 상태 (status: 1=요청, 2=수락, 3=거절)
			pstmt.setString(2, status);

			// 쿼리 실행
			int result = pstmt.executeUpdate();

			if (result > 0) {
				// 친구 추가 성공
				isAdded = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return isAdded;
	}

	//사용 유저들 나열시키기
	public int getMemberCountByAdmin(String keyfield,
			String keyword)
					throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;

		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();

			if (keyword != null && !"".equals(keyword)) {
				//검색 처리
				if (keyfield.equals("1")) sub_sql += "WHERE name LIKE '%' || ? || '%'";
				else if (keyfield.equals("2")) sub_sql += "WHERE nick_name LIKE '%' || ? || '%'";

			}

			//SQL문 작성
			sql = "SELECT COUNT(*) FROM suser LEFT OUTER JOIN "
					+ "suser_detail USING(user_num) " + sub_sql;
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(1, keyword);
			}
			//SQL문 실행
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



	public  List<FriendVO> getMember(int start,
			int end,String keyfield,String keyword,Long user_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sub_sql="";
		String sql = null;
		List<FriendVO> friends = new ArrayList<>();
		int cnt=0;
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성




			if (keyword != null && !"".equals(keyword)) {
				//검색 처리
				if (keyfield.equals("1")) sub_sql += "WHERE name LIKE '%' || ? || '%'";
				else if (keyfield.equals("2")) sub_sql += "WHERE nick_name LIKE '%' || ? || '%'";

			}

			// Add the user number condition


			// SQL 문 작성
			sql = """
					SELECT user_num, nick_name, center_num, status2, status, rnum, name
FROM (
    SELECT a.user_num, 
           a.nick_name, 
           a.name, 
           a.center_num, 
           a.status, 
           a.status2, 
           rownum AS rnum
    FROM (
        SELECT sd.user_num AS user_num,
               sd.nick_name AS nick_name,
               sd.center_num AS center_num,
               s.status AS status2,              -- suser_detail 테이블의 status
               sd.name as name,
               NVL(f.status, 'None') AS status -- friend 테이블의 status
        FROM suser s
        LEFT JOIN suser_detail sd 
            ON s.user_num = sd.user_num               -- suser와 suser_detail 연결
        LEFT JOIN friend f 
            ON (s.user_num = f.receiver_num AND f.user_num = ?) -- friend 테이블 조인
        """ + sub_sql + """
        ORDER BY s.user_num DESC NULLS LAST
    ) a
)
WHERE rnum >= ? 
  AND rnum <= ? 
  AND user_num != ? 
  AND user_num != 35
					        		""";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(++cnt, user_num);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}

			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			pstmt.setLong(++cnt, user_num);


			//PreparedStatement 객체 생성

			//?에 데이터 바인딩

			//SQL문 실행
			rs = pstmt.executeQuery();
			while(rs.next()) {
				FriendVO friend = new FriendVO();
				friend = new FriendVO();
				friend.setUser_Num(rs.getLong("user_Num"));
				friend.setNick_Name(rs.getString("nick_name"));
				friend.setName(rs.getString("name"));
				friend.setCenter_Num(rs.getInt("center_num"));
				friend.setStatus(rs.getString("status"));
				friend.setStatus2(rs.getString("status2"));
			
				friends.add(friend);
				
				
				
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return friends;
	}


	public  List<FriendVO> centerGetMember(int start,
			int end,String keyfield,String keyword,int center_num ,Long user_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sub_sql="";
		String sql = null;
		List<FriendVO> friends = new ArrayList<>();
		int cnt=0;
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성




			if(keyword != null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += "WHERE login_id LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "WHERE name LIKE '%' || ? || '%'";
				else if(keyfield.equals("3")) sub_sql += "WHERE nick_name LIKE '%' || ? || '%'";
			}	
			sub_sql += (sub_sql.contains("WHERE") ? " AND" : " WHERE") + " center_num = ?";
			//SQL문 작성
			sql = """
					SELECT user_num, nick_name, center_num, status, rnum,name
					FROM (
					    SELECT a.user_num, a.nick_name,a.name, a.center_num, a.status, rownum AS rnum  -- rownum을 별칭으로 지정
					    FROM (
					        SELECT s.user_num AS user_num,
					               s.nick_name AS nick_name,
					               s.center_num AS center_num,
					               s.name,
					               NVL(f.status, 'None') AS status
					        FROM suser_detail s
					        LEFT JOIN friend f 
					            ON (s.user_num = f.receiver_num AND f.user_num = ?)  -- 로그인한 사용자가 친구 요청을 보낸 경우
					       -- 로그인한 사용자가 친구 요청을 받은 경우
					        """ + sub_sql + """ 
					        		ORDER BY s.user_num DESC NULLS LAST
					        		) a
					        		)
					        		WHERE rnum >= ? AND rnum <= ? and user_num!=? and user_num!=35
					        		""";

			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(++cnt, user_num);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, center_num);
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			pstmt.setLong(++cnt, user_num);

			//PreparedStatement 객체 생성

			//?에 데이터 바인딩

			//SQL문 실행
			rs = pstmt.executeQuery();
			while(rs.next()) {
				FriendVO friend = new FriendVO();
				friend = new FriendVO();
				friend.setUser_Num(rs.getLong("user_Num"));
				friend.setNick_Name(rs.getString("nick_name"));
				friend.setName(rs.getString("name"));
				friend.setCenter_Num(rs.getInt("center_num"));
				friend.setStatus(rs.getString("status"));
				friends.add(friend);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return friends;
	}







	public List<FriendVO> updateGetMember(Long user_num) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    List<FriendVO> friends = new ArrayList<>();

	    try {
	        // 커넥션풀로부터 커넥션을 할당
	        conn = DBUtil.getConnection();

	        // SQL 문 작성
	        sql = """
	                SELECT s.user_num AS user_num,
	                       s.nick_name AS nick_name,
	                       s.center_num AS center_num,
	                       NVL(f.status, 'None') AS status,
	                       s.name AS name
	                FROM suser_detail s
	                JOIN friend f 
	                  ON s.user_num = f.user_num
	                WHERE f.receiver_num = ? 
	                  AND f.user_num != ?  
	                  AND NVL(f.status, 'None') = '1' 
	                ORDER BY s.user_num DESC
	              """;

	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, user_num);  // 요청을 받은 사람 (user_num)
	        pstmt.setLong(2, user_num);  // 자기 자신은 제외

	        // SQL문 실행
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            FriendVO friend = new FriendVO();
	            friend.setUser_Num(rs.getLong("user_num"));
	            friend.setNick_Name(rs.getString("nick_name"));
	            friend.setName(rs.getString("name"));
	            friend.setCenter_Num(rs.getInt("center_num"));
	            friend.setStatus(rs.getString("status"));
	            friends.add(friend);
	        }
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }

	    return friends;
	}


	
	
	public String sulag(Long user_num, Long receiver) throws Exception {
	    String isRequestSent = "failure";  // 기본값을 'failure'로 설정
	  
	    Connection conn = null;
	    PreparedStatement pstmt2 = null;

	    try {
	        conn = DBUtil.getConnection();
	        String sql2 = "UPDATE friend SET status=2 WHERE (user_num=? AND receiver_num=?)or(user_num=? AND receiver_num=?)";
	        pstmt2 = conn.prepareStatement(sql2);
	        pstmt2.setLong(1, user_num);
	        pstmt2.setLong(2, receiver);
	        pstmt2.setLong(3, receiver);
	        pstmt2.setLong(4, user_num);
	        // 쿼리 실행 후 반환된 행의 수를 확인
	        int rowsAffected = pstmt2.executeUpdate();

	        // 행이 하나 이상 업데이트되었으면 성공
	        if (rowsAffected > 0) {
	            isRequestSent = "success";
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // 예외를 로깅하거나 처리
	    } finally {
	        // 리소스를 적절히 닫음
	        try {
	            if (pstmt2 != null) pstmt2.close();
	            if (conn != null) conn.close();  // 연결도 닫아야 함
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return isRequestSent;
	}

	
}
