package kr.mydiet.vo;

public class DietPlanVO {
    private long dietId;        // DIETID (Primary Key)
    private String foodName;    // FOODNAME (음식 이름)
    private Double calories;    // CALORIES (칼로리)
    private Double protein;     // PROTEIN (단백질)
    private Double carbohydrate;// CARBOHYDRATE (탄수화물)
    private Double fat;         // FAT (지방)
    private Double minerals;    // MINERALS (미네랄)
    private int dietShow;       // DIET_SHOW (식단 노출 정도: 0 - 비공개, 1 - 공개)
    private int dietComment;    // DIET_COMMENT (식단 노출 요청: 0 - 비요청, 1 - 요청, 2 - 반려됨)
    private long userNum;       // USER_NUM (사용자 번호)

    // Getters and Setters
    public long getDietId() {
        return dietId;
    }

    public void setDietId(long dietId) {
        this.dietId = dietId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(Double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getMinerals() {
        return minerals;
    }

    public void setMinerals(Double minerals) {
        this.minerals = minerals;
    }

    public int getDietShow() {
        return dietShow;
    }

    public void setDietShow(int dietShow) {
        this.dietShow = dietShow;
    }

    public int getDietComment() {
        return dietComment;
    }

    public void setDietComment(int dietComment) {
        this.dietComment = dietComment;
    }

    public long getUserNum() {
        return userNum;
    }

    public void setUserNum(long userNum) {
        this.userNum = userNum;
    }
}
