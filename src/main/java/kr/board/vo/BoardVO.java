package kr.board.vo;

public class BoardVO {
	private long board_num;//글번호
	private String board_title;//제목
	private String board_content;//내용
	private String board_attachment;//첨부파일
	private String board_regdate;//등록일
	private String board_modifydate;//수정일
	private long board_count;//조회수
	private int board_category;//게시판 카테고리
	private long user_num;//회원번호
	private String nick_name;//닉네임
	private String login_id; //아이디
	private String photo;
	private int recount;
	
	public int getRecount() {
		return recount;
	}
	public void setRecount(int recount) {
		this.recount = recount;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public long getBoard_num() {
		return board_num;
	}
	public void setBoard_num(long board_num) {
		this.board_num = board_num;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_content() {
		return board_content;
	}
	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}
	public String getBoard_attachment() {
		return board_attachment;
	}
	public void setBoard_attachment(String board_attachment) {
		this.board_attachment = board_attachment;
	}
	public String getBoard_regdate() {
		return board_regdate;
	}
	public void setBoard_regdate(String board_regdate) {
		this.board_regdate = board_regdate;
	}
	public String getBoard_modifydate() {
		return board_modifydate;
	}
	public void setBoard_modifydate(String board_modifydate) {
		this.board_modifydate = board_modifydate;
	}
	public long getBoard_count() {
		return board_count;
	}
	public void setBoard_count(long board_count) {
		this.board_count = board_count;
	}
	public int getBoard_category() {
		return board_category;
	}
	public void setBoard_category(int board_category) {
		this.board_category = board_category;
	}
	public long getUser_num() {
		return user_num;
	}
	public void setUser_num(long user_num) {
		this.user_num = user_num;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	
	
	
}
