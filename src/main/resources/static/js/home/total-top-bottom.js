"use strict"; // Strict 모드를 활성화

var totalList = []; // 각 탭에 맞는 List를 전역변수로 설정하여 리소스 최소화

$(document).ready(function() {
    init();
});

function init() {
    totalListFunction(); // 접근 시 데이터 호출
    publishing.hamClick();
    publishing.cancleClick();
    publishing.dateShow();
    publishing.rightTopGrid();
    publishing.toggleBtn();
}

// 년도 변경 시 실행
function searchFunction(year) {
    // 전역변수에 셋팅되어있는 totalList 사용
}

function totalListFunction() {
    // Publishing.ShowLoading(); // 로딩바 start
    var url = totalControllerUrl + "/total"
    const formData = {

    };
    CommonModule.commonSelectData(formData, url,
        function (response) {
        if (response) {
            var jsonData = response.data.list;
            if(jsonData.length > 0) {
                jsonData = totalList;
            }else {
                console.log("데이터가 없습니다.");
            }
            // Publishing.HiddenLoading(); // 로딩바 end
        }else {
            console.log("error");
            // Publishing.HiddenLoading(); // 로딩바 end
        }
    });
}