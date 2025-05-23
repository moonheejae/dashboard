"use strict"; // Strict 모드를 활성화


$(document).ready(function() {
    init();
});

// 1초마다 업데이트
setInterval(updateDateTime, 1000);

function init() {
    // 상단 시간 셋팅
    updateDateTime();
    // 소켓 연결 - 초기 상태는 OFFLINE
    updateConnectionStatus(false);
    // connectSocket();
    // 데이터 가져오기

}
// 소켓 연결 상태 관리
let socket = null;
let isConnected = false;

// 상태 업데이트 함수
function updateConnectionStatus(connected) {
    const statusIndicator = $("#statusIndicator");
    const statusText = $("#statusText");

    if (connected) {
        // 기존 상태 클래스 제거 후 새 상태 추가
        statusIndicator
            .removeClass('offline')
            .addClass('online');
        statusText.text('ONLINE');
        isConnected = true;
    } else {
        statusIndicator
            .removeClass('online')
            .addClass('offline');
        statusText.text('OFFLINE');
        isConnected = false;
    }
}

// 소켓 연결 시도
function connectSocket() {
    try {
        // 실제 소켓 서버 주소로 변경하세요
        socket = new WebSocket('ws://localhost:8080');

        socket.onopen = function(event) {
            console.log('소켓 연결 성공');
            updateConnectionStatus(true);
        };

        socket.onmessage = function(event) {
            console.log('메시지 수신:', event.data);
            // 여기서 받은 데이터를 처리
        };

        socket.onclose = function(event) {
            console.log('소켓 연결 종료');
            updateConnectionStatus(false);
            // 5초 후 재연결 시도
            setTimeout(connectSocket, 5000);
        };

        socket.onerror = function(error) {
            console.error('소켓 에러:', error);
            updateConnectionStatus(false);
        };

    } catch (error) {
        console.error('소켓 연결 실패:', error);
        updateConnectionStatus(false);
        // 5초 후 재연결 시도
        setTimeout(connectSocket, 5000);
    }
}

function updateDateTime() {
    const now = new Date();

    // 날짜 포맷: 2024.12.25 WED
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const date = String(now.getDate()).padStart(2, '0');
    const days = ['일', '월', '화', '수', '목', '금', '토'];
    const dayOfWeek = days[now.getDay()];

    const dateString = `${year}.${month}.${date} ${dayOfWeek}`;

    // 시간 포맷: 15:30:45
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');

    const timeString = `${hours}:${minutes}:${seconds}`;

    $("#dateDisplay").text(dateString);
    $("#timeDisplay").text(timeString);
}



