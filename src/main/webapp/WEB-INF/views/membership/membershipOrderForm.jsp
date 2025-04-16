<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>상품 구매</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/MS.css" type="text/css">
<style>
    .content-main {
        max-width: 600px;
        margin: 50px auto;
        padding: 20px;
        background-color: #f9f9f9;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .content-main h2 {
        font-size: 24px;
        text-align: center;
        color: #333;
        margin-bottom: 20px;
    }

    form ul {
        list-style: none;
        padding: 0;
    }

    form li {
        margin-bottom: 15px;
    }

    label {
        font-size: 16px;
        font-weight: bold;
        color: #555;
        display: block;
        margin-bottom: 8px;
    }

    select, input[type="text"], input[type="radio"] {
        font-size: 14px;
        padding: 8px;
        width: calc(100% - 16px);
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    select:focus, input[type="text"]:focus {
        outline: none;
        border-color: #4CAF50;
        box-shadow: 0 0 5px rgba(76, 175, 80, 0.5);
    }

    #price {
        font-size: 16px;
        color: #333;
        margin-top: 10px;
        font-weight: bold;
    }

    .align-center {
        text-align: center;
        margin-top: 20px;
    }

    input[type="submit"], input[type="button"] {
        font-size: 16px;
        color: #fff;
        background-color: #4CAF50;
        border: none;
        padding: 10px 20px;
        border-radius: 5px;
        cursor: pointer;
        transition: background-color 0.3s ease;
        margin: 5px;
    }

    input[type="submit"]:hover, input[type="button"]:hover {
        background-color: #45a049;
    }
</style>
    <script type="text/javascript">
        // 선택된 옵션에 따라 가격을 동적으로 갱신하는 함수
        function updatePrice() {
            // 선택된 option의 값 가져오기
            var typeId = document.getElementById('typeId').value;
            
            // 가격을 보여줄 div 요소
            var priceElement = document.getElementById('price');
            
            // 가격 계산
            var price;
            switch(typeId) {
                case '1':
                    price = "50000";
                    break;
                case '2':
                    price = "150000";
                    break;
                case '3':
                    price = "300000";
                    break;
                case '4':
                    price = "6000000";
                    break;
                default:
                    price = "선택된 상품이 없습니다.";
            }
            
            // 가격 표시
            priceElement.innerText = "가격: " + price;
            document.getElementById('priceInput').value = price;
        }

        // 페이지 로딩 시 가격을 자동으로 갱신하도록 함
        window.onload = function() {
            updatePrice(); // 페이지 로딩 시 가격 표시
        };
    </script>

    <script type="text/javascript">
        $(function(){
            // 주문 정보 등록 유효성 체크
            $('#order_form').submit(function(){
                const items = document.querySelectorAll('.input-check');
                for(let i=0; i<items.length; i++){
                    if(items[i].value.trim() == ''){
                        const label = document.querySelector('label[for="'+items[i].id+'"]');
                        alert(label.textContent + ' 필수 입력');
                        items[i].value = '';
                        items[i].focus();
                        return false;
                    }
                }

                const radio = document.querySelectorAll('input[type=radio]:checked');
                if(radio.length < 1){
                    alert('결제수단을 선택하세요!');
                    return false;
                }            
            });
        });
    </script>
</head>
<body>
    <div class="page-main">
        <jsp:include page="/WEB-INF/views/common/header.jsp"/>
        <div class="content-main">
    
            <h2>상품 구매</h2>

            <form id="membershipForm" action="order.do" method="post">
                <ul>
                    <li>
                        <label for="receive_name" >등록할 회원권</label>
                        <select id="typeId" name="typeId" onchange="updatePrice()">
                            <option value="1" ${param.typeId == '1' ? 'selected' : ''}>1개월</option>
                            <option value="2" ${param.typeId == '2' ? 'selected' : ''}>3개월</option>
                            <option value="3" ${param.typeId == '3' ? 'selected' : ''}>6개월</option>
                            <option value="4" ${param.typeId == '4' ? 'selected' : ''}>12개월</option>
                        </select>
                    </li>
                    <li>
                        <div id="price">가격: </div> <!-- 가격을 표시할 곳 -->
                            <input type="hidden" id="priceInput" name="price">
                    </li>
                    <li>
                        <label for="receive_phone">전화번호</label>
                        <input type="text" name="receive_phone" id="receive_phone" maxlength="15" class="input-check">
                    </li>
                    <li>
                        <label>결제수단</label>
                        <input type="radio" name="payment" id="payment1" style="width:30px;" value="1">통장입금
                        <input type="radio" name="payment" id="payment2"  style="width:30px;" value="2">카드결제   
                    </li>
                
                </ul> 
                <div class="align-center">
                    <input type="submit" value="주문">
                    <input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
                </div>                               
            </form>
        </div>
    </div>
   
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
    
    <script>
        $(document).ready(function() {
            // 폼 제출 시 Ajax 요청 보내기
            $("#membershipForm").submit(function(event) {
                event.preventDefault();  // 폼 제출 기본 동작 방지

                var typeId = $("#typeId").val();

                // Ajax 요청
                $.ajax({
                    url: "membershipBuy22.do",  // 서블릿 URL
                    method: "POST",
                    data: { typeId: typeId },  // 폼에서 선택한 typeId 데이터 전달
                    dataType: "json",
                    success: function(response) {
                        // 응답 처리
                     
                    error: function() {
                        $("#result").html("<p style='color:red;'>서버와 연결할 수 없습니다.</p>");
                    }
                });
            });
        });
    </script>
</body>
</html>
