package kr.friend.action;

import java.sql.Date;

public class ChatOneVO {

	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private Long message_num;       // 메시지 고유 번호
    private Long sender_num;        // 발신자 번호
    private Long receiver_num;      // 수신자 번호
    private String message_text;    // 메시지 내용
    private String is_read;         // 읽음 여부 ('Y' 또는 'N')
    private String message_date;      // 메시지 전송 날짜
	public Long getMessage_num() {
		return message_num;
	}
	public void setMessage_num(Long message_num) {
		this.message_num = message_num;
	}
	public Long getSender_num() {
		return sender_num;
	}
	public void setSender_num(Long sender_num) {
		this.sender_num = sender_num;
	}
	public Long getReceiver_num() {
		return receiver_num;
	}
	public void setReceiver_num(Long receiver_num) {
		this.receiver_num = receiver_num;
	}
	public String getMessage_text() {
		return message_text;
	}
	public void setMessage_text(String message_text) {
		this.message_text = message_text;
	}
	public String getIs_read() {
		return is_read;
	}
	public void setIs_read(String is_read) {
		this.is_read = is_read;
	}
	public String getMessage_date() {
		return message_date;
	}
	public void setMessage_date(String message_date) {
		this.message_date = message_date;
	}

}
