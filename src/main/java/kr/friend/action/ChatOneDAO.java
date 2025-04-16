package kr.friend.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import kr.friend.vo.FriendVO;
import kr.friend.action.ChatOneDAO;
import kr.util.DBUtil;

public class ChatOneDAO {

	private static ChatOneDAO instance = new ChatOneDAO();

	public static ChatOneDAO getInstance() {
		return instance;
	}
	private ChatOneDAO() {}


	//전체 채팅 목록/검색 목록
	public List<FriendVO> getChatList(int start, int end,Long user_num)
			throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FriendVO> list =new ArrayList<FriendVO>();;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			sql="""

					SELECT
					friend_user.user_num AS user_num,
					friend_user.nick_name AS nick_name,
					friend_user.name AS name,
					latest_messages.latest_message_date AS latest_message_date

					FROM
					(
					SELECT
					CASE 
					WHEN f.user_num = ? THEN f.receiver_num
					ELSE f.user_num
					END AS friend_user_num
					FROM
					friend f
					WHERE
					(f.receiver_num = ? OR f.user_num = ?)
					AND f.status = 2
					) friend_list
					LEFT JOIN (
					SELECT
					sender_num,
					MAX(message_date) AS latest_message_date
					FROM
					messages
					GROUP BY
					sender_num
					) latest_messages ON friend_list.friend_user_num = latest_messages.sender_num
					LEFT JOIN
					suser_detail friend_user ON friend_list.friend_user_num = friend_user.user_num
					ORDER BY
					latest_messages.latest_message_date DESC NULLS LAST

					""";
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, user_num);
			pstmt.setLong(2, user_num);
			pstmt.setLong(3, user_num);

			rs=pstmt.executeQuery();
			while(rs.next()) {
				FriendVO friend = new FriendVO();
				friend = new FriendVO();
				friend.setUser_Num(rs.getLong("user_num"));
				friend.setName(rs.getString("name"));
				;

				list.add(friend);
			}

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

	
	 public List<FriendVO> getReceivedMessages(long userNum) throws Exception {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        List<FriendVO> list = null;

	        try {
	            conn = DBUtil.getConnection();
	            String sql = """
SELECT "SENDER_NUM"
FROM (
    SELECT "SENDER_NUM", 
           "MESSAGE_DATE", 
           ROW_NUMBER() OVER (PARTITION BY "SENDER_NUM" ORDER BY "MESSAGE_DATE" ASC) AS rn
    FROM "MESSAGES"
    WHERE "RECEIVER_NUM" = ?
) subquery
WHERE rn = 1
ORDER BY "MESSAGE_DATE" ASC



	            		""";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setLong(1, userNum);
	            rs = pstmt.executeQuery();

	            list = new ArrayList<>();
	            while (rs.next()) {
	            	FriendVO friend = new FriendVO();
	 
					friend = new FriendVO();
					friend.setUser_Num(rs.getLong("SENDER_NUM"));
			
	                list.add(friend);
	            }
	        } finally {
	            DBUtil.executeClose(rs, pstmt, conn);
	        }
	        return list;
	    }

	

	public void insertChat(ChatOneVO chat)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO Messages (message_num, sender_num, receiver_num, message_text, is_read,message_date) " +
					"VALUES (seq_messages.nextval, ?, ?, ?, 'N',sysdate)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, chat.getSender_num());
			pstmt.setLong(2, chat.getReceiver_num());
			pstmt.setString(3, chat.getMessage_text());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	public List<ChatOneVO> getChatDetail(long send_num,
			long recv_num)
					throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		List<ChatOneVO> list = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//오토커밋 해제
			conn.setAutoCommit(false);

			//내가 보낸 메시지가 아닌 상대방이 보낸 메시지를 읽었을
			//때 read_check를 1에서 0으로 변경해야 하기때문에
			//send_num=recv_num AND recv_num=send_num 으로 셋팅
			sql = "UPDATE messages SET is_read=0 WHERE "
					+ "SENDER_NUM=? AND RECEIVER_NUM=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, recv_num);
			pstmt.setLong(2, send_num);
			//SQL문 실행
			pstmt.executeUpdate();

			//내가 보낸 메시지뿐만 아니라 상대방이 보낸 메시지도 
			//읽어와야 하기때문에
			//(send_num=send_num AND recv_num=recv_num) OR
			//(send_num=recv_num AND recv_num=send_num)
			sql = "SELECT * FROM messages c JOIN suser_detail m "
					+ "ON c.SENDER_NUM=m.user_num WHERE "
					+ "(SENDER_NUM=? AND RECEIVER_NUM=?) OR "
					+ "(SENDER_NUM=? AND RECEIVER_NUM=?) "
					+ "ORDER BY message_date ASC";
			//PreparedStatement 객체 생성
			pstmt2 = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt2.setLong(1, send_num);
			pstmt2.setLong(2, recv_num);
			pstmt2.setLong(3, recv_num);
			pstmt2.setLong(4, send_num);
			//SQL문 실행
			rs = pstmt2.executeQuery();
			list = new ArrayList<ChatOneVO>();
			while(rs.next()) {
				ChatOneVO chat = new ChatOneVO();
				chat.setMessage_num(rs.getLong("message_num"));
				chat.setSender_num(rs.getLong("sender_num"));
				chat.setReceiver_num(rs.getLong("receiver_num"));
				chat.setMessage_text(rs.getString("message_text"));
				chat.setIs_read(rs.getString("is_read"));
				chat.setMessage_date(rs.getString("message_date"));
				chat.setName(rs.getString("name"));
			

				list.add(chat);
			}
			//SQL문이 모두 성공하면 commit
			conn.commit();
		}catch(Exception e) {
			//SQL문이 하나라도 실패하면 rollback
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, null);
			DBUtil.executeClose(rs, pstmt2, conn);
		}
		return list;
	}
}
