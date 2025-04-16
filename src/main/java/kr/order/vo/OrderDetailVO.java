package kr.order.vo;

public class OrderDetailVO {
	private long detail_num;
	private long goods_num;
	private String goods_name;
	private int goods_price;
	private int goods_total;	//동일 상품의 총주문 금액
	private int order_quantity;
	private long order_num;
	
	public long getDetail_num() {
		return detail_num;
	}
	public void setDetail_num(long detail_num) {
		this.detail_num = detail_num;
	}
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
	public int getGoods_total() {
		return goods_total;
	}
	public void setGoods_total(int goods_total) {
		this.goods_total = goods_total;
	}
	public int getOrder_quantity() {
		return order_quantity;
	}
	public void setOrder_quantity(int order_quantity) {
		this.order_quantity = order_quantity;
	}
	public long getOrder_num() {
		return order_num;
	}
	public void setOrder_num(long order_num) {
		this.order_num = order_num;
	}
	
}