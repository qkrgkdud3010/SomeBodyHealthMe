<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원권 구매</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
	 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/MS.css" type="text/css">
<style>
        #membershipForm {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px 30px;
            width: 300px;
            text-align: left;
            margin-right:45px;

        }

        #membershipForm h2 {
            font-size: 20px;
            margin-bottom: 20px;
            color: #333;
        }

        #membershipForm label {
            font-weight: bold;
            margin-bottom: 10px;
            display: block;
        }

        #membershipForm select {
            width: 100%;
            padding: 10px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-bottom: 20px;
            appearance: none; /* 기본 화살표 제거 */
            background-image: url('data:image/svg+xml;charset=US-ASCII,%3Csvg xmlns%3D%22http%3A//www.w3.org/2000/svg%22 viewBox%3D%220 0 4 5%22%3E%3Cpath fill%3D%22%23000000%22 d%3D%22M2 0L0 2h4z%22/%3E%3C/svg%3E');
            background-repeat: no-repeat;
            background-position: right 10px top 50%;
            background-size: 10px;
        }

        #membershipForm input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            font-size: 16px;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
            transition: background-color 0.3s ease;
        }

        #membershipForm input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
	</div>
	
	<jsp:include page="/WEB-INF/views/common/AsideMembership.jsp" />
	<div class="clear:both;"></div>
	<div style="border:1px solid rgba(0, 0, 0, .25); height:800px;" >
    <h1 style="text-align:left; margin-right:150px;">회원권 구매</h1>
	<div style="text-align:left; float:left; width:800px;" >
	<img alt="" src="../images/membership.png" width="800">
	</div>
    <form action="membershipOrderForm.do" id="membershipForm" style="text-align:right; float:right;">
        회원권 구매 
        <select id="typeId" name="typeId">
            <option value="1">1개월 5만원</option>
            <option value="2">3개월 15만원</option>
            <option value="3">6개월 30만원</option>
            <option value="4">12개월 60만원</option>
        </select><br>
        <input type="submit" value="구매">
    </form>
</div>
    <div id="result"></div>


    
    	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
