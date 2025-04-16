package kr.appl.vo;

import java.sql.Date;

public class ApplVO {
	private long appl_num;            // 지원번호
	private int field;              // 분야(0: 탈퇴, 1: 일반 사용자, 2: 트레이너, 3:사무직 관리자, 4: 마스터 관리자, 5: 정지)
	private String appl_attachment;  // appl_attachment
	private int appl_status;             // 확인 상태(0:미학인 1:확인)
	private Date appl_regdate;          // 등록일
	private Date appl_modifydate;        // 수정일
	private int career;             // 경력(0:미경력 1:경력)
	private String content;         // 자기소개
	private String source;          // 지원경로
	private int appl_center;          // 센터번호
	private long user_num;            // 회원번호
	private String name;             //회원명
	private String login_id;		//회원 아이디
	private String phone;           //전화번호
	private String birth_date;      //생년월일
	private int status;             //상태
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public long getAppl_num() {
		return appl_num;
	}
	public void setAppl_num(long appl_num) {
		this.appl_num = appl_num;
	}
	public int getField() {
		return field;
	}
	public void setField(int field) {
		this.field = field;
	}
	public String getAppl_attachment() {
		return appl_attachment;
	}
	public void setAppl_attachment(String appl_attachment) {
		this.appl_attachment = appl_attachment;
	}
	public int getAppl_status() {
		return appl_status;
	}
	public void setAppl_status(int appl_status) {
		this.appl_status = appl_status;
	}
	public Date getAppl_regdate() {
		return appl_regdate;
	}
	public void setAppl_regdate(Date appl_regdate) {
		this.appl_regdate = appl_regdate;
	}
	public Date getAppl_modifydate() {
		return appl_modifydate;
	}
	public void setAppl_modifydate(Date appl_modifydate) {
		this.appl_modifydate = appl_modifydate;
	}
	public int getCareer() {
		return career;
	}
	public void setCareer(int career) {
		this.career = career;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getAppl_center() {
		return appl_center;
	}
	public void setAppl_center(int appl_center) {
		this.appl_center = appl_center;
	}
	public long getUser_num() {
		return user_num;
	}
	public void setUser_num(long user_num) {
		this.user_num = user_num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
