package com.cknb.htPlatform.auth.controller;

import com.cknb.htPlatform.auth.dto.DataReportUserDto;
import com.cknb.htPlatform.auth.service.UserService;
import com.cknb.htPlatform.dto.BaseResponseDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/check")
    public ResponseEntity<BaseResponseDto<Boolean>> checkAuth(
            @RequestBody @Valid DataReportUserDto dto
    ) throws Exception {
        try{
            if (dto.getToken() != null) {
                return ResponseEntity.ok(
                        BaseResponseDto.<Boolean>builder()
                                .data(userService.validateToken(dto.getUserNo(), dto.getToken()))
                                .build());
            }
        }catch (Exception e){
            throw new Exception();
        }
        return ResponseEntity.ok(
                BaseResponseDto.<Boolean>builder()
                        .data(false)
                        .build());
    }

    // 로그인 체크
    @GetMapping("/loginCheck")
    public String getUserNo(HttpSession session) {
        String userNo = (String) session.getAttribute("userNo"); // 세션에서 userNo 가져오기
        if(userNo!=null) {
            return userNo; // 원하는 페이지로 리디렉트
        }else {
            return "redirect:http://hiddentagiqr.com:8080/"; // 원하는 페이지로 리디렉트
        }
    }
}