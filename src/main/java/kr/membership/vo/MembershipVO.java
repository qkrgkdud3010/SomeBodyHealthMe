package kr.membership.vo;

import java.sql.Date;

public class MembershipVO {
    private long mem_num;
    private long user_num;
    private int payment_status;
    private int mem_type;
    private Date mem_startdate;
    private Date mem_enddate;
    private int mem_status;
    private int mem_price;
    private String user_name; // 사용자 이름 필드 추가
    private int duration_months; // 기간 필드 추가

    public long getMem_num() {
        return mem_num;
    }

    public void setMem_num(long mem_num) {
        this.mem_num = mem_num;
    }

    public long getUser_num() {
        return user_num;
    }

    public void setUser_num(long user_num) {
        this.user_num = user_num;
    }

    public int getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(int payment_status) {
        this.payment_status = payment_status;
    }

    public int getMem_type() {
        return mem_type;
    }

    public void setMem_type(int mem_type) {
        this.mem_type = mem_type;
    }

    public Date getMem_startdate() {
        return mem_startdate;
    }

    public void setMem_startdate(Date mem_startdate) {
        this.mem_startdate = mem_startdate;
    }

    public Date getMem_enddate() {
        return mem_enddate;
    }

    public void setMem_enddate(Date mem_enddate) {
        this.mem_enddate = mem_enddate;
    }

    public int getMem_status() {
        return mem_status;
    }

    public void setMem_status(int mem_status) {
        this.mem_status = mem_status;
    }

    public int getMem_price() {
        return mem_price;
    }

    public void setMem_price(int mem_price) {
        this.mem_price = mem_price;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    // duration_months 필드의 getter와 setter 추가
    public int getDuration_months() {
        return duration_months;
    }

    public void setDuration_months(int duration_months) {
        this.duration_months = duration_months;
    }
}
