package kr.mybody.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.member.vo.MemberVO;
import kr.mybody.vo.InbodyStatusVO;
import kr.mybody.vo.MyBodyStatusVO;
import kr.util.DBUtil;

public class MyBodyDAO{
	
	
	private static MyBodyDAO instance = new MyBodyDAO();
	
	
	public static MyBodyDAO getInstance() {
		return instance;
	}
	
	
	public MyBodyDAO() {}
	
	
	public MyBodyStatusVO getMyBodyStatus(long user_num)throws Exception{
		
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MyBodyStatusVO mybodystatus = null;
		String sql = null;
		
		try {
			//커넥션 풀로 부터 커넥션을 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "SELECT * FROM healthInfo WHERE user_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setLong(1, user_num);
			
			//sql문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				mybodystatus = new MyBodyStatusVO();
				mybodystatus.setHealthInfoID(rs.getInt("healthinfoid"));
				mybodystatus.setHeight(rs.getInt("height"));
				mybodystatus.setWeight(rs.getInt("weight"));
				mybodystatus.setAge(rs.getInt("age"));
				mybodystatus.setBmi(rs.getInt("bmi"));
				mybodystatus.setGoal(rs.getString("goal"));
				mybodystatus.setGender(rs.getString("gender"));
				mybodystatus.setCreatedAt(rs.getDate("createdat"));
				mybodystatus.setModifyDate(rs.getDate("modifydate"));
			}
			
		} catch (SQLException e) {
		    // 로그를 남길 수도 있습니다.
		    throw new Exception("Database error: " + e.getMessage(), e);
		} catch (Exception e) {
		    throw new Exception("An unexpected error occurred: " + e.getMessage(), e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return mybodystatus;
		
	}
	
	//내 건강 정보 등록
	
	public void insertMyBodyStatus(MyBodyStatusVO myBodyStatus) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = null;

	    try {
	        // 커넥션풀로부터 커넥션을 할당
	        conn = DBUtil.getConnection();
	        
	        // 자동 커밋 비활성화
	        conn.setAutoCommit(false);

	        // BMI 계산 (height는 cm로 입력되었을 것으로 가정)
	        double heightInMeters = myBodyStatus.getHeight() / 100.0;  // height를 m 단위로 변환
	        double bmi = myBodyStatus.getWeight() / (heightInMeters * heightInMeters);  // BMI 계산
	        
	        sql = "INSERT INTO HealthInfo (healthinfoid, user_num, height, weight, age, bmi, goal, gender, createdat, modifydate) "
	                + "VALUES (healthinfo_seq.nextval, ?, ?, ?, ?, ?, ?, ?, SYSDATE, NULL)";
	        
	        pstmt = conn.prepareStatement(sql);
	        
	        pstmt.setLong(1, myBodyStatus.getUserNum()); // user_num
	        pstmt.setInt(2, myBodyStatus.getHeight());  // height
	        pstmt.setInt(3, myBodyStatus.getWeight());  // weight
	        pstmt.setInt(4, myBodyStatus.getAge());     // age
	        pstmt.setDouble(5, bmi);                    // bmi (계산된 값)
	        pstmt.setString(6, myBodyStatus.getGoal()); // goal
	        pstmt.setString(7, myBodyStatus.getGender()); // gender
	        
	        pstmt.executeUpdate();

	        // SQL문 실행 시 모두 성공하면 commit
	        conn.commit();            
	        
	    } catch (Exception e) {
	        // 예외가 발생하면 롤백
	        if (conn != null) {
	            conn.rollback();
	        }
	        throw new Exception(e);
	    } finally {
	        // 커넥션이 null이 아닐 경우 자동 커밋을 다시 원래 상태로 복구
	        if (conn != null) {
	            try {
	                conn.setAutoCommit(true); // 자동 커밋을 다시 true로 설정
	            } catch (SQLException se) {
	                se.printStackTrace();
	            }
	        }
	        // DB 자원 해제
	        DBUtil.executeClose(null, pstmt, conn);
	    }        
	}
	
	
	//내 건강 정보 수정
	
	public void updateMyBodyStatus(MyBodyStatusVO myBodyStatus) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = null;

	    try {
	        // 커넥션풀로부터 커넥션을 할당
	        conn = DBUtil.getConnection();
	        
	        // 자동 커밋 비활성화
	        conn.setAutoCommit(false);

	        // BMI 계산 (height는 cm로 입력되었을 것으로 가정)
	        double heightInMeters = myBodyStatus.getHeight() / 100.0;  // height를 m 단위로 변환
	        double bmi = myBodyStatus.getWeight() / (heightInMeters * heightInMeters);  // BMI 계산
	        
	        // UPDATE 쿼리 작성
	        sql = "UPDATE HealthInfo "
	            + "SET height = ?, weight = ?, age = ?, bmi = ?, goal = ?, gender = ?, modifydate = SYSDATE "
	            + "WHERE user_num = ?"; // user_num을 기준으로 업데이트
	        
	        pstmt = conn.prepareStatement(sql);
	        
	        pstmt.setInt(1, myBodyStatus.getHeight());  // height
	        pstmt.setInt(2, myBodyStatus.getWeight());  // weight
	        pstmt.setInt(3, myBodyStatus.getAge());     // age
	        pstmt.setDouble(4, bmi);                    // bmi (계산된 값)
	        pstmt.setString(5, myBodyStatus.getGoal()); // goal
	        pstmt.setString(6, myBodyStatus.getGender()); // gender
	        pstmt.setLong(7, myBodyStatus.getUserNum());  // user_num (WHERE 조건)

	        // SQL 실행
	        pstmt.executeUpdate();

	        // SQL문 실행 시 모두 성공하면 commit
	        conn.commit();
	        
	    } catch (Exception e) {
	        // 예외가 발생하면 롤백
	        if (conn != null) {
	            conn.rollback();
	        }
	        throw new Exception(e);
	    } finally {
	        // 커넥션이 null이 아닐 경우 자동 커밋을 다시 원래 상태로 복구
	        if (conn != null) {
	            try {
	                conn.setAutoCommit(true); // 자동 커밋을 다시 true로 설정
	            } catch (SQLException se) {
	                se.printStackTrace();
	            }
	        }
	        // DB 자원 해제
	        DBUtil.executeClose(null, pstmt, conn);
	    }
	}
	
	
	
	public List<InbodyStatusVO> getMonthlyInBodyData(Long user_num) throws Exception {
        List<InbodyStatusVO> inbodyStatusList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;

        try {
            // 커넥션 할당
            conn = DBUtil.getConnection();

            // SQL 쿼리: 월별 평균 근육량과 체지방률 조회
            sql = "SELECT TO_CHAR(MeasurementDate, 'YYYY-MM') AS month, "
                   + "AVG(MuscleMass) AS avg_muscle_mass, "
                   + "AVG(BodyFatPercentage) AS avg_body_fat_percentage "
                   + "FROM InBody "
                   + "WHERE user_num = ? "  // 로그인된 유저에 대해서만 조회
                   + "GROUP BY TO_CHAR(MeasurementDate, 'YYYY-MM') "
                   + "ORDER BY month";

            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, user_num);  // user_num을 바인딩
            rs = pstmt.executeQuery();

            // 결과 처리
            while (rs.next()) {
                InbodyStatusVO inBodyStatus = new InbodyStatusVO();
                inBodyStatus.setMonth(rs.getString("month"));
                inBodyStatus.setAvgMuscleMass(rs.getDouble("avg_muscle_mass"));
                inBodyStatus.setAvgBodyFatPercentage(rs.getDouble("avg_body_fat_percentage"));
                
                // 리스트에 추가
                inbodyStatusList.add(inBodyStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            // 자원 해제
            DBUtil.executeClose(rs, pstmt, conn);
        }

        return inbodyStatusList;
    }
	
	public List<InbodyStatusVO> getMonthlyInBodyData() throws Exception {
		
	    List<InbodyStatusVO> inbodyStatusList = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = null;

	    try {
	        // 커넥션 할당
	        conn = DBUtil.getConnection();

	        // SQL 쿼리: 월별 평균 근육량과 체지방률 조회
	        sql = "SELECT TO_CHAR(MeasurementDate, 'YYYY-MM') AS month, "
	                   + "AVG(MuscleMass) AS avg_muscle_mass, "
	                   + "AVG(BodyFatPercentage) AS avg_body_fat_percentage "
	                   + "FROM InBody "
	                   + "GROUP BY TO_CHAR(MeasurementDate, 'YYYY-MM') "
	                   + "ORDER BY month";

	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();

	        // 결과 처리
	        while (rs.next()) {
	            InbodyStatusVO inBodyStatus = new InbodyStatusVO();
	            inBodyStatus.setMonth(rs.getString("month"));
	            inBodyStatus.setAvgMuscleMass(rs.getDouble("avg_muscle_mass"));
	            inBodyStatus.setAvgBodyFatPercentage(rs.getDouble("avg_body_fat_percentage"));
	            
	            // 리스트에 추가
	            inbodyStatusList.add(inBodyStatus);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception(e);
	    } finally {
	        // 자원 해제
	        DBUtil.executeClose(rs, pstmt, conn);
	    }

	    return inbodyStatusList;
	}
	
	//내 건강 정보 등록
	
	public void insertInbodyStatus(InbodyStatusVO inbodyStatus) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = null;

	    try {
	        // 커넥션풀로부터 커넥션을 할당
	        conn = DBUtil.getConnection();
	        
	        // 자동 커밋 비활성화
	        conn.setAutoCommit(false);
	        
	        // SQL 쿼리: InBody 테이블에 인바디 데이터 삽입
	        sql = "INSERT INTO InBody (INBODYID, USER_NUM, MEASUREMENTDATE, MUSCLEMASS, BODYFATPERCENTAGE, CREATEDAT, MODIFY_DATE) "
	                + "VALUES (INBODYID_SEQ.nextval, ?, ?, ?, ?, SYSDATE, NULL)";

	        pstmt = conn.prepareStatement(sql);
	        
	        // 사용자 번호 (user_num)
	        pstmt.setLong(1, inbodyStatus.getUserNum());
	        
	        // 측정일 (MeasurementDate) -> `Date` 타입이므로 `java.sql.Date`로 변환
	        // 날짜만 필요하므로 시간을 제외한 날짜만 설정
	        Date sqlDate = new Date(inbodyStatus.getMeasurementDate().getTime());
	        pstmt.setDate(2, sqlDate); 
	        
	        // 근육량 (MuscleMass)
	        pstmt.setDouble(3, inbodyStatus.getMuscleMass());
	        
	        // 체지방률 (BodyFatPercentage)
	        pstmt.setDouble(4, inbodyStatus.getBodyFatPercentage());
	        
	        // SQL문 실행
	        pstmt.executeUpdate();

	        // SQL문 실행 시 모두 성공하면 commit
	        conn.commit();
	        
	    } catch (Exception e) {
	        // 예외가 발생하면 롤백
	        if (conn != null) {
	            conn.rollback();
	        }
	        throw new Exception(e);
	    } finally {
	        // 커넥션이 null이 아닐 경우 자동 커밋을 다시 원래 상태로 복구
	        if (conn != null) {
	            try {
	                conn.setAutoCommit(true); // 자동 커밋을 다시 true로 설정
	            } catch (SQLException se) {
	                se.printStackTrace();
	            }
	        }
	        // DB 자원 해제
	        DBUtil.executeClose(null, pstmt, conn);
	    }       
	}

	public List<InbodyStatusVO> getAllInbodyData(Long user_num) throws Exception {
	    List<InbodyStatusVO> inbodyStatusList = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = null;

	    try {
	        // 커넥션 할당
	        conn = DBUtil.getConnection();

	        // SQL 쿼리: user_num에 해당하는 모든 인바디 데이터 조회
	        sql = "SELECT MeasurementDate, MuscleMass, BodyFatPercentage "
	              + "FROM InBody "
	              + "WHERE user_num = ? "
	              + "ORDER BY MeasurementDate DESC";  // 날짜별로 최신순 정렬

	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, user_num);  // 로그인된 유저에 대해서만 조회
	        rs = pstmt.executeQuery();

	        // 결과 처리
	        while (rs.next()) {
	            InbodyStatusVO inbodyStatus = new InbodyStatusVO();
	            inbodyStatus.setMeasurementDate(rs.getDate("MeasurementDate"));
	            inbodyStatus.setMuscleMass(rs.getDouble("MuscleMass"));
	            inbodyStatus.setBodyFatPercentage(rs.getDouble("BodyFatPercentage"));
	            
	            // 리스트에 추가
	            inbodyStatusList.add(inbodyStatus);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception(e);
	    } finally {
	        // 자원 해제
	        DBUtil.executeClose(rs, pstmt, conn);
	    }

	    return inbodyStatusList;
	}


	
	public InbodyStatusVO getInbodyDataByDate(Long user_num, Date measurementDate) throws Exception {
	    InbodyStatusVO inbodyStatus = null;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = DBUtil.getConnection();
	        String sql = "SELECT MeasurementDate, MuscleMass, BodyFatPercentage "
	                   + "FROM InBody WHERE user_num = ? AND MeasurementDate = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, user_num);
	        pstmt.setDate(2, measurementDate);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            inbodyStatus = new InbodyStatusVO();
	            inbodyStatus.setMeasurementDate(rs.getDate("MeasurementDate"));
	            inbodyStatus.setMuscleMass(rs.getDouble("MuscleMass"));
	            inbodyStatus.setBodyFatPercentage(rs.getDouble("BodyFatPercentage"));
	        }
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }

	    return inbodyStatus;
	}

	public void updateInbodyStatus(InbodyStatusVO inbodyStatusVO) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = null;

	    try {
	        // 커넥션풀로부터 커넥션을 할당
	        conn = DBUtil.getConnection();

	        // SQL문 작성 (InbodyStatus 테이블 업데이트)
	        sql = "UPDATE inbody SET measurementdate=?, musclemass=?, bodyfatpercentage=?," 
	            + " modify_date=SYSDATE WHERE measurementdate=?";  // measurement_date는 PK로 가정

	        // PreparedStatement 객체 생성
	        pstmt = conn.prepareStatement(sql);

	        // ?에 데이터를 바인딩 (inbodyStatusVO 객체에서 데이터를 가져옴)
	        pstmt.setDate(1, inbodyStatusVO.getMeasurementDate());  // measurement_date
	        pstmt.setDouble(2, inbodyStatusVO.getMuscleMass());     // muscle_mass
	        pstmt.setDouble(3, inbodyStatusVO.getBodyFatPercentage()); // body_fat_percentage
	        pstmt.setDate(4, inbodyStatusVO.getMeasurementDate());   // 업데이트하려는 측정 날짜 기준으로

	        // SQL문 실행
	        pstmt.executeUpdate();
	    } catch (Exception e) {
	        throw new Exception("Inbody status update failed", e);
	    } finally {
	        DBUtil.executeClose(null, pstmt, conn);
	    }
	}


}
		
	