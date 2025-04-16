package kr.mydiet.vo;

import java.sql.Date;

public class MealLogVO {
    private long mealLogId;    // 식단 기록 고유 코드
    private String foodName;   // 음식 이름
    private String mealType;   // 식사 유형 (아침, 점심, 저녁 등)
    private Date createdAt;    // 식단 기록 시간
    private long user_num;      // 사용자 고유 ID

    // Getter와 Setter 메서드
    public long getMealLogId() {
        return mealLogId;
    }

    public void setMealLogId(long mealLogId) {
        this.mealLogId = mealLogId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getUserNum() {
        return user_num;
    }

    public void setUserNum(long user_num) {
        this.user_num = user_num;
    }
}
