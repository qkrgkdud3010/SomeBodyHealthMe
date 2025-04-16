package kr.membership.vo;

import java.sql.Date;

public class MembershipPtVO {
	private long pt_num;
	private long mem_num;
	private int payment_status;
	private Date pt_reservedate;
	private int pt_status;
	private int pt_price;
	
	public long getPt_num() {
		return pt_num;
	}
	public void setPt_num(long pt_num) {
		this.pt_num = pt_num;
	}
	public long getMem_num() {
		return mem_num;
	}
	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}
	public int getPayment_status() {
		return payment_status;
	}
	public void setPayment_status(int payment_status) {
		this.payment_status = payment_status;
	}
	public Date getPt_reservedate() {
		return pt_reservedate;
	}
	public void setPt_reservedate(Date pt_reservedate) {
		this.pt_reservedate = pt_reservedate;
	}
	public int getPt_status() {
		return pt_status;
	}
	public void setPt_status(int pt_status) {
		this.pt_status = pt_status;
	}
	public int getPt_price() {
		return pt_price;
	}
	public void setPt_price(int pt_price) {
		this.pt_price = pt_price;
	}
	
}
