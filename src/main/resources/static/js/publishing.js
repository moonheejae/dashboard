let publishing = {
    // 햄버거 메뉴 클릭
    hamClick : function() {
        $(".nav-wrap .ic-ham").on("click", function (event) {
            $(".sidebar-wrap").fadeIn(500).addClass('active');
            event.stopPropagation(); // 이벤트 전파 방지 (문서 클릭 이벤트 방해 X)
        });
    },
    // 네비게이션바 x 버튼 클릭
    cancleClick : function() {
        $(".ic-cancle").on("click", function (event) {
            $(".sidebar-wrap").fadeOut(500).removeClass('active');
            event.stopPropagation(); // 이벤트 전파 방지 (문서 클릭 이벤트 방해 X)
        });
    },
    // 달력
    dateShow : function() {
        let today = new Date();
        let currentYear = today.getFullYear();
        let currentMonth = ('0' + (today.getMonth() + 1)).slice(-2);
        
        // 강제로 기본 값을 적용
        setTimeout(function() {
            $("#datepicker").val(currentYear + '-' + currentMonth);
        }, 100); // 약간의 딜레이 추가

        $("#datepicker").datepicker({
            dateFormat: "yy-mm",
            changeMonth: true,
            changeYear: true,
            showButtonPanel: true,
            maxDate: new Date(currentYear, currentMonth - 1, 1), // 현재 월 이후 선택 제한
            /*beforeShow: function(input, inst) {
                setTimeout(function() {
                    let month = $(".ui-datepicker-month option:selected").val();
                    let year = $(".ui-datepicker-year option:selected").val();
                    $(input).val(year + '-' + ('0' + (parseInt(month) + 1)).slice(-2));
                }, 10);
            },*/
            beforeShow: function(input) {
                // 현재 입력된 값 가져오기
                let dateValue = $(input).val();
                if (dateValue) {
                    let [year, month] = dateValue.split('-');
                    $(this).datepicker('option', 'defaultDate', new Date(year, month - 1, 1));
                }
            },
            onClose: function(dateText, inst) {
                let month = $(".ui-datepicker-month option:selected").val();
                let year = $(".ui-datepicker-year option:selected").val();
                $(this).val(year + '-' + ('0' + (parseInt(month) + 1)).slice(-2));

                // 날짜 변경 시 호출
                $("#gridChart").jqGrid('GridUnload');
                changeDate();
            }
        });
    },
    // 셀렉박스
    selectBox : function() {
        $(".select-btn").click(function () {
            $(".options").slideToggle(200);
            $(".arrow").toggleClass("rotate");
        });

        // 옵션 클릭 시 선택 값 변경
        $(".option").click(function () {
            $("#selected-text").text($(this).text());
            $(".options").slideUp(200);
            $(".arrow").removeClass("rotate");
        });

        // 외부 클릭 시 드롭다운 닫기
        $(document).click(function (event) {
            if (!$(event.target).closest(".custom-select").length) {
                $(".options").slideUp(200);
                $(".arrow").removeClass("rotate");
            }
        });
    },
    // 데이트피커
    dateShowPicker : function() {
        let today = new Date();
        let currentYear = today.getFullYear();
        let currentMonth = ('0' + (today.getMonth() + 1)).slice(-2);

        // 강제로 기본 값을 적용
        setTimeout(function() {
            $(".dateShowPicker").val(currentYear + '-' + currentMonth);
        }, 100); // 약간의 딜레이 추가

        document.querySelectorAll(".dateShowPicker").forEach(el => {
            flatpickr(el, {
                dateFormat: "Y-m", // YYYY-MM 형식으로 표시
                defaultDate: new Date(), // 기본값: 오늘 날짜
                disableMobile: true, // 모바일에서 기본 datepicker 방지
                clickOpens: true, // 클릭하면 열기
                maxDate: new Date().setMonth(today.getMonth()), // 금월 이후는 선택 불가
                plugins: [new monthSelectPlugin({
                    shorthand: true, // 월을 Jan, Feb 같은 짧은 표기로 표시
                    dateFormat: "Y-m", // YYYY-MM 형식 유지
                    theme: "light" // 기본 스타일
                })]
            });
        });
    },

    // 데이트피커

    // 데이트피커 카테고리별
    dateShowPickerCategory: function () {
        const startInput = document.querySelector(".startShowPicker");
        const endInput = document.querySelector(".endShowPicker");

        let startDate = new Date(); // 기본 시작일
        let endDate = new Date();   // 기본 종료일

        // 초기 input 값 설정
        const formatMonth = (date) => {
            return date.getFullYear() + '-' + ('0' + (date.getMonth() + 1)).slice(-2);
        };

        startInput.value = formatMonth(startDate);
        endInput.value = formatMonth(endDate);

        const disableMonths = (fp, compareDate, isStart) => {
            setTimeout(() => {
                const months = fp.calendarContainer.querySelectorAll(".flatpickr-monthSelect-month");
                if (!months) return;

                months.forEach((monthEl, i) => {
                    const year = fp.currentYear;
                    const month = i;
                    const date = new Date(year, month);

                    const shouldDisable = isStart ? date > compareDate : date < compareDate;

                    if (shouldDisable) {
                        monthEl.classList.add("flatpickr-disabled");
                        monthEl.style.pointerEvents = "none";
                        monthEl.style.opacity = "0.3";
                    } else {
                        monthEl.classList.remove("flatpickr-disabled");
                        monthEl.style.pointerEvents = "";
                        monthEl.style.opacity = "";
                    }
                });
            }, 10);
        };

        const startPicker = flatpickr(startInput, {
            dateFormat: "Y-m",
            disableMobile: true,
            defaultDate: startDate,
            plugins: [new monthSelectPlugin({
                shorthand: true,
                dateFormat: "Y-m",
                theme: "light"
            })],
            onChange: function (selectedDates) {
                if (selectedDates.length > 0) {
                    startDate = selectedDates[0];

                    // 종료월 업데이트
                    if (endDate < startDate) {
                        endDate = new Date(startDate);
                        endInput._flatpickr.setDate(endDate);
                    }
                    endPicker.setDate(endDate);
                }
            },
            onOpen: function () {
                if (endDate) disableMonths(startPicker, endDate, true);
            },
            onYearChange: function () {
                if (endDate) disableMonths(startPicker, endDate, true);
            }
        });

        const endPicker = flatpickr(endInput, {
            dateFormat: "Y-m",
            disableMobile: true,
            defaultDate: endDate,
            plugins: [new monthSelectPlugin({
                shorthand: true,
                dateFormat: "Y-m",
                theme: "light"
            })],
            onChange: function (selectedDates) {
                if (selectedDates.length > 0) {
                    endDate = selectedDates[0];

                    if (startDate > endDate) {
                        startDate = new Date(endDate);
                        startInput._flatpickr.setDate(startDate);
                    }
                    startPicker.setDate(startDate);
                }
            },
            onOpen: function () {
                if (startDate) disableMonths(endPicker, startDate, false);
            },
            onYearChange: function () {
                if (startDate) disableMonths(endPicker, startDate, false);
            }
        });
    },
    // 데이트피커 - 기간별 검색
    dateShowPickerPeriod: function(startId,endId, startMonth) {
        const startInput = document.getElementById(startId);
        const endInput = document.getElementById(endId);

        let startDate =  startMonth; // 기본 시작일
        let endDate = new Date();   // 기본 종료일

        // 초기 input 값 설정
        const formatMonth = (date) => {
            return date.getFullYear() + '-' + ('0' + (date.getMonth() + 1)).slice(-2);
        };

        startInput.value = formatMonth(startDate);
        endInput.value = formatMonth(endDate);

        const disableMonths = (fp, compareDate, isStart) => {
            setTimeout(() => {
                const months = fp.calendarContainer.querySelectorAll(".flatpickr-monthSelect-month");
                if (!months) return;

                months.forEach((monthEl, i) => {
                    const year = fp.currentYear;
                    const month = i;
                    const date = new Date(year, month);

                    const shouldDisable = isStart ? date > compareDate : date < compareDate;

                    if (shouldDisable) {
                        monthEl.classList.add("flatpickr-disabled");
                        monthEl.style.pointerEvents = "none";
                        monthEl.style.opacity = "0.3";
                    } else {
                        monthEl.classList.remove("flatpickr-disabled");
                        monthEl.style.pointerEvents = "";
                        monthEl.style.opacity = "";
                    }
                });
            }, 10);
        };

        const startPicker = flatpickr(startInput, {
            dateFormat: "Y-m",
            disableMobile: true,
            defaultDate: startDate,
            plugins: [new monthSelectPlugin({
                shorthand: true,
                dateFormat: "Y-m",
                theme: "light"
            })],
            onChange: function (selectedDates) {
                if (selectedDates.length > 0) {
                    startDate = selectedDates[0];

                    // 종료월 업데이트
                    if (endDate < startDate) {
                        endDate = new Date(startDate);
                        endInput._flatpickr.setDate(endDate);
                    }
                    endPicker.setDate(endDate);
                }
            },
            onOpen: function () {
                if (endDate) disableMonths(startPicker, endDate, true);
            },
            onYearChange: function () {
                if (endDate) disableMonths(startPicker, endDate, true);
            }
        });

        const endPicker = flatpickr(endInput, {
            dateFormat: "Y-m",
            disableMobile: true,
            defaultDate: endDate,
            plugins: [new monthSelectPlugin({
                shorthand: true,
                dateFormat: "Y-m",
                theme: "light"
            })],
            onChange: function (selectedDates) {
                if (selectedDates.length > 0) {
                    endDate = selectedDates[0];

                    if (startDate > endDate) {
                        startDate = new Date(endDate);
                        startInput._flatpickr.setDate(startDate);
                    }
                    startPicker.setDate(startDate);
                }
            },
            onOpen: function () {
                if (startDate) disableMonths(endPicker, startDate, false);
            },
            onYearChange: function () {
                if (startDate) disableMonths(endPicker, startDate, false);
            }
        });
    },
    // 데이트피커 - 기간별 검색
    dateShowPickerPeriodNotTodayAfter: function(startId,endId, startMonth) {
        const startInput = document.getElementById(startId);
        const endInput = document.getElementById(endId);

        let startDate =  startMonth; // 기본 시작일
        let endDate = new Date();   // 기본 종료일

        // 초기 input 값 설정
        const formatMonth = (date) => {
            return date.getFullYear() + '-' + ('0' + (date.getMonth() + 1)).slice(-2);
        };

        startInput.value = formatMonth(startDate);
        endInput.value = formatMonth(endDate);

        const disableMonths = (fp, compareDate, isStart) => {
            setTimeout(() => {
                const months = fp.calendarContainer.querySelectorAll(".flatpickr-monthSelect-month");
                if (!months) return;

                const current = new Date(); // 현재 날짜
                const currentYear = current.getFullYear();
                const currentMonth = current.getMonth(); // 0-based

                months.forEach((monthEl, i) => {
                    const year = fp.currentYear;
                    const month = i;
                    const date = new Date(year, month);

                    const isAfterCompare = isStart ? date > compareDate : date < compareDate;
                    const isAfterCurrentMonth = (year > currentYear) || (year === currentYear && month > currentMonth);

                    const shouldDisable = isAfterCompare || isAfterCurrentMonth;

                    if (shouldDisable) {
                        monthEl.classList.add("flatpickr-disabled");
                        monthEl.style.pointerEvents = "none";
                        monthEl.style.opacity = "0.3";
                    } else {
                        monthEl.classList.remove("flatpickr-disabled");
                        monthEl.style.pointerEvents = "";
                        monthEl.style.opacity = "";
                    }
                });
            }, 10);
        };

        const startPicker = flatpickr(startInput, {
            dateFormat: "Y-m",
            disableMobile: true,
            defaultDate: startDate,
            plugins: [new monthSelectPlugin({
                shorthand: true,
                dateFormat: "Y-m",
                theme: "light"
            })],
            onChange: function (selectedDates) {
                if (selectedDates.length > 0) {
                    startDate = selectedDates[0];

                    // 종료월 업데이트
                    if (endDate < startDate) {
                        endDate = new Date(startDate);
                        endInput._flatpickr.setDate(endDate);
                    }
                    endPicker.setDate(endDate);
                }
            },
            onOpen: function () {
                if (endDate) disableMonths(startPicker, endDate, true);
            },
            onYearChange: function () {
                if (endDate) disableMonths(startPicker, endDate, true);
            }
        });

        const endPicker = flatpickr(endInput, {
            dateFormat: "Y-m",
            disableMobile: true,
            defaultDate: endDate,
            plugins: [new monthSelectPlugin({
                shorthand: true,
                dateFormat: "Y-m",
                theme: "light"
            })],
            onChange: function (selectedDates) {
                if (selectedDates.length > 0) {
                    endDate = selectedDates[0];

                    if (startDate > endDate) {
                        startDate = new Date(endDate);
                        startInput._flatpickr.setDate(startDate);
                    }
                    startPicker.setDate(startDate);
                }
            },
            onOpen: function () {
                if (startDate) disableMonths(endPicker, startDate, false);
            },
            onYearChange: function () {
                if (startDate) disableMonths(endPicker, startDate, false);
            }
        });
    },

    // 토글 버튼 클릭
    toggleBtn : function() {
        $(".toggle-btn").on('click', function() {
            $('.toggle-btn').removeClass('active');
            $(this).addClass('active');
        })
    },
    dateChangePicker : function() {
        let today = new Date();
        let currentYear = today.getFullYear();
        let currentMonth = ('0' + (today.getMonth() + 1)).slice(-2);

        // 강제로 기본 값을 적용
        setTimeout(function() {
            $(".dateChangePicker").val(currentYear + '-' + currentMonth);
        }, 100); // 약간의 딜레이 추가

        document.querySelectorAll(".dateChangePicker").forEach(el => {
            flatpickr(el, {
                dateFormat: "Y-m", // YYYY-MM 형식으로 표시
                defaultDate: new Date(), // 기본값: 오늘 날짜
                disableMobile: true, // 모바일에서 기본 datepicker 방지
                clickOpens: true, // 클릭하면 열기
                maxDate: new Date().setMonth(today.getMonth()), // 금월 이후는 선택 불가
                plugins: [new monthSelectPlugin({
                    shorthand: true, // 월을 Jan, Feb 같은 짧은 표기로 표시
                    dateFormat: "Y-m", // YYYY-MM 형식 유지
                    theme: "light" // 기본 스타일
                })],
                // 날짜 변경 이벤트 추가
                onChange: function(selectedDates, dateStr, instance) {
                    // 선택된 날짜 값 설정
                    $(instance.element).val(dateStr);

                    // 기존 그리드 제거
                    $("#gridChart").jqGrid('GridUnload');
                    // 새로운 데이터로 그리드 생성
                    changeDate();
                },
            });
        });
    },
    // 신규 데이트 피커 (국가별 TOP10)
    daterangepicker: function () {
        const today = moment();
    
        function setDateValue(inputId, date) {
            $(`#${inputId}`).val(date.format('YYYY-MM-DD'));
        }
    
        // 공통 옵션
        const options = {
            singleDatePicker: true,
            showDropdowns: true,
            autoApply: false,
            locale: {
                format: 'YYYY-MM-DD',
                applyLabel: '확인',
                cancelLabel: '초기화',
                daysOfWeek: ['일', '월', '화', '수', '목', '금', '토'],
                monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                firstDay: 0
            }
        };
    
        // 시작일용 달력
        $('#startDate').daterangepicker(options, function (start) {
            setDateValue('startDate', start);
    
            // 종료일 달력의 최소 날짜를 시작일로 제한
            $('#endDate').data('daterangepicker').minDate = start;
        });
    
        // 종료일용 달력
        $('#endDate').daterangepicker(options, function (end) {
            setDateValue('endDate', end);
            // 시작일은 자유 선택 가능하므로 maxDate 설정하지 않음
        });
    
        // 시작일 취소 시
        $('#startDate').on('cancel.daterangepicker', function (ev, picker) {
            setDateValue('startDate', today);
            picker.setStartDate(today);
    
            // 종료일 달력의 minDate를 오늘로 초기화
            $('#endDate').data('daterangepicker').minDate = today;
        });
    
        // 종료일 취소 시
        $('#endDate').on('cancel.daterangepicker', function (ev, picker) {
            setDateValue('endDate', today);
            picker.setStartDate(today);
    
            // 종료일만 제한하므로 startDate의 maxDate는 설정하지 않음
        });
    
        // 초기값
        setDateValue('startDate', today);
        setDateValue('endDate', today);
    
        // 초기 minDate만 설정 (endDate만)
        $('#endDate').data('daterangepicker').minDate = today;
    },

    customDaterangepicker: function (selectedDate) {
        const today = moment();
        const startDateValue = selectedDate ? moment(selectedDate) : today;

        function setDateValue(inputId, date) {
            $(`#${inputId}`).val(date.format('YYYY-MM-DD')).trigger('change');
            // input의 값이 변경된 후 change 이벤트 수동으로 트리거
        }

        // 공통 옵션
        const options = {
            singleDatePicker: true,
            showDropdowns: true,
            autoApply: true,
            locale: {
                format: 'YYYY-MM-DD',
                applyLabel: '확인',
                cancelLabel: '초기화',
                daysOfWeek: ['일', '월', '화', '수', '목', '금', '토'],
                monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                firstDay: 0
            }
        };

        // 시작일용 달력 - 선택된 날짜로 설정
        const startOptions = {
            ...options,
            startDate: startDateValue
        };

        // 시작일용 달력
        $('#startDate').daterangepicker(startOptions, function (start) {
            setDateValue('startDate', start);

            // 종료일 달력의 최소 날짜를 시작일로 제한
            $('#endDate').data('daterangepicker').minDate = start;
        });

        // 종료일용 달력
        $('#endDate').daterangepicker(options, function (end) {
            setDateValue('endDate', end);
            // 시작일은 자유 선택 가능하므로 maxDate 설정하지 않음
        });

        // 시작일 취소 시
        $('#startDate').on('cancel.daterangepicker', function (ev, picker) {
            setDateValue('startDate', today);
            picker.setStartDate(today);

            // 종료일 달력의 minDate를 오늘로 초기화
            $('#endDate').data('daterangepicker').minDate = today;
        });

        // 종료일 취소 시
        $('#endDate').on('cancel.daterangepicker', function (ev, picker) {
            setDateValue('endDate', today);
            picker.setStartDate(today);

            // 종료일만 제한하므로 startDate의 maxDate는 설정하지 않음
        });

        // 초기값
        setDateValue('startDate', startDateValue);
        setDateValue('endDate', today);

        // 초기 minDate만 설정 (endDate만)
        $('#endDate').data('daterangepicker').minDate = today;
    },

    // 그리드 부분
    rightTopGrid : function() {
        $("#grid, #grid2").jqGrid({
            datatype: "local",
            colNames: ["구분", "전체", "2025-01", "2025-02", "증감"],
            colModel: [
                { name: "category", width: 120, align: "center", sorttype: "text", search: true },
                { name: "total", width: 120, align: "right", formatter: 'integer', search: true },
                { name: "jan", width: 120, align: "right", formatter: 'integer' },
                { name: "feb", width: 120, align: "right", formatter: 'integer' },
                { name: "change", width: 120, align: "right", formatter: function (cellvalue) {
                    let colorClass = cellvalue.includes("↓") ? "decrease" : "increase";
                    return `<span class='${colorClass}'>${cellvalue}</span>`;
                } }
            ],
            data: [
                { category: "고유기기", total: 5863964, jan: 73391, feb: 31975, change: "↓ 41,416" },
                { category: "1회 실행 기기", total: 3659442, jan: 27382, feb: 13863, change: "↓ 13,519" },
                { category: "다회 실행 기기", total: 2204522, jan: 46009, feb: 18112, change: "↓ 27,897" },
                { category: "고유기기", total: 5863964, jan: 73391, feb: 31975, change: "↓ 41,416" },
                { category: "1회 실행 기기", total: 3659442, jan: 27382, feb: 13863, change: "↓ 13,519" },
                { category: "다회 실행 기기", total: 2204522, jan: 46009, feb: 18112, change: "↓ 27,897" },
                { category: "고유기기", total: 5863964, jan: 73391, feb: 31975, change: "↓ 41,416" },
                { category: "1회 실행 기기", total: 3659442, jan: 27382, feb: 13863, change: "↓ 13,519" },
                { category: "다회 실행 기기", total: 2204522, jan: 46009, feb: 18112, change: "↓ 27,897" },
                { category: "고유기기", total: 5863964, jan: 73391, feb: 31975, change: "↓ 41,416" },
                { category: "1회 실행 기기", total: 3659442, jan: 27382, feb: 13863, change: "↓ 13,519" },
                { category: "다회 실행 기기", total: 2204522, jan: 46009, feb: 18112, change: "↓ 27,897" },
                { category: "고유기기", total: 5863964, jan: 73391, feb: 31975, change: "↓ 41,416" },
                { category: "1회 실행 기기", total: 3659442, jan: 27382, feb: 13863, change: "↓ 13,519" },
                { category: "다회 실행 기기", total: 2204522, jan: 46009, feb: 18112, change: "↓ 27,897" },
                { category: "고유기기", total: 5863964, jan: 73391, feb: 31975, change: "↓ 41,416" },
                { category: "1회 실행 기기", total: 3659442, jan: 27382, feb: 13863, change: "↓ 13,519" },
                { category: "다회 실행 기기", total: 2204522, jan: 46009, feb: 18112, change: "↓ 27,897" },
                { category: "고유기기", total: 5863964, jan: 73391, feb: 31975, change: "↓ 41,416" },
                { category: "1회 실행 기기", total: 3659442, jan: 27382, feb: 13863, change: "↓ 13,519" },
                { category: "다회 실행 기기", total: 2204522, jan: 46009, feb: 18112, change: "↓ 27,897" },
                { category: "전체 실행 횟수", total: 10800002, jan: 265998, feb: 98323, change: "↓ 167,675" }
            ],
            rowNum: 2,
            width: "100%",
            autowidth: true,
            viewrecords: true,
            shrinkToFit: true,
            gridview: true,
            autoencode: true,
            sortable: true,
            height: "auto",
        });
    },
    // 파이차트 부분
    pieChart : function( title, labels, datas, colors ) {
           // 데이터 및 상위 3개 요소를 기준으로 조건부 수치 표시
        const titles = '국가별 스캔 TOP10';
        const dataLabels = ['중국', '베트남', '인도네시아', '대한민국', '말레이시아', '태국', '대만', '일본'];
        const dataValues = [46.8, 18.4, 13.3, 10.0, 5.0, 3.0, 2.0, 1.5];
        const top3Indices = [0, 1, 2]; // 상위 3개 (중국, 베트남, 인도네시아)
        const color = [
            '#4A90E2', '#E94E3A', '#F5A623', '#B388FF', '#8CCFFF', '#FFADAD', '#FFD59E', '#D3B8FF'
        ];

        const ctx = document.getElementById('pieChart').getContext('2d');
        new Chart(ctx, {
            type: 'pie',
            data: {
                labels: labels,
                datasets: [{
                    data: datas,
                    backgroundColor: colors,
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    title: {
                        display: true,
                        text: title, // 차트 제목
                        font: {
                            size: 18,
                            weight: 'bold'
                        },
                        align: 'start', // 좌측 정렬
                        padding: {
                            bottom: 10
                        }
                    },
                    legend: {
                        position: 'right'
                    },
                    datalabels: {
                        color: '#fff', // 텍스트 색상
                        font: {
                            weight: 'bold',
                            size: 14
                        },
                        /*formatter: (value, context) => {
                            return top3Indices.includes(context.dataIndex) ? `${value}%` : ''; 
                        }*/
                        formatter: (value, ctx) => { // 수치를 %로 반환
                            const total = ctx.dataset.data.reduce((acc, data) => acc + data, 0);
                            const percentage = ((value * 100) / total).toFixed(1);
                            return percentage + '%';
                        },
                    }
                }
            },
            plugins: [ChartDataLabels] // 플러그인 활성화
        });
    },
    lineChart : function (label, datasets, title ){
        new Chart(document.getElementById('lineChart'), {
            type: 'line',
            data: {
                labels: label, // 월별 데이터 예시
                datasets: datasets
            },
            options: {
                responsive: true,
                maintainAspectRatio: false, // 캔버스 종횡비

                plugins: {
                    title: {
                        display: true,
                        text: title,
                        font: {
                            size: 20,    // 폰트 크기
                            weight: 'bold'  // 폰트 굵기 (선택사항)
                        }
                    },
                    tooltip : {

                    },
                    legend: {
                        position: 'bottom'
                    },
                    datalabels: false // 그래프 상단 숫자 제거
                },
                layout: {
                    padding: {
                        top: 20,
                        bottom: 20
                    }
                },
                scales: {
                    x: {
                        display : true,
                        beginAtZero: true,
                        title :{
                            display: true,
                            text: '연도 - 월',
                            fontsize: 10,
                            align: 'end',     // 끝으로 정렬
                            position: 'end'    // 축의 끝에 위치
                        },
                        ticks: {
                            autoSkip: true,
                            // maxTicksLimit: 12,  // 표시할 최대 눈금 수
                            callback: function(value, index) {
                                return label[index]; // Y축 숫자 포맷팅
                            }
                        }
                    },
                    y: {
                        display : true,
                        beginAtZero: true,
                        title :{
                            display: true,
                            text: '(건)',
                            fontsize: 10,
                            align: 'end',     // 끝으로 정렬
                            position: 'end'    // 축의 끝에 위치
                        },
                        ticks: {
                            autoSkip: true
                        }
                    }
                }, animation: {
                    duration: 1000,
                    easing: 'easeInOutQuad'
                },
            }
        });
    },
    lineMonthlyChart : function (label, datasets, title ){
        new Chart(document.getElementById('monthlyLineChart'), {
            type: 'line',
            data: {
                labels: label, // 월별 데이터 예시
                datasets: datasets
            },
            options: {
                responsive: true,
                maintainAspectRatio: false, // 캔버스 종횡비

                plugins: {
                    title: {
                        display: true,
                        text: title,
                        font: {
                            size: 20,    // 폰트 크기
                            weight: 'bold'  // 폰트 굵기 (선택사항)
                        }
                    },
                    tooltip : {

                    },
                    legend: {
                        position: 'bottom'
                    },
                    datalabels: false // 그래프 상단 숫자 제거
                },
                layout: {
                    padding: {
                        top: 20,
                        bottom: 20
                    }
                },
                scales: {
                    x: {
                        display : true,
                        beginAtZero: true,
                        title :{
                            display: true,
                            text: '연도 - 월',
                            fontsize: 10,
                            align: 'end',     // 끝으로 정렬
                            position: 'end'    // 축의 끝에 위치
                        },
                        ticks: {
                            autoSkip: true,
                            // maxTicksLimit: 12,  // 표시할 최대 눈금 수
                            callback: function(value, index) {
                                return label[index]; // Y축 숫자 포맷팅
                            }
                        }
                    },
                    y: {
                        display : true,
                        beginAtZero: true,
                        title :{
                            display: true,
                            text: '(건)',
                            fontsize: 10,
                            align: 'end',     // 끝으로 정렬
                            position: 'end'    // 축의 끝에 위치
                        },
                        ticks: {
                            autoSkip: true
                        }
                    }
                }, animation: {
                    duration: 1000,
                    easing: 'easeInOutQuad'
                },
            }
        });
    },
    chartTitleSelectYear : function () {
        const currentYear = new Date().getFullYear();
        const availableYears = [];

        // 현재 년도부터 2011년까지 내림차순으로 연도 추가
        for (let year = currentYear; year >= 2011; year--) {
            availableYears.push(year);
        }

        const yearSelect = document.getElementById("yearSelect");
        availableYears.forEach(year => {
            const option = document.createElement("option");
            option.value = year;
            option.textContent = year;
            yearSelect.appendChild(option);
        });
    },
    // compareDataGrid1 
    compareDataGrid1 : function () {
        let mydata = [
            { category: "hiddentag", period: "2011.11 ~ 2025.02", playstore: 30000000, appstore: 1934068, china: 1934068 },
            { category: "hiddentag C.O.P", period: "2011.11 ~ 2025.02", playstore: 34514, appstore: 30000000, china: 34514 },
            { category: "hiddentag Global", period: "2011.11 ~ 2025.02", playstore: 5189, appstore: 5189, china: 5189 }
        ];
        
        // 합계 계산
        let totalPlaystore = mydata.reduce((sum, row) => sum + row.playstore, 0);
        let totalAppstore = mydata.reduce((sum, row) => sum + row.appstore, 0);
        let totalChina = mydata.reduce((sum, row) => sum + row.china, 0);
        
        mydata.push(
            { category: "소계", period: "소계", playstore: totalPlaystore, appstore: totalAppstore, china: totalChina },
            { category: "전체소계", period: "전체소계", playstore: totalPlaystore, appstore: totalAppstore, china: totalChina }
        );
        
        $("#compareDataGrid1").jqGrid({
            datatype: "local",
            data: mydata,
            colNames: ["구분", "산출기간", "PlayStore", "AppStor", "중국마켓"],
            colModel: [
                { name: "category", width: 150, align: "center" },
                { name: "period", width: 200, align: "center" },
                { name: "playstore", width: 150, align: "right", formatter: "integer" },
                { name: "appstore", width: 150, align: "right", formatter: "integer" },
                { name: "china", width: 150, align: "right", formatter: "integer" }
            ],
            width: "100%",
            autowidth: true,
            height: "auto",
            // height: 374,
            rowNum: 10,
            pager: "#pager",
            viewrecords: true,
            gridview: true,
            autoencode: true,
            loadComplete: function () {
                var grid = $("#compareDataGrid1");
                var ids = grid.getDataIDs();
                
                // 소계 및 전체소계 행 병합
                $("#" + ids[3] + " td:nth-child(1)").attr("colspan", 2).addClass("merged-cell");
                $("#" + ids[3] + " td:nth-child(2)").remove();
                
                $("#" + ids[4] + " td:nth-child(1)").attr("colspan", 2).addClass("merged-cell");
                $("#" + ids[4] + " td:nth-child(2)").remove();
            }
        });
    },
    // compareDataGrid2 
    compareDataGrid2 : function() {
        let mydata = [
            { rank: 1, market: "App Store", downloads: 3210895 },
            { rank: 2, market: "Play Store", downloads: 1973771 },
            { rank: 3, market: "바이두", downloads: 1057652 },
            { rank: 4, market: "Vivo", downloads: 765214 },
            { rank: 5, market: "Oppo", downloads: 499809 },
            { rank: 6, market: "Huawei", downloads: 346448 },
            { rank: 7, market: "Xiaomi", downloads: 97166 },
            { rank: 8, market: "응용보", downloads: 74324 },
            { rank: 9, market: "360", downloads: 47052 },
            { rank: 10, market: "Wandure", downloads: 20658 },
            { rank: 11, market: "Flyme", downloads: 2347 },
            { rank: 12, market: "Lenovo", downloads: 1148 }
        ];
        
        $("#compareDataGrid2").jqGrid({
            datatype: "local",
            data: mydata,
            colNames: ["순위", "마켓명", "다운로드수"],
            colModel: [
                { name: "rank", width: 80, align: "center" },
                { name: "market", width: 250, align: "center" },
                { name: "downloads", width: 150, align: "right", formatter: "integer" }
            ],
            width: "100%",
            autowidth: true,
            height: 'auto',
            rowNum: 12,
            viewrecords: true,
            gridview: true,
            autoencode: true,
        });
    },
    // byNation 하단 차트1
    byNationChart1 : function() {
        const ctx = document.getElementById('byNationChart1').getContext('2d');

        // 고정된 색상 배열
        const colors = [
            'rgb(255, 99, 132)', // 빨강
            'rgb(54, 162, 235)', // 파랑
            'rgb(255, 206, 86)', // 노랑
            'rgb(75, 192, 192)', // 청록
            'rgb(153, 102, 255)', // 보라
            'rgb(255, 159, 64)'  // 주황
        ];

        // 전체 데이터셋 (항상 6개 유지)
        const datasets = [
            { label: '1등: 한국', data: [70000, 50000, 45000, 40000, 42000, 38000, 39000, 35000, 33000, 25000, 22000, 18000] },
            { label: '2등: 미국', data: [15000, 14000, 13500, 13000, 12000, 11000, 11500, 10800, 10500, 10000, 9500, 9000] },
            { label: '3등: 대만', data: [12000, 11800, 11500, 11000, 10000, 9700, 9500, 9000, 8700, 8600, 8500, 8200] },
            { label: '4등: 중국', data: [11000, 10900, 10700, 10500, 10300, 10200, 10100, 9800, 9500, 9200, 9000, 8800] },
            { label: '5등: 태국', data: [9500, 9400, 9300, 9100, 9000, 8700, 8600, 8500, 8300, 8200, 8000, 7900] },
            { label: '6등: 일본', data: [8000, 7800, 7700, 7600, 7500, 7400, 7300, 7200, 7100, 7000, 6900, 6800] }
        ];

        let currentPage = 1;
        const labels = ['2024-03', '2024-04', '2024-06', '2024-07', '2024-08', '2024-09', '2024-10', '2024-11', '2024-12', '2025-01', '2025-02'];

        const myChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels,
                datasets: datasets.map((set, index) => ({
                    label: set.label,
                    data: set.data,
                    borderWidth: 2,
                    fill: false,
                    borderColor: colors[index],
                    tension: 0.3,
                    backgroundColor: colors[index]
                }))
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: false
                    }
                }
            }
        });

        function updateLegend() {
            const legendContainer = document.getElementById('legendContainer');
            legendContainer.innerHTML = '';
            datasets.forEach((set, index) => {
                const legendItem = document.createElement('span');
                legendItem.textContent = set.label;
                legendItem.classList.add('legend-item');
                legendItem.style.color = colors[index];
                legendItem.onclick = () => {
                    const dataset = myChart.data.datasets[index];
                    dataset.borderWidth = dataset.borderWidth === 2 ? 5 : 2; // 강조 효과 변경
                    legendItem.classList.toggle('actives'); // 범례 강조 효과 적용
                    myChart.update();
                };
                if ((currentPage === 1 && index >= 3) || (currentPage === 2 && index < 3)) {
                    legendItem.style.display = 'none';
                }
                legendContainer.appendChild(legendItem);
            });
        }
        
        updateLegend();

        // 페이지네이션 기능 (범례만 변경)
        function changePage() {
            currentPage = currentPage === 1 ? 2 : 1;
            updateLegend();
            document.getElementById('pageIndicator').textContent = `${currentPage}/2`;
        }

        // 버튼 이벤트 리스너 추가
        document.getElementById('prevBtn').addEventListener('click', changePage);
        document.getElementById('nextBtn').addEventListener('click', changePage);
    },
    // byNation 하단 차트2

    // byCustomer 왼쪽 차트
    byCustomerChart1 : function() {
        const ctx = document.getElementById('byCustomerChart').getContext('2d');

        let myChart = new Chart(ctx, {
            type: 'line',
            data: {},
            options: {}
        });

        const data = {
            labels: ['2024-01', '2024-02', '2024-03', '2024-04', '2024-05', '2024-06', '2024-07', '2024-08', '2024-09', '2024-10', '2024-11', '2024-12'],
            datasets: [
                {
                    label: '(주) 난다',
                    data: [12000, 15000, 32000, 14000, 13000, 12500, 11000, 16000, 14000, 17000, 15000, 14500],
                    borderColor: 'rgb(135, 206, 250)',
                    backgroundColor: 'rgba(135, 206, 250, 0.5)',
                    borderWidth: 2,
                    fill: false,
                },
                {
                    label: '에프앤에프',
                    data: [10000, 12000, 18000, 13000, 11000, 11500, 10000, 14000, 12000, 13500, 14000, 15000],
                    borderColor: 'rgb(255, 105, 180)',
                    backgroundColor: 'rgba(255, 105, 180, 0.5)',
                    borderWidth: 2,
                    fill: false,
                },
                {
                    label: '(주)아이패밀리에스씨',
                    data: [5000, 6000, 7000, 6500, 6200, 6100, 5900, 5800, 5700, 5600, 5500, 5400],
                    borderColor: 'rgb(255, 218, 121)',
                    backgroundColor: 'rgba(255, 218, 121, 0.5)',
                    borderWidth: 2,
                    fill: false,
                },
                {
                    label: 'Grape Systems_MEDICOM-TOY',
                    data: [4500, 4800, 5000, 4900, 4800, 4700, 4600, 4500, 4400, 4300, 4200, 4100],
                    borderColor: 'rgb(255, 165, 0)',
                    backgroundColor: 'rgba(255, 165, 0, 0.5)',
                    borderWidth: 2,
                    fill: false,
                },
                {
                    label: '루첼라',
                    data: [4000, 4200, 4300, 4100, 4000, 3900, 3800, 3700, 3600, 3500, 3400, 3300],
                    borderColor: 'rgb(176, 224, 230)',
                    backgroundColor: 'rgba(176, 224, 230, 0.5)',
                    borderWidth: 2,
                    fill: false,
                }
            ]
        };

        const options = {
            responsive: true,
            maintainAspectRatio: false,
            layout: {
                padding: {
                    top: 0, 
                    bottom: 10,
                    left: 10,
                    right: 10
                }
            },
            plugins: {
                legend: {
                    display: true,
                    position: 'top',
                    align: 'center',
                    fullSize: false,
                    labels: {
                        font: {
                            size: 11
                        },
                        padding: 10,
                        usePointStyle: true,
                        boxWidth: 14
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: false,
                    ticks: {
                        callback: function(value) {
                            return value.toLocaleString();
                        }
                    }
                }
            }
        };        
        
        // 데이터를 설정하고 업데이트
        myChart.data = data;
        myChart.options = options;
        myChart.update();
    },

    // byCustomre 그리드
    byCustomerGrid : function() {
        let months = ["01월", "02월", "03월", "04월", "05월", "06월", "07월", "08월", "09월", "10월", "11월", "12월"];
        let data = {
            "01월": [
                { customer: "(주) 난다", scans: 24828 },
                { customer: "에프앤에프", scans: 23863 },
                { customer: "(주)아이퍼밀리어스씨", scans: 7778 },
                { customer: "Grape Systems_MEDICOM-TOY", scans: 2851 },
                { customer: "루첼라", scans: 2193 }
            ],
            "02월": [
                { customer: "(주) 난다", scans: 14693 },
                { customer: "에프앤에프", scans: 14550 },
                { customer: "(주)아이퍼밀리어스씨", scans: 5395 },
                { customer: "Grape Systems_MEDICOM-TOY", scans: 2322 },
                { customer: "루첼라", scans: 582 }
            ],
            "03월": [
                { customer: "(주) 난다", scans: 14693 },
                { customer: "에프앤에프", scans: 14550 },
                { customer: "(주)아이퍼밀리어스씨", scans: 5395 },
                { customer: "Grape Systems_MEDICOM-TOY", scans: 2322 },
                { customer: "루첼라", scans: 582 }
            ],
            "04월": [
                { customer: "(주) 난다", scans: 14693 },
                { customer: "에프앤에프", scans: 14550 },
                { customer: "(주)아이퍼밀리어스씨", scans: 5395 },
                { customer: "Grape Systems_MEDICOM-TOY", scans: 2322 },
                { customer: "루첼라", scans: 582 }
            ],
            "05월": [
                { customer: "(주) 난다", scans: 14693 },
                { customer: "에프앤에프", scans: 14550 },
                { customer: "(주)아이퍼밀리어스씨", scans: 5395 },
                { customer: "Grape Systems_MEDICOM-TOY", scans: 2322 },
                { customer: "루첼라", scans: 582 }
            ],
            "06월": [
                { customer: "(주) 난다", scans: 14693 },
                { customer: "에프앤에프", scans: 14550 },
                { customer: "(주)아이퍼밀리어스씨", scans: 5395 },
                { customer: "Grape Systems_MEDICOM-TOY", scans: 2322 },
                { customer: "루첼라", scans: 582 }
            ],
            "07월": [
                { customer: "(주) 난다", scans: 14693 },
                { customer: "에프앤에프", scans: 14550 },
                { customer: "(주)아이퍼밀리어스씨", scans: 5395 },
                { customer: "Grape Systems_MEDICOM-TOY", scans: 2322 },
                { customer: "루첼라", scans: 582 }
            ],
            "08월": [
                { customer: "(주) 난다", scans: 14693 },
                { customer: "에프앤에프", scans: 14550 },
                { customer: "(주)아이퍼밀리어스씨", scans: 5395 },
                { customer: "Grape Systems_MEDICOM-TOY", scans: 2322 },
                { customer: "루첼라", scans: 582 }
            ],
            "09월": [
                { customer: "(주) 난다", scans: 14693 },
                { customer: "에프앤에프", scans: 14550 },
                { customer: "(주)아이퍼밀리어스씨", scans: 5395 },
                { customer: "Grape Systems_MEDICOM-TOY", scans: 2322 },
                { customer: "루첼라", scans: 582 }
            ],
            "10월": [
                { customer: "(주) 난다", scans: 14693 },
                { customer: "에프앤에프", scans: 14550 },
                { customer: "(주)아이퍼밀리어스씨", scans: 5395 },
                { customer: "Grape Systems_MEDICOM-TOY", scans: 2322 },
                { customer: "루첼라", scans: 582 }
            ],
            "11월": [
                { customer: "(주) 난다", scans: 14693 },
                { customer: "에프앤에프", scans: 14550 },
                { customer: "(주)아이퍼밀리어스씨", scans: 5395 },
                { customer: "Grape Systems_MEDICOM-TOY", scans: 2322 },
                { customer: "루첼라", scans: 582 }
            ],
            "12월": [
                { customer: "(주) 난다", scans: 14693 },
                { customer: "에프앤에프", scans: 14550 },
                { customer: "(주)아이퍼밀리어스씨", scans: 5395 },
                { customer: "Grape Systems_MEDICOM-TOY", scans: 2322 },
                { customer: "루첼라", scans: 582 }
            ]
            // 나머지 월 데이터도 같은 형식으로 추가하세요.
        };

        months.forEach(function (month) {
            let tableId = "grid-" + month;
            let pagerId = "pager-" + month;
            $("#byCustomer-grids").append('<div class="grid-container"><div class="grid-title" style="font-size:1.4rem; font-weight: bold; margin-bottom: 1rem;">' + month + '</div><table id="' + tableId + '"></table><div id="' + pagerId + '"></div></div>');

            $("#" + tableId).jqGrid({
                datatype: "local",
                colNames: ["고객사", "스캔 수"],
                colModel: [
                    { name: "customer", index: "customer", width: "60%", align: "left" },
                    { name: "scans", index: "scans", width: "40%", align: "right" }
                ],
                height: "auto",
                width: "100%",
                autowidth: true,
                rowNum: 2,
                viewrecords: true,
            });

            if (data[month]) {
                data[month].forEach(function (row, i) {
                    $("#" + tableId).jqGrid("addRowData", i + 1, row);
                });
            }
        });
    },
    byCustomerTabClick : function() {
        $('.toggle-btn').on('click', function() {
            $('.toggle-btn').removeClass('active');
            $('.byCustomer').removeClass('actives');
            $(this).addClass('active');

            let idx = $(this).index();
            $(".byCustomer").eq(idx).addClass("actives");
        })
    },
    modifyClick: function() {
        $('.etc-wrap .modify').on('click', function() {
            let comment = $('.etc-wrap .txt');
            $('.etc-wrap').addClass('actives');
            comment.attr("contenteditable", "true").focus(); // contenteditable 추가 및 포커스
    
            // 커서를 텍스트 끝으로 이동
            let range = document.createRange();
            let selection = window.getSelection();
            range.selectNodeContents(comment[0]); // p 태그 전체 선택
            range.collapse(false); // 커서를 마지막으로 이동
            selection.removeAllRanges();
            selection.addRange(range);
        });
    },
    saveClick : function() {
        $('.etc-wrap .save').on('click', function(e) {
            let comment = $('.etc-wrap .txt');
            $('.etc-wrap').removeClass('actives');
            comment.attr("contenteditable", "false");
            $('.etc-wrap .complete').addClass('actives');
            // 0.5초 후 제거
            setTimeout(function() {
                $('.etc-wrap .complete').removeClass('actives');
            }, 500);
        })
    },
}