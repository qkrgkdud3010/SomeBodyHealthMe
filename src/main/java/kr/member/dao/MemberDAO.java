package kr.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.member.vo.MemberVO;
import kr.util.DBUtil;
//
public class MemberDAO {
    private static MemberDAO instance = new MemberDAO();

    public static MemberDAO getInstance() {
        return instance;
    }
   
    private MemberDAO() {}

    // 회원 가입
    public void insertMember(MemberVO member) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        ResultSet rs = null;
        String sql = null;
        long userNum = 0;

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            // 회원번호(user_num) 생성
            sql = "SELECT user_seq.NEXTVAL FROM dual";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                userNum = rs.getLong(1);
            }

            // STATUS 설정: 기본값 1 (일반 사용자)
            int status = 1;
            if (member.getStatus() == 4) {  // 입력값에서 STATUS가 4로 설정된 경우
                status = 4;  // 마스터 관리자
            }

            // SUSER 테이블에 데이터 삽입
            sql = "INSERT INTO SUSER (user_num, login_id, status) VALUES (?, ?, ?)";
            pstmt2 = conn.prepareStatement(sql);
            pstmt2.setLong(1, userNum);
            pstmt2.setString(2, member.getLogin_id());
            pstmt2.setInt(3, status); // 설정된 status 값 사용
            pstmt2.executeUpdate();

            // SUSER_DETAIL 테이블에 데이터 삽입
            sql = "INSERT INTO SUSER_DETAIL (user_num, nick_name, name, email, password, phone, registration_date, birth_date, center_num) "
                + "VALUES (?, ?, ?, ?, ?, ?, SYSDATE, ?, ?)";
            pstmt3 = conn.prepareStatement(sql);
            pstmt3.setLong(1, userNum);
            pstmt3.setString(2, member.getNick_name());
            pstmt3.setString(3, member.getName());
            pstmt3.setString(4, member.getEmail());
            pstmt3.setString(5, member.getPassword());
            pstmt3.setString(6, member.getPhone());
            pstmt3.setString(7, member.getBirth_date());

            // 센터 코드 유효성 체크: 센터 코드가 반드시 선택되어야 함
            int centerCode = member.getCenter_num();
            if (centerCode == 0) {
                throw new Exception("센터를 선택하세요.");
            }
            pstmt3.setInt(8, centerCode);

            pstmt3.executeUpdate();
            conn.commit();

        } catch (Exception e) {
            if (conn != null) conn.rollback();
            throw new Exception("회원가입 중 오류 발생: " + e.getMessage(), e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
            DBUtil.executeClose(null, pstmt2, null);
            DBUtil.executeClose(null, pstmt3, null);
        }
    }    
    // 아이디 중복 체크
    public boolean isDuplicateId(String loginId) throws Exception {
        return checkDuplicate("SUSER", "login_id", loginId);
    }

    // 닉네임 중복 체크
    public boolean isDuplicateNickname(String nickName) throws Exception {
        return checkDuplicate("SUSER_DETAIL", "nick_name", nickName);
    }

    // 이메일 중복 체크
    public boolean isDuplicateEmail(String email) throws Exception {
        return checkDuplicate("SUSER_DETAIL", "email", email);
    }

    // 휴대전화 중복 체크
    public boolean isDuplicatePhone(String phone) throws Exception {
        return checkDuplicate("SUSER_DETAIL", "phone", phone);
    }

    // 중복 체크 공통 메서드
    private boolean checkDuplicate(String tableName, String columnName, String value) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isDuplicate = false;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnName + " = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, value);  // 입력값을 파라미터로 바인딩
            System.out.println("Executing query: " + sql);  // 실행되는 쿼리 로그 출력
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Count result: " + count);  // 중복 카운트 출력
                isDuplicate = count > 0;  // 카운트가 0보다 크면 중복
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(columnName + " 중복 체크 중 오류 발생: " + e.getMessage());
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }

        System.out.println("Is Duplicate: " + isDuplicate);  // 최종 중복 여부 출력
        return isDuplicate;
    }
		// 로그인 메서드
    public MemberVO checkLogin(String loginId, String password) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        MemberVO member = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT u.user_num, u.login_id, u.status, d.name, d.nick_name " +
                         "FROM SUSER u " +
                         "JOIN SUSER_DETAIL d ON u.user_num = d.user_num " +
                         "WHERE u.login_id = ? AND d.password = ? AND u.status != 0";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loginId);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                member = new MemberVO();
                member.setUser_num(rs.getLong("user_num"));
                member.setLogin_id(rs.getString("login_id"));
                member.setStatus(rs.getInt("status"));
                member.setName(rs.getString("name"));
                member.setNick_name(rs.getString("nick_name")); // 닉네임 설정
            }
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        return member;
    }
    	//아이디 찾기 메서드
    	public String findAccountByNameAndEmail(String name, String email) throws Exception {
    	    Connection conn = null;
    	    PreparedStatement pstmt = null;
    	    ResultSet rs = null;
    	    String loginId = null;

    	    try {
    	        conn = DBUtil.getConnection();
    	        String sql = "SELECT u.login_id " +
    	                     "FROM SUSER u " +
    	                     "JOIN SUSER_DETAIL d ON u.user_num = d.user_num " +
    	                     "WHERE d.name = ? AND d.email = ?";
    	        pstmt = conn.prepareStatement(sql);
    	        pstmt.setString(1, name);
    	        pstmt.setString(2, email);

    	        rs = pstmt.executeQuery();
    	        if (rs.next()) {
    	            loginId = rs.getString("login_id");
    	        }
    	    } finally {
    	        DBUtil.executeClose(rs, pstmt, conn);
    	    }
    	    return loginId;
    	}
    	//비밀번호 찾기 메서드
    	public String findPasswordByLoginIdAndEmail(String loginId, String email) throws Exception {
    	    Connection conn = null;
    	    PreparedStatement pstmt = null;
    	    ResultSet rs = null;
    	    String password = null;

    	    try {
    	        conn = DBUtil.getConnection();
    	        
    	        String sql = "SELECT d.password FROM SUSER u JOIN SUSER_DETAIL d ON u.user_num = d.user_num WHERE u.login_id = ? AND d.email = ?";
    	        
    	        pstmt = conn.prepareStatement(sql);
    	        pstmt.setString(1, loginId);
    	        pstmt.setString(2, email);

    	        rs = pstmt.executeQuery();
    	        if (rs.next()) {
    	            password = rs.getString("password");
    	        }
    	    } finally {
    	        DBUtil.executeClose(rs, pstmt, conn);
    	    }
    	    return password;
    	}
    	//프로필 상세 조회
    	public MemberVO getUserProfile(long userNum) throws Exception {
    	    Connection conn = null;
    	    PreparedStatement pstmt = null;
    	    ResultSet rs = null;
    	    MemberVO member = null;

    	    try {
    	        conn = DBUtil.getConnection();
    	        String sql = "SELECT u.user_num, u.login_id, u.status, " +
    	                     "d.nick_name, d.name, d.email, d.phone, " +
    	                     "d.birth_date, d.registration_date, d.center_num, d.photo " +
    	                     "FROM SUSER u " +
    	                     "JOIN SUSER_DETAIL d ON u.user_num = d.user_num " +
    	                     "WHERE u.user_num = ?";
    	        pstmt = conn.prepareStatement(sql);
    	        pstmt.setLong(1, userNum);

    	        rs = pstmt.executeQuery();
    	        if (rs.next()) {
    	            member = new MemberVO();
    	            member.setUser_num(rs.getLong("user_num"));
    	            member.setLogin_id(rs.getString("login_id"));
    	            member.setStatus(rs.getInt("status"));
    	            member.setNick_name(rs.getString("nick_name"));
    	            member.setName(rs.getString("name"));
    	            member.setEmail(rs.getString("email"));
    	            member.setPhone(rs.getString("phone"));
    	            member.setBirth_date(rs.getString("birth_date"));
    	            member.setRegistration_date(rs.getDate("registration_date"));
    	            member.setCenter_num(rs.getInt("center_num"));
    	            member.setPhoto(rs.getString("photo")); // 프로필 사진 추가
    	        }
    	    } finally {
    	        DBUtil.executeClose(rs, pstmt, conn);
    	    }
    	    return member;
    	}
    	// 사용자 프로필 수정
    	public void updateUserProfile(MemberVO member) throws Exception {
    	    Connection conn = null;
    	    PreparedStatement pstmt = null;

    	    try {
    	        conn = DBUtil.getConnection();

    	        // 중복 체크
    	        if (isDuplicateNickname(member.getNick_name())) {
    	            throw new Exception("이미 사용 중인 닉네임입니다.");
    	        }
    	        if (isDuplicateEmail(member.getEmail())) {
    	            throw new Exception("이미 사용 중인 이메일입니다.");
    	        }
    	        if (isDuplicatePhone(member.getPhone())) {
    	            throw new Exception("이미 사용 중인 전화번호입니다.");
    	        }

    	        // 업데이트 SQL 실행
    	        String sql = "UPDATE SUSER_DETAIL SET nick_name = ?, name = ?, email = ?, phone = ?, birth_date = ? WHERE user_num = ?";
    	        pstmt = conn.prepareStatement(sql);
    	        pstmt.setString(1, member.getNick_name());
    	        pstmt.setString(2, member.getName());
    	        pstmt.setString(3, member.getEmail());
    	        pstmt.setString(4, member.getPhone());
    	        pstmt.setString(5, member.getBirth_date());
    	        pstmt.setLong(6, member.getUser_num());

    	        pstmt.executeUpdate();
    	    } finally {
    	        DBUtil.executeClose(null, pstmt, conn);
    	    }
    	}
    	// 사용자 프로필 동적 업데이트
    	public void updateUserProfileDynamic(MemberVO member) throws Exception {
    	    Connection conn = null;
    	    PreparedStatement pstmt = null;

    	    try {
    	        conn = DBUtil.getConnection();

    	        // 기존 사용자 정보 조회
    	        MemberVO existingMember = getUserProfile(member.getUser_num());

    	        // 기존 값을 유지하도록 처리
    	        String nick_name = member.getNick_name() != null ? member.getNick_name() : existingMember.getNick_name();
    	        String email = member.getEmail() != null ? member.getEmail() : existingMember.getEmail();
    	        String phone = member.getPhone() != null ? member.getPhone() : existingMember.getPhone();
    	        String birth_date = member.getBirth_date() != null ? member.getBirth_date() : existingMember.getBirth_date();

    	        // 동적 업데이트 SQL
    	        String sql = "UPDATE SUSER_DETAIL SET nick_name = ?, email = ?, phone = ?, birth_date = ? WHERE user_num = ?";
    	        pstmt = conn.prepareStatement(sql);
    	        pstmt.setString(1, nick_name);
    	        pstmt.setString(2, email);
    	        pstmt.setString(3, phone);
    	        pstmt.setString(4, birth_date);
    	        pstmt.setLong(5, member.getUser_num());

    	        pstmt.executeUpdate();
    	    } finally {
    	        DBUtil.executeClose(null, pstmt, conn);
    	    }
    	}
    	// 프로필 사진 수정
    	public void updateUserPhoto(long userNum, String photo) throws Exception {
    	    Connection conn = null;
    	    PreparedStatement pstmt = null;

    	    try {
    	        conn = DBUtil.getConnection();
    	        String sql = "UPDATE SUSER_DETAIL SET photo = ? WHERE user_num = ?";
    	        pstmt = conn.prepareStatement(sql);
    	        pstmt.setString(1, photo);
    	        pstmt.setLong(2, userNum);

    	        pstmt.executeUpdate();
    	    } finally {
    	        DBUtil.executeClose(null, pstmt, conn);
    	    }
    	}
    	// 프로필 사진 삭제
    	public void deleteUserPhoto(long userNum) throws Exception {
    	    updateUserPhoto(userNum, null); // 사진 경로를 NULL로 설정
    	}
    	// 회원 탈퇴 처리 (비활성화)
    	public void deactivateUser(long userNum) throws Exception {
    	    Connection conn = null;
    	    PreparedStatement pstmt = null;

    	    try {
    	        conn = DBUtil.getConnection();
    	        String sql = "UPDATE SUSER SET status = 0 WHERE user_num = ?";
    	        pstmt = conn.prepareStatement(sql);
    	        pstmt.setLong(1, userNum);

    	        pstmt.executeUpdate();
    	    } finally {
    	        DBUtil.executeClose(null, pstmt, conn);
    	    }
    	}
    	public MemberVO getMemberByPhone(String phoneNumber) throws Exception {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            MemberVO member = null;

            try {
                conn = DBUtil.getConnection();
                String sql = "SELECT * FROM suser WHERE phone = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, phoneNumber);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    member = new MemberVO();
                    member.setUser_num(rs.getLong("user_num"));
                    member.setName(rs.getString("name"));
                    member.setPhone(rs.getString("phone"));
                    member.setEmail(rs.getString("email"));
                }
            } catch (Exception e) {
                throw new Exception(e);
            } finally {
                DBUtil.executeClose(rs, pstmt, conn);
            }

            return member;
        }
    	// 특정 회원의 남은 회원권 일수 계산
    	public int getRemainingMembershipDays(long userNum) throws Exception {
    	    Connection conn = null;
    	    PreparedStatement pstmt = null;
    	    ResultSet rs = null;

    	    String sql = "SELECT ROUND((mt.duration_months * 30) - (SYSDATE - mo.order_date)) AS remaining_days " +
    	                 "FROM membershipOrder mo " +
    	                 "JOIN membership_types mt ON mo.typeId = mt.type_id " +
    	                 "WHERE mo.user_num = ? " +
    	                 "AND SYSDATE BETWEEN mo.order_date AND ADD_MONTHS(mo.order_date, mt.duration_months) " +
    	                 "ORDER BY mo.order_date DESC";

    	    try {
    	        conn = DBUtil.getConnection();
    	        pstmt = conn.prepareStatement(sql);
    	        pstmt.setLong(1, userNum);
    	        rs = pstmt.executeQuery();

    	        if (rs.next()) {
    	            return rs.getInt("remaining_days");
    	        } else {
    	            return 0; // 유효한 회원권이 없는 경우
    	        }
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        throw new Exception("남은 회원권 일수 계산 중 오류 발생", e);
    	    } finally {
    	        DBUtil.executeClose(rs, pstmt, conn);
    	    }
    	}
    }
