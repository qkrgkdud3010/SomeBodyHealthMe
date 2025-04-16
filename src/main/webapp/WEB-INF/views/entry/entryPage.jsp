<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>헬스장 출입 체크</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/entryPage.css">
    <style>
        /* 임시 스타일 */
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }
        .entry-container {
            max-width: 400px;
            margin: 100px auto;
            padding: 20px;
            background: #fff;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            text-align: center;
        }
        h1 {
            margin-bottom: 20px;
            color: #333;
        }
        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
        }
        input[type="text"] {
            width: calc(100% - 20px);
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        button {
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="entry-container">
        <h1>헬스장 출입 체크</h1>
        <form action="${pageContext.request.contextPath}/entry/entryCheck.do" method="post">
   				<label for="phone">전화번호</label>
    			<input type="text" id="phone" name="phone" placeholder="전화번호를 입력하세요" required>
  				<button type="submit">입장 확인</button>
</form>
    </div>
</body>
</html>
