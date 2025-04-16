<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>aside</title>
    <link rel="stylesheet" href="">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/MS.css" type="text/css">
</head>
<body>
    <div id="box_out">
        <div id="box_in_1">
            <img src="${pageContext.request.contextPath}/images/image 1.png" alt="">
            <p>친구 만들기</p>
        </div>
        <div class="box_in_2">
            <div class="dropdown">
                <button class="dropdown-button toggle-button">친구만들기
                   <img src="${pageContext.request.contextPath}/images/free-icon-down-2732661 2.png" class="toggle-image" alt="">
                </button>
                <div class="dropdown-content">
                  <a href="${pageContext.request.contextPath}/friendSearch/friendList.do">친구 검색</a>
                  <a href="${pageContext.request.contextPath}/friendSearch/reciverList.do">받은 요청</a>
                </div>
              </div>
              
              <div class="dropdown">
                <button class="dropdown-button toggle-button">메시지
                   <img src="${pageContext.request.contextPath}/images/free-icon-down-2732661 2.png" class="toggle-image" alt="">
                </button>
                <div class="dropdown-content">
                  <a href="${pageContext.request.contextPath}/friendSearch/chatDetail.do">메시지 보내기</a>
         
   
                </div>
              </div>

             
        </div>

    </div>

    <script>
       // 두 이미지 경로 설정
const image1 = "${pageContext.request.contextPath}/images/free-icon-down-2732661%201.png";

const image2 = "${pageContext.request.contextPath}/images/free-icon-down-2732661%202.png"; // 두 번째 이미지


document.querySelectorAll('.dropdown-button').forEach(function(button) {
    button.addEventListener('click', function(event) {
        // 이미지 토글
        const img = this.querySelector('.toggle-image'); // 각 버튼의 이미지
        const currentSrc = img.getAttribute('src');  // 이미지 경로 가져오기
        
        // 이미지 변경
        if (currentSrc === image1) {
            img.setAttribute('src', image2);  // 첫 번째 이미지에서 두 번째 이미지로 변경
        } else {
            img.setAttribute('src', image1);  // 두 번째 이미지에서 첫 번째 이미지로 변경
        }

        // 드롭다운 토글
        const dropdownContent = this.nextElementSibling;
        dropdownContent.classList.toggle('show');

        // 클릭된 버튼이 드롭다운 이외의 영역일 경우 드롭다운 숨기기
        event.stopPropagation();  // 클릭 이벤트가 상위 요소로 전달되지 않도록 막기
    });
});

// 다른 곳을 클릭하면 드롭다운 숨기기
window.addEventListener('click', function(e) {
    if (!e.target.matches('.dropdown-button')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        for (var i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
});

    </script>
</body>
</html>