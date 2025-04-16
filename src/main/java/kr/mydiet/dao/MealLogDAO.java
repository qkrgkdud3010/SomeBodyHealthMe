package kr.mydiet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import kr.mydiet.vo.DietPlanVO;
import kr.mydiet.vo.MealLogVO;
import kr.util.DBUtil;

public class MealLogDAO {
    private static MealLogDAO instance = new MealLogDAO();

    public static MealLogDAO getInstance() {
        return instance;
    }

    public List<MealLogVO> getMealLogsByUserAndMealType(long user_num, String mealType) {
        List<MealLogVO> mealLogs = new ArrayList<>();
        String sql = "SELECT MEALLOGID, FOODNAME, MEALTYPE, CREATEDAT, USER_NUM FROM MEALLOG WHERE USER_NUM = ? AND MEALTYPE = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, user_num);
            pstmt.setString(2, mealType);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    MealLogVO log = new MealLogVO();
                    log.setMealLogId(rs.getLong("MEALLOGID"));
                    log.setFoodName(rs.getString("FOODNAME"));
                    log.setMealType(rs.getString("MEALTYPE"));
                    log.setCreatedAt(rs.getDate("CREATEDAT"));
                    log.setUserNum(rs.getLong("USER_NUM"));
                    mealLogs.add(log);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mealLogs;
    }

    public DietPlanVO getDietPlanByFoodName(String foodName) {
        String sql = "SELECT DIETID, FOODNAME, CALORIES, PROTEIN, CARBOHYDRATE, FAT, MINERALS, USER_NUM "
                   + "FROM DIETPLAN WHERE FOODNAME = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, foodName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    DietPlanVO dietPlan = new DietPlanVO();
                    dietPlan.setDietId(rs.getLong("DIETID"));
                    dietPlan.setFoodName(rs.getString("FOODNAME"));
                    dietPlan.setCalories(rs.getDouble("CALORIES"));
                    dietPlan.setProtein(rs.getDouble("PROTEIN"));
                    dietPlan.setCarbohydrate(rs.getDouble("CARBOHYDRATE"));
                    dietPlan.setFat(rs.getDouble("FAT"));
                    dietPlan.setMinerals(rs.getDouble("MINERALS"));
                    dietPlan.setUserNum(rs.getLong("USER_NUM"));
                    return dietPlan;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertMealLog(MealLogVO mealLog) throws Exception {
        String sql = "INSERT INTO MEALLOG (MEALLOGID, FOODNAME, MEALTYPE, CREATEDAT, USER_NUM) " +
                     "VALUES (MEALLOGID_SEQ.NEXTVAL, ?, ?, SYSDATE, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, mealLog.getFoodName());
            pstmt.setString(2, mealLog.getMealType());
            pstmt.setLong(3, mealLog.getUserNum());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
