package kr.goods.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.goods.vo.GoodsLikeVO;
import kr.goods.vo.GoodsReviewVO;
import kr.goods.vo.GoodsVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.FileUtil;
import kr.util.StringUtil;

public class GoodsDAO {
	//싱글턴 패턴
	private static GoodsDAO instance = new GoodsDAO();
	public static GoodsDAO getInstance() {
		return instance;
	}
	private GoodsDAO() {}

	//관리자 상품등록
	public void insertGoods(GoodsVO goods)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO goods (goods_num, goods_name, goods_price, goods_info, "
					+ "goods_category, goods_img1, goods_img2, goods_date, goods_quantity, goods_status) VALUES (goods_seq.nextval,?,?,?,?,?,?,SYSDATE,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, goods.getGoods_name());
			pstmt.setInt(2, goods.getGoods_price());
			pstmt.setString(3, goods.getGoods_info());
			pstmt.setString(4, goods.getGoods_category());
			pstmt.setString(5, goods.getGoods_img1());
			pstmt.setString(6, goods.getGoods_img2());
			pstmt.setInt(7, goods.getGoods_quantity());
			pstmt.setInt(8, goods.getGoods_status());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//전체글 개수/검색글 개수
	public int getGoodsCount(String keyfield, String keyword, int goods_status) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		int count = 0;

		try {
			conn = DBUtil.getConnection();

			if(keyword!=null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "And goods_name like '%' || ? || '%' ";
				else if (keyfield.equals("2")) sub_sql += "And goods_category like '%' || ? || '%' ";
			}
			//sql 문 작성
			sql = "select count(*) from goods where goods_status > ? " + sub_sql;
			//pre어쩌구 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(++cnt, goods_status);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count= rs.getInt(1);
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	//전체글 목록/검색글 목록
	public List<GoodsVO> getListGoods(int start, int end, String keyfield, String keyword, int goods_status) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<GoodsVO> list =null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		try {
			conn = DBUtil.getConnection();
			if(keyword!=null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "AND goods_name LIKE ? ";
				else if (keyfield.equals("2")) sub_sql += "AND goods_category LIKE ? ";
			}
			//status의 값이 0이면 , 1(미표시),2(표시) 모두호출 --->관리자용 
			//status의 값이 1이면, 2(표시) 호출 -> 사용자용
			sql = "select * from (select a.*, rownum rnum from (select * from goods where goods_status > ?" +sub_sql
					+" order by goods_num desc)a) where rnum >= ? and rnum <= ?"	;
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(++cnt, goods_status);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%" + keyword + "%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			rs = pstmt.executeQuery();
			list = new ArrayList<GoodsVO>();
			while (rs.next()) {
				GoodsVO goods = new GoodsVO();
				goods.setGoods_num(rs.getLong("goods_num"));
				goods.setGoods_name(StringUtil.useNoHtml(rs.getString("goods_name")));
				goods.setGoods_img1(rs.getString("goods_img1"));
				goods.setGoods_img2(rs.getString("goods_img2"));
				goods.setGoods_category(rs.getString("goods_category"));
				goods.setGoods_price(rs.getInt("goods_price"));
				goods.setGoods_quantity(rs.getInt("goods_quantity"));
				goods.setGoods_date(rs.getDate("goods_date"));
				goods.setGoods_status(rs.getInt("goods_status"));

				list.add(goods);
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return list;
	}
	
	//관리자 전체글 목록/검색글 목록
		public List<GoodsVO> getAdminListGoods(int start, int end, String keyfield, String keyword, int goods_status) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<GoodsVO> list =null;
			String sql = null;
			String sub_sql = "";
			int cnt = 0;
			try {
				conn = DBUtil.getConnection();
				if(keyword!=null && !"".equals(keyword)) {
					//검색처리
					if(keyfield.equals("1")) sub_sql += "AND goods_name LIKE ? ";
					else if (keyfield.equals("2")) sub_sql += "AND goods_category LIKE ? ";
				}
				//status의 값이 0이면 , 1(미표시),2(표시) 모두호출 --->관리자용 
				//status의 값이 1이면, 2(표시) 호출 -> 사용자용
				sql = "select * from (select a.*, rownum rnum from (select * from goods where goods_status > ?" +sub_sql
						+" order by goods_num desc)a) where rnum >= ? and rnum <= ?"	;
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(++cnt, goods_status);
				if(keyword != null && !"".equals(keyword)) {
					pstmt.setString(++cnt, "%" + keyword + "%");
				}
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);
				rs = pstmt.executeQuery();
				list = new ArrayList<GoodsVO>();
				while (rs.next()) {
					GoodsVO goods = new GoodsVO();
					goods.setGoods_num(rs.getLong("goods_num"));
					goods.setGoods_name(StringUtil.useNoHtml(rs.getString("goods_name")));
					goods.setGoods_img1(rs.getString("goods_img1"));
					goods.setGoods_img2(rs.getString("goods_img2"));
					goods.setGoods_category(rs.getString("goods_category"));
					goods.setGoods_price(rs.getInt("goods_price"));
					goods.setGoods_quantity(rs.getInt("goods_quantity"));
					goods.setGoods_date(rs.getDate("goods_date"));
					goods.setGoods_status(rs.getInt("goods_status"));

					list.add(goods);
				}
			}catch (Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}

			return list;
		}
	
	
	//관리자 / 사용자 - 상품 상세
	public GoodsVO getGoods(long goods_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GoodsVO goods = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql ="SELECT * FROM goods WHERE goods_num=?";

			pstmt= conn.prepareStatement(sql);
			pstmt.setLong(1, goods_num);
			rs= pstmt.executeQuery();
			if(rs.next()) {
				goods = new GoodsVO();
				goods.setGoods_num(rs.getLong("goods_num"));
				goods.setGoods_name(rs.getString("goods_name"));
				goods.setGoods_price(rs.getInt("goods_price"));
				goods.setGoods_info(rs.getString("goods_info"));
				goods.setGoods_category(rs.getString("goods_category"));
				goods.setGoods_img1(rs.getString("goods_img1"));
				goods.setGoods_img2(rs.getString("goods_img2"));
				goods.setGoods_status(rs.getInt("goods_status"));
				goods.setGoods_quantity(rs.getInt("goods_quantity"));
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return goods;
	}
	//관리자 - 이미지삭제
	public void deleteImg(long goods_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE goods SET goods_img='' WHERE goods_num";
			pstmt = conn.prepareStatement(sql);
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//관리자 - 상품 수정
	public void updateGoods(GoodsVO goods)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;

		try {
			conn =DBUtil.getConnection();

			if(goods.getGoods_img1()!=null && !"".equals(goods.getGoods_img1())) {
				sub_sql += ",goods_img1=?";
			}
			if(goods.getGoods_img2()!=null && !"".equals(goods.getGoods_img2())) {
				sub_sql += ",goods_img2=?";
			}
			sql = "update goods set goods_name=?,goods_price=?,goods_info=?,"
					+ "goods_category=?,goods_quantity=?,goods_mdate=SYSDATE,goods_status=?" + sub_sql
					+" where goods_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++cnt, goods.getGoods_name());
			pstmt.setInt(++cnt, goods.getGoods_price());
			pstmt.setString(++cnt, goods.getGoods_info());
			pstmt.setString(++cnt, goods.getGoods_category());
			pstmt.setInt(++cnt, goods.getGoods_quantity());
			pstmt.setInt(++cnt, goods.getGoods_status());
			if(goods.getGoods_img1()!=null && !"".equals(goods.getGoods_img1())) {
				pstmt.setString(++cnt, goods.getGoods_img1());
			}
			if(goods.getGoods_img2()!=null && !"".equals(goods.getGoods_img2())) {
				pstmt.setString(++cnt, goods.getGoods_img2());
			}
			pstmt.setLong(++cnt, goods.getGoods_num());
			pstmt.executeUpdate();
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//관리자 - 상품 삭제
	public void deleteGoods(long goods_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);

			//좋아요 삭제
			sql = "delete from goods_like where goods_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, goods_num);
			pstmt.executeUpdate();

			//댓글 삭제
			sql = "delete from goods_review where goods_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setLong(1, goods_num);
			pstmt2.executeUpdate();

			//부모글 삭제
			sql = "delete from goods where goods_num=?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setLong(1, goods_num);
			pstmt3.executeUpdate();
			conn.commit();
		}catch(Exception e) {
			//sql문이 하나라도 예외가 발생하면 롤백 처리
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//좋아요 개수
	public int selectLikeCount(long goods_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count =0;
		try {
			conn = DBUtil.getConnection();
			sql ="select count(*) from goods_like where goods_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, goods_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return count;
	}

	//내가 선택한 좋아요 목록
	public List<GoodsVO> getListGoodsLike(int start, int end,long user_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<GoodsVO> list =null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			//주의!! zboard_fav의 회원번호(좋아요 클릭한 회원번호)로 검색되어야 하기 때문에 f.mem_num으로 표기함
			sql= "select * from  (select a.*, rownum rnum from (SELECT * "
					+ "FROM goods_like l "
					+ "JOIN suser u ON l.user_num = u.user_num "
					+ "JOIN goods g ON l.goods_num = g.goods_num "
					+ "WHERE l.user_num = ? "
					+ "ORDER BY g.goods_num DESC "
					+ ")a) where rnum >= ? and rnum <= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, user_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			rs= pstmt.executeQuery();
			list = new ArrayList<GoodsVO>();
			while(rs.next()) {
				GoodsVO goods = new GoodsVO();
				goods.setGoods_num(rs.getLong("goods_num"));
				goods.setGoods_name(StringUtil.useNoHtml(rs.getString("goods_name")));
				goods.setGoods_price(rs.getInt("goods_price"));
				goods.setGoods_img1(rs.getString("goods_img1"));
				list.add(goods);
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	
	
	
	//회원번호와 게시물 번호를 이용한 좋아요 정보
	//(회원이 게시물을 호출했을 때 좋아요 선택 여부 표시) 내가 선택한 좋아요 목록!
	public GoodsLikeVO selectLike(GoodsLikeVO likeVO) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GoodsLikeVO like =null;
		String sql = null;
		try {	
			conn = DBUtil.getConnection();
			sql ="select * from goods_like where goods_num=? and user_num=?";
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, likeVO.getGoods_num());
			pstmt.setLong(2, likeVO.getUser_num());
			//sql문실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				like = new GoodsLikeVO();
				like.setGoods_num(rs.getLong("goods_num"));
				like.setUser_num(rs.getLong("user_num"));
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return like;
	}

	//좋아요 등록
	public void insertLike(GoodsLikeVO likeVO) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql= "insert into goods_like (goods_num,user_num) values(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, likeVO.getGoods_num());
			pstmt.setLong(2, likeVO.getUser_num());
			pstmt.executeUpdate();
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//좋아요 삭제
	public void deleteLike(GoodsLikeVO likeVO) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql= "delete from goods_like where goods_num=? and user_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, likeVO.getGoods_num());
			pstmt.setLong(2, likeVO.getUser_num());
			pstmt.executeUpdate();
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//댓글 등록 (리뷰 등록)
	public void insertGoodsReview(GoodsReviewVO goodsReview) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql ="insert into goods_review (re_num,re_content,re_rating,re_ip,user_num,goods_num) values (review_seq.nextval,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, goodsReview.getRe_content());
			pstmt.setInt(2, goodsReview.getRe_rating());
			pstmt.setString(3, goodsReview.getRe_ip());
			pstmt.setLong(4, goodsReview.getUser_num());
			pstmt.setLong(5, goodsReview.getGoods_num());
			pstmt.executeUpdate();
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//댓글 개수 (리뷰 개수)
	public int getGoodsReviewCount(long goods_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count =0;
		try {
			conn = DBUtil.getConnection();
			sql ="select count(*) from goods_review where goods_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, goods_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return count;
	}

	//댓글 목록
	public List<GoodsReviewVO> getListGoodsReview(int start, int end, long goods_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<GoodsReviewVO> list = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "select * from (select a.*, rownum rnum from (SELECT * FROM goods_review JOIN suser_detail "
					+ "USING(user_num) WHERE goods_num = ? ORDER BY re_num desc)a) "
					+ "WHERE rnum >=? AND rnum <=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, goods_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			list = new ArrayList<GoodsReviewVO>();
			while(rs.next()) {
				GoodsReviewVO review = new GoodsReviewVO();
				review.setRe_num(rs.getLong("re_num"));
				review.setRe_date(DurationFromNow.getTimeDiffLabel(rs.getString("re_date")));

				if(rs.getString("re_mdate")!=null) {
					review.setRe_mdate(DurationFromNow.getTimeDiffLabel(rs.getString("re_mdate")));
				}

				review.setRe_content(StringUtil.useBrNoHtml(rs.getString("re_content")));
				review.setGoods_num(rs.getLong("goods_num"));
				review.setUser_num(rs.getLong("user_num"));
				review.setNick_name(rs.getString("nick_name"));
				review.setRe_rating(rs.getInt("re_rating"));

				list.add(review);
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

	//댓글 상세 (리뷰 수정, 삭제시 작성자 회원번호 체크용도로 사용)
	public GoodsReviewVO getGoodsReview(long re_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GoodsReviewVO review = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql ="select * from goods_review where re_num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, re_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				review = new GoodsReviewVO();
				review.setRe_num(rs.getLong("re_num"));
				review.setUser_num(rs.getLong("user_num"));
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return review;
	}
	//댓글 수정 (리뷰 수정)
	public void updateGoodsReview(GoodsReviewVO review)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql =null;

		try {
			conn = DBUtil.getConnection();
			//sql문 작성
			sql = "update goods_review set re_content=?, re_mdate=SYSDATE, re_rating=? WHERE re_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,review.getRe_content());
			pstmt.setInt(2, review.getRe_rating());
			pstmt.setLong(3, review.getRe_num());
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//댓글 삭제 (리뷰 삭제)
	public void deleteGoodsReview(long re_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql ="delete from goods_review where re_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, re_num);
			pstmt.executeUpdate();
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//리뷰 작성 체크
	public boolean checkReview(long user_num, long goods_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean isReviewed = false;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(*) FROM goods_review WHERE user_num = ? AND goods_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, user_num);
			pstmt.setLong(2, goods_num);
			rs = pstmt.executeQuery();
			if(rs.next() && rs.getInt(1) > 0) {
				isReviewed = true;
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return isReviewed;
	}
	
	//리뷰 평균 점수
	public double getAverageRating(long goods_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT AVG(re_rating) AS avg_rating FROM goods_review WHERE goods_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, goods_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getDouble("avg_rating");
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return 0;
	}
	
	
	
}






















