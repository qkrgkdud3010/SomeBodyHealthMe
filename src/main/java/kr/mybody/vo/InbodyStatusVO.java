package kr.mybody.vo;

import java.sql.Date;

public class InbodyStatusVO {
    private Long userNum; // 사용자 번호 (외래키)
    private Date measurementDate; // 측정 날짜 (java.util.Date 타입)
    private double muscleMass; // 근육량
    private double bodyFatPercentage; // 체지방률
    private Date createdAt; // 생성일 (자동으로 SYSDATE로 할당)
    private String month; // 월 (예: "2023-01") - 월별 데이터 조회를 위한 필드
    private double avgMuscleMass; // 월별 평균 근육량
    private double avgBodyFatPercentage; // 월별 평균 체지방률

    // Getter and Setter methods

    public Long getUserNum() {
        return userNum;
    }

    public void setUserNum(Long userNum) {
        this.userNum = userNum;
    }

    public Date getMeasurementDate() {
        return measurementDate;
    }

    public void setMeasurementDate(Date measurementDate) {
        this.measurementDate = measurementDate;
    }

    public double getMuscleMass() {
        return muscleMass;
    }

    public void setMuscleMass(double muscleMass) {
        this.muscleMass = muscleMass;
    }

    public double getBodyFatPercentage() {
        return bodyFatPercentage;
    }

    public void setBodyFatPercentage(double bodyFatPercentage) {
        this.bodyFatPercentage = bodyFatPercentage;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getAvgMuscleMass() {
        return avgMuscleMass;
    }

    public void setAvgMuscleMass(double avgMuscleMass) {
        this.avgMuscleMass = avgMuscleMass;
    }

    public double getAvgBodyFatPercentage() {
        return avgBodyFatPercentage;
    }

    public void setAvgBodyFatPercentage(double avgBodyFatPercentage) {
        this.avgBodyFatPercentage = avgBodyFatPercentage;
    }

}
