<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${result_title}</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/MS.css" type="text/css">
</head>
<style>
    .content-main {
        max-width: 600px;
        margin: 50px auto;
        padding: 20px;
        background-color: #f9f9f9;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        text-align: center;
    }

    .content-main h3 {
        font-size: 24px;
        color: #333;
        margin-bottom: 20px;
    }

    .result-display {
        background-color: #ffffff;
        padding: 20px;
        border-radius: 8px;
        border: 1px solid #ddd;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .result-display p {
        font-size: 18px;
        color: #666;
        margin-top: 10px;
    }

    .align-center {
        margin-top: 20px;
    }

    .align-center input[type="button"] {
        font-size: 16px;
        color: #fff;
        background-color: #4CAF50;
        border: none;
        padding: 10px 20px;
        border-radius: 5px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    .align-center input[type="button"]:hover {
        background-color: #45a049;
    }
</style>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>

</div>
<div class="content-main">
    <h3>${result_title}</h3>
    <div class="result-display">
        <p>${result_msg}</p>
        <div class="align-center">
            <input type="button" value="확인" onclick="location.href='${result_url}'">
        </div>
    </div>
</div>

    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>





