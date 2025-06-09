// main.js 작성자 CYJ ( 2024/05/17 )

"use strict"; // Strict 모드를 활성화

const deFaultUrl = "http://192.168.0.14:8021/"; // local
// const deFaultUrl = "http://52.77.138.41:8021/"; // test server
// const deFaultUrl = "http://hiddentagiqr.com:8020/"; // application.urls.data_report 와 동일
const downloadControllerUrl = "api/download";
const exeControllerUrl = "api/exe";
const scanControllerUrl = "api/scan";
const totalControllerUrl = "api/total";
const marketStatusControllerUrl = "api/status";
const commentControllerUrl = "api/comment";
const noticeControllerUrl = "api/notice";
const authControllerUrl = "api/auth";
const orderControllerUrl = "api/order";
const userControllerUrl = "api/user";
const marketingControllerUrl = "api/marketing";
const customerControllerUrl = "api/customer";
let userNo = "0";

// 모니터링(redis) 채널 이름
const monitoringChannel = {
    TRADE_CHAT: 'trade_chat_monitor',
    CHAT: 'chat_monitor',
    COMMUNITY: 'community_monitor',
    EXE: 'exe_monitor',
    SCAN: 'scan_monitor'
};

const defaultSettings = {
    theme: 'light',
    language: localStorage.getItem("Lang") || 'ko',
}

// 초기화 코드
$(document).ready(function() {
    if( userNo === "" ||  userNo === "0" ){
        var url = authControllerUrl + "/loginCheck"
        const formData = {};
        CommonModule.commonSelectData(formData, url,
            function (response) {
                if (response !== "0") {
                    userNo = parseInt(response);
                    // 최영준, 김현태, 문희재 수정 가능
                    if( userNo === 319 || userNo === 288 || userNo === 327 ){
                        $('.save').show();
                        $('.modify').show();
                    }
                }else {
                    console.log("유저 번호 체크 필요 userNo = " + response);
                }
            });
    }
});

let today = new Date();
let year = today.getFullYear();
let month = today.getMonth() + 1;
let date = today.getDate();

const DefaultDate= {
    // 오늘이 몇일
    todayDate:function() {
      return date;
    },
    // 오늘이 몇월
    todayMonth: function() {
        return month;
    },
    // 오늘이 몇년도
    todayYear: function() {
        return year;
    },
    // 오늘이 몇년도 몇월
    todayYearMonth: function() {
      return year+"-"+month
    },
    // 오늘이 해당월의 몇 주차
    todayWeek: function() {
        // 현재 날짜 기준 해당 달의 첫 번째 날
        var firstDayOfMonth = new Date(year, month, 1);
        // 첫 번째 날이 속한 주가 몇 주차인지 계산
        var firstWeek = Math.ceil((date + firstDayOfMonth.getDay())/7);
        return firstWeek;
    },
    nowDiff: function(date) {
        var createDate = new Date(date);
        var now = new Date();
        var timeDiff = now - createDate;
        var secondsDiff = Math.floor(timeDiff / 1000);
        var minutesDiff = Math.floor(secondsDiff / 60);
        var hoursDiff = Math.floor(minutesDiff / 60);
        var daysDiff = Math.floor(hoursDiff / 24);
        return daysDiff + "일 " + (hoursDiff % 24) + "시간 " + (minutesDiff % 60) + "분 전"
    },
    chatNowDate: function(dateStr) {
        var now = new Date(dateStr);
        var year = now.getFullYear().toString();
        var month = ('0' + (now.getMonth() + 1)).slice(-2);
        var day = ('0' + now.getDate()).slice(-2)
        return year+'-'+month+'-'+day;
    },
    chatNowTime: function() {
        var now = new Date();
        var hours = ('0' + now.getHours()).slice(-2);
        var minutes = ('0' + now.getMinutes()).slice(-2);
        var seconds = ('0' + now.getSeconds()).slice(-2);
        return hours+':'+minutes+':'+seconds;
    },
    previousYear:function(dateStr) {
        // "yyyy-mm" 형식의 문자열을 분리
        const [year, month] = dateStr.split('-').map(Number);

        // 연도만 1년 감소시키고 월은 동일하게 유지
        const previousYear = year - 1;
        const monthStr = String(month).padStart(2, '0'); // 두 자리 수로 맞추기

        // "yyyy-mm" 형식으로 전년 동월 반환
        return `${previousYear}-${monthStr}`;
    },
    previousMonth:function(dateStr) {
        // "yyyy-mm" 형식의 문자열을 Date 객체로 변환
        const [year, month] = dateStr.split('-').map(Number);
        const date = new Date(year, month - 1); // 월은 0부터 시작하므로 month-1

        // 전월로 이동
        date.setMonth(date.getMonth() - 1);

        // "yyyy-mm" 형식으로 전월 반환
        const previousYear = date.getFullYear();
        const previousMonth = String(date.getMonth() + 1).padStart(2, '0'); // 두 자리 수로 맞추기

        return `${previousYear}-${previousMonth}`;
    },
    yearTake:function() {
        const years = [];
        const currentYear = new Date().getFullYear();

        for (let year = currentYear; year >= 2011; year--) {
            years.push(year);
        }

        return years;
    }
}

var Publishing = {
    ShowLoading : function() {
        console.log("로딩바 시작입니다.");
    },
    HiddenLoading : function() {
        console.log("로딩바 끝났습니다.");
    }
}