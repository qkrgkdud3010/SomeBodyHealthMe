package kr.goods.vo;

import java.sql.Date;

public class GoodsVO {
	private long goods_num;
	private String goods_name;
	private int goods_price;
	private String goods_info;
	private String goods_category;
	private String goods_img1;
	private String goods_img2;
	private Date goods_date;
	private Date goods_mdate;
	private int goods_quantity;
	private int goods_status;
	public long getGoods_num() {
		return goods_num;
	}
	public void setGoods_num(long goods_num) {
		this.goods_num = goods_num;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public int getGoods_price() {
		return goods_price;
	}
	public void setGoods_price(int goods_price) {
		this.goods_price = goods_price;
	}
	public String getGoods_info() {
		return goods_info;
	}
	public void setGoods_info(String goods_info) {
		this.goods_info = goods_info;
	}
	public String getGoods_category() {
		return goods_category;
	}
	public void setGoods_category(String goods_category) {
		this.goods_category = goods_category;
	}
	public String getGoods_img1() {
		return goods_img1;
	}
	public void setGoods_img1(String goods_img1) {
		this.goods_img1 = goods_img1;
	}
	public String getGoods_img2() {
		return goods_img2;
	}
	public void setGoods_img2(String goods_img2) {
		this.goods_img2 = goods_img2;
	}
	public Date getGoods_date() {
		return goods_date;
	}
	public void setGoods_date(Date goods_date) {
		this.goods_date = goods_date;
	}
	public Date getGoods_mdate() {
		return goods_mdate;
	}
	public void setGoods_mdate(Date goods_mdate) {
		this.goods_mdate = goods_mdate;
	}
	public int getGoods_quantity() {
		return goods_quantity;
	}
	public void setGoods_quantity(int goods_quantity) {
		this.goods_quantity = goods_quantity;
	}
	public int getGoods_status() {
		return goods_status;
	}
	public void setGoods_status(int goods_status) {
		this.goods_status = goods_status;
	}
	
		
}
