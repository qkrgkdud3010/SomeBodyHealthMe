package kr.goods.vo;

public class GoodsLikeVO {
	private long goods_num;
	private long user_num;
	
	public GoodsLikeVO() {}
	
	public GoodsLikeVO(long goods_num, long user_num) {
		this.goods_num = goods_num;
		this.user_num = user_num;
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
}
