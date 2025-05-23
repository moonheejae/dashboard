package com.cknb.htPlatform.dto;

import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BaseResponseDto<T> {
    @Description("상태 코드")
    private Integer statusCode;
    @Description("상태")
    private String status;
    @Description("응답 데이터")
    private T data;
}
