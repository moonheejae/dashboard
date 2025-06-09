"use strict"; // Strict 모드를 활성화
// 소켓 연결 상태 관리
let ws = null;
let isConnected = false;


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
    connectSocket();
    // 데이터 가져오기

}

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
// 소켓 연결
function connectSocket() {
    try {
        // 엔드포인트 수정: /realtime (서버와 일치)
        ws = new SockJS('http://192.168.0.14:8021/ws');
        // ws = new SockJS('http://52.77.138.41:8021/ws');

        ws.onopen = function() {
            console.log('✅ 연결 성공');
            isConnected = true;
            updateConnectionStatus(true);
        };

        ws.onmessage = function(event) {
            console.log('📨 수신:', event.data);
            handleMessage(JSON.parse(event.data));
        };

        ws.onclose = function(event) {
            console.log('❌ 연결 종료:', event.code);
            isConnected = false;
            updateConnectionStatus(false);

            // 5초 후 재연결
            setTimeout(connectSocket, 5000);
        };

        ws.onerror = function(error) {
            console.error('🚨 오류:', error);
            isConnected = false;
            updateConnectionStatus(false);
        };

    } catch (error) {
        console.error('연결 실패:', error);
        setTimeout(connectSocket, 5000);
    }
}

// 메시지 처리
function handleMessage(message) {
    switch(message.type) {
        case monitoringChannel.COMMUNITY:
            console.log('커뮤니티 :', message.data);
            updateLiveCommunityPost(message.data);
            break;
        case monitoringChannel.CHAT:
            updateLiveChat(message.data); // 현재 1:1 채팅, 중고 거래 채팅의 구분 없이 노출, 추후 분리 필요시 분리
            break;
        case monitoringChannel.TRADE_CHAT:
            updateLiveChat(message.data);
            break;
        case monitoringChannel.EXE:
            console.log('EXE 발생', message.type);
            updateLiveExe(message.data);
            break;
        case monitoringChannel.SCAN:
            console.log('스캔 발생', message.type);
            updateLiveScan(message.data);
            break;
        default:
            console.log('기타:', message);
    }
}

// 실시간 스캔 정보
function updateLiveScan(jsonData) {
    let nation = jsonData.address_n;
    let idx = jsonData.idx; // HT, COP, Global
    let device = jsonData.device_imei;
    let customer = jsonData.customer_name;
    let brand = jsonData.brand_name;
    let create_date = jsonData.det_time.split('.')[0].replace('T', ' ');

    const newRow = `
            <tr>
                <td>${create_date}</td>
                <td>${idx}</td>
                <td>${customer}</td>
                <td>${brand}</td>
                <td>${device}</td>
                <td>${nation}</td>
            </tr>
        `;
    $('#scanTable tbody').prepend(newRow);
}

// 실시간 실행 정보
function updateLiveExe(jsonData) {
    let nation = jsonData.address_n;
    let app_kind = jsonData.app_kind; // HT, COP, Global
    let device = jsonData.device_imei;
    let create_date = jsonData.create_date.split('.')[0].replace('T', ' ');

    const newRow = `
            <tr>
                <td>${create_date}</td>
                <td>${device}</td>
                <td>${app_kind}</td>
                <td>${nation}</td>
            </tr>
        `;
    $('#exeTable tbody').prepend(newRow);
}

// 실시간 커뮤니티 게시물
function updateLiveCommunityPost(jsonData) {
    let title = jsonData.title;
    let app_kind = jsonData.app_kind; // HT, COP, Global
    let user_nickname = jsonData.user_nickname || jsonData.user_no;
    let create_date = jsonData.create_date.split('.')[0].replace('T', ' ');

    const newRow = `
            <tr>
                <td>${app_kind}</td>
                <td>${create_date}</td>
                <td>${title}</td>
                <td>${user_nickname}</td>
            </tr>
        `;

    if( jsonData.type === 'TradeEntitiy' ){ // 정품 인증 거래
        $('#communityTable tbody').prepend(newRow);
    }else if( jsonData.type === 'CommProductReviewEntity' ){ // 정품 제품 리뷰
        $('#communityTable tbody').prepend(newRow);
    }else if( jsonData.type === 'CommDebateEntity' ){ // 정품 Qna
        $('#communityTable tbody').prepend(newRow);
    }else if( jsonData.type === 'CommInfoEntity' ){ // 정품 판별 팁
        $('#communityTable tbody').prepend(newRow);
    }
}

function updateLiveChat(jsonData){
    let nickName = jsonData.nick_name;
    let content = jsonData.content;
    let create_date = jsonData.create_date.split('.')[0].replace('T', ' ');

    // 실시간 중고 거래 채팅(1:1 채팅) 로직 추가
    const newRow = `
            <tr>
                <td>${create_date}</td>
                <td>${content}</td>
                <td>${nickName}</td>
            </tr>
        `;
    $('#chatTable tbody').prepend(newRow);
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


