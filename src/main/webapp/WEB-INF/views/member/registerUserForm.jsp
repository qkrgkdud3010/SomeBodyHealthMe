<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 가입</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registerForm.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
    <script src="https://kit.fontawesome.com/8e490eaab5.js" crossorigin="anonymous"></script>
    <script type="text/javascript">
        $(function(){
            let idChecked = 0, nicknameChecked = 0, emailChecked = 0, phoneChecked = 0;

            // 아이디 중복 체크
            $('#loginId').blur(function() {
                const loginId = $(this).val().trim();
                if (loginId === '') {
                    $('#message_loginId').css('color', 'red').text('아이디를 입력해주세요.');
                    return;
                }
                $.ajax({
                    url: '/someBodyHealthMe/member/checkDuplicateId.do',
                    type: 'post',
                    data: { value: loginId },
                    dataType: 'json',
                    success: function(param) {
                        if (param.result === 'idNotFound') {
                            idChecked = 1;
                            $('#message_loginId').css('color', '#000000').text('사용 가능한 아이디입니다.');
                        } else {
                            idChecked = 0;
                            $('#message_loginId').css('color', 'red').text('중복된 아이디입니다.');
                        }
                    },
                    error: function() {
                        idChecked = 0;
                        alert('네트워크 오류 발생');
                    }
                });
            });

            // 닉네임 중복 체크
            $('#nickname').blur(function() {
                const nickname = $(this).val().trim();
                if (nickname === '') {
                    $('#message_nickname').css('color', 'red').text('닉네임을 입력해주세요.');
                    return;
                }
                $.ajax({
                    url: '/someBodyHealthMe/member/checkDuplicateNickname.do',
                    type: 'post',
                    data: { value: nickname },
                    dataType: 'json',
                    success: function(param) {
                        if (param.result === 'idNotFound') {
                            nicknameChecked = 1;
                            $('#message_nickname').css('color', '#000000').text('사용 가능한 닉네임입니다.');
                        } else {
                            nicknameChecked = 0;
                            $('#message_nickname').css('color', 'red').text('중복된 닉네임입니다.');
                        }
                    },
                    error: function() {
                        nicknameChecked = 0;
                        alert('네트워크 오류 발생');
                    }
                });
            });

            // 이메일 중복 체크
            $('#email').blur(function() {
                const email = $(this).val().trim();
                if (email === '') {
                    $('#message_email').css('color', 'red').text('이메일을 입력해주세요.');
                    return;
                }
                $.ajax({
                    url: '/someBodyHealthMe/member/checkDuplicateEmail.do',
                    type: 'post',
                    data: { value: email },
                    dataType: 'json',
                    success: function(param) {
                        if (param.result === 'idNotFound') {
                            emailChecked = 1;
                            $('#message_email').css('color', '#000000').text('사용 가능한 이메일입니다.');
                        } else {
                            emailChecked = 0;
                            $('#message_email').css('color', 'red').text('중복된 이메일입니다.');
                        }
                    },
                    error: function() {
                        emailChecked = 0;
                        alert('네트워크 오류 발생');
                    }
                });
            });

            // 휴대전화번호 중복 체크
            $('#phone').blur(function() {
                const phone = $(this).val().trim();
                if (phone === '') {
                    $('#message_phone').css('color', 'red').text('휴대전화번호를 입력해주세요.');
                    return;
                }
                $.ajax({
                    url: '/someBodyHealthMe/member/checkDuplicatePhone.do',
                    type: 'post',
                    data: { value: phone },
                    dataType: 'json',
                    success: function(param) {
                        if (param.result === 'idNotFound') {
                            phoneChecked = 1;
                            $('#message_phone').css('color', '#000000').text('사용 가능한 전화번호입니다.');
                        } else {
                            phoneChecked = 0;
                            $('#message_phone').css('color', 'red').text('중복된 전화번호입니다.');
                        }
                    },
                    error: function() {
                        phoneChecked = 0;
                        alert('네트워크 오류 발생');
                    }
                });
            });

            // 회원가입 폼 제출 전 유효성 체크
            $('#register_form').submit(function(event) {
                event.preventDefault();
                if (idChecked === 0) {
                    alert('아이디 중복 체크 필수');
                    return false;
                }
                if (nicknameChecked === 0) {
                    alert('닉네임 중복 체크 필수');
                    return false;
                }
                if (emailChecked === 0) {
                    alert('이메일 중복 체크 필수');
                    return false;
                }
                if (phoneChecked === 0) {
                    alert('휴대전화번호 중복 체크 필수');
                    return false;
                }
                $.ajax({
                    url: 'registerUser.do',
                    type: 'post',
                    data: $('#register_form').serialize(),
                    success: function() {
                        alert('계정 생성 완료');
                        window.location.href = '${pageContext.request.contextPath}/main/main.do';
                    },
                    error: function() {
                        alert('회원가입 중 오류 발생');
                    }
                });
            });
        });
    </script>
</head>
<body>
<div class="page-main">
    <!-- 로고와 타이틀 -->
    <div class="login-title">
        <div class="navbar__logo">
            <i class="fa-solid fa-fire"></i>
            <a href="${pageContext.request.contextPath}/main/main.do" style="text-decoration: none; color: inherit;">Some Body Health Me</a>
        </div>
    </div>
    <div class="content-main">
        <form id="register_form" method="post">
            <ul>
                <li>
                    <label for="loginId">아이디</label>
                    <input type="text" name="loginId" id="loginId" maxlength="12" autocomplete="off">
                    <span id="message_loginId"></span>
                </li>
                <li>
                    <label for="nickname">닉네임</label>
                    <input type="text" name="nickname" id="nickname" maxlength="12">
                    <span id="message_nickname"></span>
                </li>
                <li>
                    <label for="password">비밀번호</label>
                    <input type="password" name="password" id="password" maxlength="12">
                </li>
                <li>
                    <label for="email">이메일 주소</label>
                    <input type="email" name="email" id="email" maxlength="50">
                    <span id="message_email"></span>
                </li>
                <li>
                    <label for="name">이름</label>
                    <input type="text" name="name" id="name" maxlength="10">
                </li>
                <li>
                    <label for="birthdate">생년월일</label>
                    <input type="text" name="birthdate" id="birthdate" maxlength="8" placeholder="YYYYMMDD">
                </li>
                <li>
                    <label for="phone">휴대전화번호</label>
                    <input type="text" name="phone" id="phone" maxlength="15">
                    <span id="message_phone"></span>
                </li>
                <li>
                    <label for="adminCode">관리자 코드 (선택)</label>
                    <input type="text" name="adminCode" id="adminCode" maxlength="6">
                </li>
               <li>
    				<label>센터 코드</label>
    				<div class="center-code">
       					<div>
            				<input type="radio" name="centerCode" id="centerCode1" value="1">
            				<label for="centerCode1">강남점</label>
        			</div>
        			<div>
            				<input type="radio" name="centerCode" id="centerCode2" value="2">
            				<label for="centerCode2">강북점</label>
        		</div>
   		 		</div>
			</li>
            </ul>
            <div class="align-center buttons-row">
                <input type="submit" value="회원가입">
                <input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
            </div>
        </form>
    </div>
</div>
</body>
</html>

