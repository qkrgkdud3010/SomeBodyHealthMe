package kr.cart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.cart.vo.CartVO;
import kr.goods.vo.GoodsVO;
import kr.util.DBUtil;

public class CartDAO {
	//싱글턴 패턴
	private static CartDAO instance = new CartDAO();
	
	public static CartDAO getInstance() {
		return instance;
	}
	private CartDAO() {}
	
	//장바구니를 등록합니다
		public void insertCart(CartVO cart)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "INSERT INTO cart (cart_num,goods_num,"
					+ "order_quantity,user_num) VALUES ("
					+ "cart_seq.nextval,?,?,?)";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setLong(1, cart.getGoods_num());
				pstmt.setInt(2, cart.getOrder_quantity());
				pstmt.setLong(3, cart.getUser_num());
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
	//회원번호(user_num) 별 총구매 금액
	public int getTotalByUser_num(long user_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int total = 0;
		try {
			conn = DBUtil.getConnection();
			sql = "select sum(sub_total) from (select user_num, order_quantity * goods_price as sub_total from cart join goods using(goods_num)) "
					+ "where user_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, user_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				total = rs.getInt(1);
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
			
		}
		return total;
	}
	
	
	
	//장바구니 목록
	public List<CartVO> getListCart(long user_num) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CartVO> list = null;
		String sql = null;
		try{
			conn = DBUtil.getConnection();
			sql = "select * from cart c join goods g using(goods_num) where user_num=? order by c.cart_num desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, user_num);
			rs = pstmt.executeQuery();
			list = new ArrayList<CartVO>();
			while(rs.next()) {
				CartVO cart = new CartVO();
				cart.setCart_num(rs.getLong("cart_num"));
				cart.setGoods_num(rs.getLong("goods_num"));
				cart.setOrder_quantity(rs.getInt("order_quantity"));
				cart.setUser_num(rs.getLong("user_num"));
				
				//상품 정볼르 담기위해  itemVO 객체 생성
				GoodsVO goods = new GoodsVO();
				goods.setGoods_name(rs.getString("goods_name"));
				goods.setGoods_price(rs.getInt("goods_price"));
				goods.setGoods_img1(rs.getString("goods_img1"));
				goods.setGoods_img2(rs.getString("goods_img2"));
				goods.setGoods_quantity(rs.getInt("goods_quantity"));
				goods.setGoods_status(rs.getInt("goods_status"));
				
				//ItemVO 를 CartVO 에 저장
				cart.setGoodsVO(goods);
				
				//동일상품(item_num이 같은 상품) 의 총구매 금액 구하기
				cart.setSub_total(cart.getOrder_quantity()*goods.getGoods_price());
				
				//cartVO를 Arraylist에 저장
				list.add(cart);
			}
			
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	//장바구니 상세
	public CartVO getCart(CartVO cart) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CartVO cartSaved = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql ="select * from cart where goods_num=? and user_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, cart.getGoods_num());
			pstmt.setLong(2, cart.getUser_num());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cartSaved = new CartVO();
				cartSaved.setCart_num(rs.getLong("cart_num"));
				cartSaved.setGoods_num(rs.getLong("goods_num"));
				cartSaved.setOrder_quantity(rs.getInt("order_quantity"));
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return cartSaved;
	}
	
	//장바구니 수정(개별 상품 수량 수정)
	public void updateCart(CartVO cart) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "update cart set order_quantity=? where cart_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cart.getOrder_quantity());
			pstmt.setLong(2, cart.getCart_num());
			pstmt.executeUpdate();
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	
	//장바구니 수정(상품번호와 회원번호별 수정)
	//동일상품이 있을 경우 수량을 합산
	public void updateCartByGoods_num (CartVO cart) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql ="update cart set order_quantity=? where goods_num=? and user_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cart.getOrder_quantity());
			pstmt.setLong(2, cart.getGoods_num());
			pstmt.setLong(3, cart.getUser_num());
			pstmt.executeUpdate();
			
		}catch (Exception e) {
			throw new Exception(e);
		
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//장바구니 삭제
	public void deleteCart(long cart_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql ="delete from cart where cart_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, cart_num);
			pstmt.executeUpdate();
			
		}catch (Exception e) {
			throw new Exception(e);
		
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}
