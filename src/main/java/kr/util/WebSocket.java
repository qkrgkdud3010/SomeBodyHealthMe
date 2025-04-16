package kr.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
// WebSocket의 호스트 주소 설정
@ServerEndpoint("/webSocket")
public class WebSocket {

	static List<Session> sessionList = Collections.synchronizedList(new ArrayList<>());
	//WebSocket으로 브라우저가 접속하면 요청되는 함수	
	@OnOpen
	public void handleOpen(Session userSession) {
		//콘솔에 접속 로그를 출력
		System.out.println("client is now connected...");
		sessionList.add(userSession); // 웹 소켓 연결시 세션리스트에 추가
	}
	// WebSocket으로 메시지가 오면 요청되는 함수
	@OnMessage
	public void handleMessage(String message) throws IOException {
		// 메시지 내용을 콘솔에 출력
		System.out.println("receive from client : " + message);
		//세션리스트에게 데이터를 보낸다.
	    Iterator<Session> iterator = sessionList.iterator();
	    while(iterator.hasNext()){
	    	//해당 데이터를 다른 세션들에게 뿌린다.
	  	  iterator.next().getBasicRemote().sendText(message);
	    }
	}
	// WebSocket과 브라우저가 접속이 끊기면 요청되는 함수
	@OnClose
	public void handleClose(Session userSession) {
		// 콘솔에 접속 끊김 로그를 출력
		System.out.println("client is now disconnected...");
		sessionList.remove(userSession);
	}
	// WebSocket과 브라우저 간에 통신 에러가 발생하면 요청되는 함수.
	@OnError
	public void handleError(Throwable t) {
		// 콘솔에 에러를 표시
		t.printStackTrace();
	}
}