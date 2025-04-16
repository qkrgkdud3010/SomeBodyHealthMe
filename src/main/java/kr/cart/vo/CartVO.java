package kr.cart.vo;

import kr.goods.vo.GoodsVO;

public class CartVO {
	private long cart_num;
	private long goods_num;
	private int order_quantity;
	private long user_num;
	private int sub_total;
	
	private GoodsVO goodsVO;
	
	public long getCart_num() {
		return cart_num;
	}
	public void setCart_num(long cart_num) {
		this.cart_num = cart_num;
	}
	public long getGoods_num() {
		return goods_num;
	}
	public void setGoods_num(long goods_num) {
		this.goods_num = goods_num;
	}
	public long getUser_num() {
		return user_num;
	}
	public void setUser_num(long user_num) {
		this.user_num = user_num;
	}
	public int getOrder_quantity() {
		return order_quantity;
	}
	public void setOrder_quantity(int order_quantity) {
		this.order_quantity = order_quantity;
	}
	public int getSub_total() {
		return sub_total;
	}
	public void setSub_total(int sub_total) {
		this.sub_total = sub_total;
	}
	public GoodsVO getGoodsVO() {
		return goodsVO;
	}
	public void setGoodsVO(GoodsVO goodsVO) {
		this.goodsVO = goodsVO;
	}
}
