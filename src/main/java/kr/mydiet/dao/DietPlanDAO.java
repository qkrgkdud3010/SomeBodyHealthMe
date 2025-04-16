package kr.mydiet.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import kr.mydiet.vo.DietPlanVO;
import kr.util.DBUtil;

public class DietPlanDAO {

	  private static DietPlanDAO instance = new DietPlanDAO();

	    public static DietPlanDAO getInstance() {
	        return instance;
	    }

    // 식단 데이터 삽입 메서드
    public void insertDietPlan(DietPlanVO dietPlan) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO DIETPLAN (DIETID, FOODNAME, CALORIES, PROTEIN, CARBOHYDRATE, FAT, MINERALS, DIET_SHOW, DIET_COMMENT, USER_NUM) "
                   + "VALUES (DIETPLAN_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, dietPlan.getFoodName());  // FOODNAME
            pstmt.setDouble(2, dietPlan.getCalories());  // CALORIES
            pstmt.setDouble(3, dietPlan.getProtein());   // PROTEIN
            pstmt.setDouble(4, dietPlan.getCarbohydrate()); // CARBOHYDRATE
            pstmt.setDouble(5, dietPlan.getFat());       // FAT
            pstmt.setDouble(6, dietPlan.getMinerals());  // MINERALS
            pstmt.setInt(7, dietPlan.getDietShow());     // DIET_SHOW
            pstmt.setInt(8, dietPlan.getDietComment());  // DIET_COMMENT
            pstmt.setLong(9, dietPlan.getUserNum());     // USER_NUM

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error while inserting DietPlan", e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }

    // 식단 데이터 조회 (특정 사용자 USER_NUM 기준)
    public List<DietPlanVO> selectDietPlansByUserNum(long userNum) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT DIETID, FOODNAME, CALORIES, PROTEIN, CARBOHYDRATE, FAT, MINERALS, DIET_SHOW, DIET_COMMENT, USER_NUM "
                   + "FROM DIETPLAN WHERE USER_NUM = ?";
        List<DietPlanVO> dietPlans = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, userNum);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                DietPlanVO dietPlan = new DietPlanVO();
                dietPlan.setDietId(rs.getLong("DIETID"));
                dietPlan.setFoodName(rs.getString("FOODNAME"));
                dietPlan.setCalories(rs.getDouble("CALORIES"));
                dietPlan.setProtein(rs.getDouble("PROTEIN"));
                dietPlan.setCarbohydrate(rs.getDouble("CARBOHYDRATE"));
                dietPlan.setFat(rs.getDouble("FAT"));
                dietPlan.setMinerals(rs.getDouble("MINERALS"));
                dietPlan.setDietShow(rs.getInt("DIET_SHOW"));
                dietPlan.setDietComment(rs.getInt("DIET_COMMENT"));
                dietPlan.setUserNum(rs.getLong("USER_NUM"));

                dietPlans.add(dietPlan);
            }
        } catch (SQLException e) {
            throw new SQLException("Error while selecting DietPlans", e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }

        return dietPlans;
    }

    // 식단 데이터 삭제
    public void deleteDietPlan(long dietId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM DIETPLAN WHERE DIETID = ?";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, dietId);  // DIETID (PK)
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error while deleting DietPlan", e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }

    // 식단 데이터 조회 (특정 DIETID 기준)
    public DietPlanVO selectDietPlanByDietId(long dietId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT DIETID, FOODNAME, CALORIES, PROTEIN, CARBOHYDRATE, FAT, MINERALS, DIET_SHOW, DIET_COMMENT, USER_NUM "
                   + "FROM DIETPLAN WHERE DIETID = ?";
        DietPlanVO dietPlan = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, dietId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dietPlan = new DietPlanVO();
                dietPlan.setDietId(rs.getLong("DIETID"));
                dietPlan.setFoodName(rs.getString("FOODNAME"));
                dietPlan.setCalories(rs.getDouble("CALORIES"));
                dietPlan.setProtein(rs.getDouble("PROTEIN"));
                dietPlan.setCarbohydrate(rs.getDouble("CARBOHYDRATE"));
                dietPlan.setFat(rs.getDouble("FAT"));
                dietPlan.setMinerals(rs.getDouble("MINERALS"));
                dietPlan.setDietShow(rs.getInt("DIET_SHOW"));
                dietPlan.setDietComment(rs.getInt("DIET_COMMENT"));
                dietPlan.setUserNum(rs.getLong("USER_NUM"));
            }
        } catch (SQLException e) {
            throw new SQLException("Error while selecting DietPlan by DietId", e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }

        return dietPlan;
    }
    
 // 로그인한 사용자의 DIET_SHOW = 0인 사용자 지정 식단 목록을 가져오는 메서드
    public List<DietPlanVO> selectDietPlansWithDietShowZero(long userNum) throws Exception { 
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT DIETID, FOODNAME, CALORIES, PROTEIN, CARBOHYDRATE, FAT, MINERALS, DIET_SHOW, DIET_COMMENT, USER_NUM "
                   + "FROM DIETPLAN WHERE USER_NUM = ? AND DIET_SHOW = 0";

        List<DietPlanVO> dietList = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, userNum);

            System.out.println("Executing SQL: " + sql);
            System.out.println("With USER_NUM: " + userNum);

            rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No data found for USER_NUM: " + userNum);
            }

            while (rs.next()) {
                DietPlanVO dietPlan = new DietPlanVO();
                dietPlan.setDietId(rs.getLong("DIETID"));
                dietPlan.setFoodName(rs.getString("FOODNAME"));
                dietPlan.setCalories(rs.getDouble("CALORIES"));
                dietPlan.setProtein(rs.getDouble("PROTEIN"));
                dietPlan.setCarbohydrate(rs.getDouble("CARBOHYDRATE"));
                dietPlan.setFat(rs.getDouble("FAT"));
                dietPlan.setMinerals(rs.getDouble("MINERALS"));
                dietPlan.setDietShow(rs.getInt("DIET_SHOW"));
                dietPlan.setDietComment(rs.getInt("DIET_COMMENT"));
                dietPlan.setUserNum(rs.getLong("USER_NUM"));
                dietList.add(dietPlan);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }

        System.out.println("Diet list size: " + dietList.size());

        return dietList;
    }


    // 선택한 식단의 상세 정보를 가져오는 메서드
    public DietPlanVO getDietPlanById(long dietId, long userNum) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DietPlanVO dietPlan = null;

        String sql = "SELECT DIETID, FOODNAME, CALORIES, PROTEIN, CARBOHYDRATE, FAT, MINERALS, DIET_SHOW, DIET_COMMENT, USER_NUM "
                   + "FROM DIETPLAN WHERE DIETID = ? AND USER_NUM = ? AND DIET_SHOW = 0";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, dietId);
            pstmt.setLong(2, userNum);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dietPlan = new DietPlanVO();
                dietPlan.setDietId(rs.getLong("DIETID"));
                dietPlan.setFoodName(rs.getString("FOODNAME"));
                dietPlan.setCalories(rs.getDouble("CALORIES"));
                dietPlan.setProtein(rs.getDouble("PROTEIN"));
                dietPlan.setCarbohydrate(rs.getDouble("CARBOHYDRATE"));
                dietPlan.setFat(rs.getDouble("FAT"));
                dietPlan.setMinerals(rs.getDouble("MINERALS"));
                dietPlan.setDietShow(rs.getInt("DIET_SHOW"));
                dietPlan.setDietComment(rs.getInt("DIET_COMMENT"));
                dietPlan.setUserNum(rs.getLong("USER_NUM"));
            }
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }

        return dietPlan;
    }
    
    // 식단 ID로 DietPlanVO 가져오기
    public DietPlanVO getDietPlanById(long dietId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        DietPlanVO dietPlan = null;

        try {
            conn = DBUtil.getConnection();

            sql = "SELECT * FROM DietPlan WHERE dietId = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, dietId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                dietPlan = new DietPlanVO();
                dietPlan.setDietId(rs.getLong("dietId"));
                dietPlan.setFoodName(rs.getString("foodName"));
                dietPlan.setCalories(rs.getDouble("calories"));
                dietPlan.setProtein(rs.getDouble("protein"));
                dietPlan.setCarbohydrate(rs.getDouble("carbohydrate"));
                dietPlan.setFat(rs.getDouble("fat"));
                dietPlan.setMinerals(rs.getDouble("minerals"));
                dietPlan.setDietShow(rs.getInt("diet_show"));
                dietPlan.setDietComment(rs.getInt("diet_comment"));
                dietPlan.setUserNum(rs.getLong("user_num"));
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }

        return dietPlan;
    }

    // 식단 정보 업데이트 메서드
    public void updateDietPlan(DietPlanVO dietPlan) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE DIETPLAN SET FOODNAME = ?, CALORIES = ?, PROTEIN = ?, CARBOHYDRATE = ?, FAT = ?, MINERALS = ? "
                   + "WHERE DIETID = ? AND USER_NUM = ?";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dietPlan.getFoodName());
            pstmt.setDouble(2, dietPlan.getCalories());
            pstmt.setDouble(3, dietPlan.getProtein());
            pstmt.setDouble(4, dietPlan.getCarbohydrate());
            pstmt.setDouble(5, dietPlan.getFat());
            pstmt.setDouble(6, dietPlan.getMinerals());
            pstmt.setLong(7, dietPlan.getDietId());
            pstmt.setLong(8, dietPlan.getUserNum());

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new Exception("해당 식단 정보를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("식단 정보 업데이트 중 오류 발생: " + e.getMessage(), e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }

    
    public List<DietPlanVO> searchDietByKeyword(String keyword, int startRow, int endRow) throws Exception {
        List<DietPlanVO> foodList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // Oracle의 페이징을 위한 SQL
        String sql = "SELECT DIETID, FOODNAME FROM ( "
                   + "    SELECT DIETID, FOODNAME, ROW_NUMBER() OVER (ORDER BY DIETID DESC) AS row_num "
                   + "    FROM DIETPLAN "
                   + "    WHERE FOODNAME LIKE ? "
                   + ") "
                   + "WHERE row_num BETWEEN ? AND ?";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            // 검색어에 %를 추가하여 부분 일치 검색을 수행
            pstmt.setString(1, "%" + keyword + "%");  // "%고구마%"와 같은 형태로 쿼리
            
            // 페이징 처리: 시작 행과 끝 행을 전달
            pstmt.setInt(2, startRow);  // 시작 인덱스 (예: 1, 11, 21...)
            pstmt.setInt(3, endRow);    // 끝 인덱스 (예: 10, 20, 30...)

            rs = pstmt.executeQuery();

            while (rs.next()) {
                DietPlanVO dietPlan = new DietPlanVO();
                dietPlan.setDietId(rs.getLong("DIETID"));
                dietPlan.setFoodName(rs.getString("FOODNAME"));
                foodList.add(dietPlan);
            }
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }

        return foodList;
    }



    public int getDietCountByKeyword(String keyword) throws Exception {
        int count = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT COUNT(*) FROM DIETPLAN WHERE FOODNAME LIKE ?";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }

        return count;
    }

    public void deleteDietPlan(long dietId, long userNum) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM DIETPLAN WHERE DIETID = ? AND USER_NUM = ?";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, dietId);
            pstmt.setLong(2, userNum);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted == 0) {
                throw new Exception("해당 식단 정보를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("식단 삭제 중 오류 발생: " + e.getMessage(), e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    public void updateDietComment(long dietId, int dietComment) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE DIETPLAN SET DIET_COMMENT = ? WHERE DIETID = ?";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, dietComment);
            pstmt.setLong(2, dietId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new Exception("해당 식단 정보를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("DIET_COMMENT 업데이트 중 오류 발생: " + e.getMessage(), e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }

 // 모든 DietPlan 데이터를 가져오기
    public List<DietPlanVO> getAllDietPlans() {
        List<DietPlanVO> dietPlans = new ArrayList<>();
        String sql = "SELECT DIETID, FOODNAME, CALORIES, PROTEIN, CARBOHYDRATE, FAT, MINERALS, DIET_SHOW, DIET_COMMENT, USER_NUM "
                   + "FROM DIETPLAN";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                DietPlanVO dietPlan = new DietPlanVO();
                dietPlan.setDietId(rs.getLong("DIETID"));
                dietPlan.setFoodName(rs.getString("FOODNAME"));
                dietPlan.setCalories(rs.getDouble("CALORIES"));
                dietPlan.setProtein(rs.getDouble("PROTEIN"));
                dietPlan.setCarbohydrate(rs.getDouble("CARBOHYDRATE"));
                dietPlan.setFat(rs.getDouble("FAT"));
                dietPlan.setMinerals(rs.getDouble("MINERALS"));
                dietPlan.setDietShow(rs.getInt("DIET_SHOW"));
                dietPlan.setDietComment(rs.getInt("DIET_COMMENT"));
                dietPlan.setUserNum(rs.getLong("USER_NUM"));
                dietPlans.add(dietPlan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dietPlans;
    }

    // DietPlan 데이터 업데이트
    public void updateDietPlan(long dietId, int dietShow, int dietComment) {
        String sql = "UPDATE DIETPLAN SET DIET_SHOW = ?, DIET_COMMENT = ? WHERE DIETID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dietShow);
            pstmt.setInt(2, dietComment);
            pstmt.setLong(3, dietId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
