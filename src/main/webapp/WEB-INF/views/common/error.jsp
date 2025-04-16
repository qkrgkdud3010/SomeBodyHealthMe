<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>오류 발생</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"> <!-- 스타일시트 연결 -->
</head>
<body>
    <div class="container">
        <h1>오류가 발생했습니다!</h1>
        <div class="error-message">
            <p><strong>오류 메시지:</strong> ${error_msg}</p> <!-- 오류 메시지 출력 -->
        </div>
        <div class="error-actions">
            <a href="${pageContext.request.contextPath}/mybody/myStatus.do" class="button">홈으로 돌아가기</a> <!-- 홈으로 돌아가기 버튼 -->
        </div>
    </div>

    <!-- 오류 페이지에 대한 간단한 스타일 -->
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8d7da;
            color: #721c24;
            text-align: center;
            padding: 50px;
        }

        .container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            border: 1px solid #f5c6cb;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
            width: 400px;
            margin: 0 auto;
        }

        h1 {
            font-size: 24px;
            margin-bottom: 20px;
        }

        .error-message {
            margin-bottom: 20px;
            font-size: 18px;
        }

        .error-actions .button {
            background-color: #721c24;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            font-size: 16px;
            display: inline-block;
        }

        .error-actions .button:hover {
            background-color: #f5c6cb;
            color: #721c24;
        }
    </style>
</body>
</html>
