<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>헬스장 출입 결과</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .result-container {
            max-width: 400px;
            margin: 100px auto;
            padding: 20px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        h1 {
            margin-bottom: 20px;
            color: #333;
        }
        .message {
            font-size: 18px;
            margin-bottom: 20px;
        }
        .message.success {
            color: #28a745; /* 초록색 (성공) */
        }
        .message.error {
            color: #dc3545; /* 빨간색 (실패) */
        }
        button {
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="result-container">
        <h1>출입 결과</h1>
        <!-- 성공/실패 메시지 출력 -->
        <p class="message ${messageType}">${message}</p>
        <button onclick="history.back()">뒤로</button>
    </div>
</body>
</html>
