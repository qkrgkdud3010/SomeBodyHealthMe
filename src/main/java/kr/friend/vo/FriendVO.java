package kr.friend.vo;

import java.sql.Date;

public class FriendVO {

    // 필드를 private로 선언
    private long friend_Num;      
    private Date created_At;
    private long user_Num;        
    private String status;
    private String status2;
    private String message_text; 
    private long receiver_num;
	public long getReceiver_num() {
		return receiver_num;
	}
	public void setReceiver_num(long receiver_num) {
		this.receiver_num = receiver_num;
	}
	public String getMessage_text() {
		return message_text;
	}
	public void setMessage_text(String message_text) {
		this.message_text = message_text;
	}

	
	public long getFriend_Num() {
		return friend_Num;
	}
	public void setFriend_Num(long friend_Num) {
		this.friend_Num = friend_Num;
	}
	public Date getCreated_At() {
		return created_At;
	}
	public void setCreated_At(Date created_At) {
		this.created_At = created_At;
	}
	public long getUser_Num() {
		return user_Num;
	}
	public void setUser_Num(long user_Num) {
		this.user_Num = user_Num;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}   
	public String getStatus2() {
		return status2;
	}
	public void setStatus2(String status2) {
		this.status2 = status2;
	}   


	    private String nick_name;      // 사용자의 별명
	    private String name;          // 사용자의 이름
	    private String email;         // 사용자의 이메일 주소
	    private String password;      // 사용자 비밀번호
	    private String phone;         // 사용자의 전화번호
	    private Date registration_Date; // 가입일자
	    private String birth_Date;     // 사용자의 생년월일
	    private Date modify_Date;      // 수정일
	    private int center_Num;
		public String getNick_name() {
			return nick_name;
		}
		public void setNick_Name(String nick_name) {
			this.nick_name = nick_name;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public Date getRegistration_Date() {
			return registration_Date;
		}
		public void setRegistration_Date(Date registration_Date) {
			this.registration_Date = registration_Date;
		}
		public String getBirth_Date() {
			return birth_Date;
		}
		public void setBirth_Date(String birth_Date) {
			this.birth_Date = birth_Date;
		}
		public Date getModify_Date() {
			return modify_Date;
		}
		public void setModify_Date(Date modify_Date) {
			this.modify_Date = modify_Date;
		}
		public int getCenter_Num() {
			return center_Num;
		}
		public void setCenter_Num(int center_Num) {
			this.center_Num = center_Num;
		}
		
	
}
