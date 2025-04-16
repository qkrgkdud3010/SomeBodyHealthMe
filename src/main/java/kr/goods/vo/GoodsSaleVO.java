package kr.goods.vo;

import java.sql.Date;

public class GoodsSaleVO {
	private long goods_num;
	private int sale_discount;
	private Date sale_startdate;
	private Date sale_enddate;
	private int sale_status;
	public long getGoods_num() {
		return goods_num;
	}
	public void setGoods_num(long goods_num) {
		this.goods_num = goods_num;
	}
	public int getSale_discount() {
		return sale_discount;
	}
	public void setSale_discount(int sale_discount) {
		this.sale_discount = sale_discount;
	}
	public Date getSale_startdate() {
		return sale_startdate;
	}
	public void setSale_startdate(Date sale_startdate) {
		this.sale_startdate = sale_startdate;
	}
	public Date getSale_enddate() {
		return sale_enddate;
	}
	public void setSale_enddate(Date sale_enddate) {
		this.sale_enddate = sale_enddate;
	}
	public int getSale_status() {
		return sale_status;
	}
	public void setSale_status(int sale_status) {
		this.sale_status = sale_status;
	}
}
