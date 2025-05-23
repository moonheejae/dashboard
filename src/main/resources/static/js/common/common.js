// main.js 작성자 CYJ ( 2024/05/17 )

"use strict"; // Strict 모드를 활성화

const CommonModule = {
    commonSelectData: function (formData, url, successCallback) {
        $.ajax({
            type: 'GET',
            url: deFaultUrl + url, // 서버의 URL로 변경
            data: formData,
            dataType: 'json',
            success: successCallback,
            error: function (request, status, error) {
                console.log("code : " + request.status + " / error : " + error);
            }
        });
    },

    commonInsertData: function (formData, url, successCallback) {
        $.ajax({
            type: 'POST',
            url: deFaultUrl + url, // 서버의 URL로 변경
            data: JSON.stringify(formData),
            contentType: 'application/json', // 데이터 타입을 JSON으로 지정
            success: successCallback,
            error: function (request, status, error) {
                console.log("code : " + request.status + " / error : " + error);
            }
        });
    },

    commonUpdateData: function (formData, url, successCallback) {
        $.ajax({
            type: 'PUT',
            url: deFaultUrl + url, // 서버의 URL로 변경
            data: JSON.stringify(formData),
            contentType: 'application/json', // 데이터 타입을 JSON으로 지정
            success: successCallback,
            error: function (request, status, error) {
                console.log("code : " + request.status + " / error : " + error);
            }
        });
    },

    commonDeleteData: function (formData, url, successCallback) {
        $.ajax({
            type: 'DELETE',
            url: deFaultUrl + url, // 서버의 URL로 변경
            data: JSON.stringify(formData),
            contentType: 'application/json', // 데이터 타입을 JSON으로 지정
            success: successCallback,
            error: function (request, status, error) {
                console.log("code : " + request.status + " / error : " + error);
            }
        });
    },
};

const PageMove = {
    pagesMove : function(pageName) {
        location.href = "/"+pageName;
    }
}