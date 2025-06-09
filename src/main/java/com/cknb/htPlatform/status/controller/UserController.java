package com.cknb.htPlatform.status.controller;

import com.cknb.htPlatform.status.dto.vo.NewUserInterface;
import com.cknb.htPlatform.status.dto.vo.ReVisitInterface;
import com.cknb.htPlatform.status.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "UserController", description = "유저 관련 API")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("api/user")
public class UserController {

    private final UserInfoService userInfoService;

    @GetMapping("total")
    @Operation(summary = "누적 가입자 수", description = "누적 가입자 수 정보 입니다.")
    public ResponseEntity<Long> getUserTotal(){
        return ResponseEntity.ok(userInfoService.getUserTotal());
    }

    @GetMapping("countAndJoinPath")
    @Operation(summary = "기간별 신규 가입자 수 및 가입 경로 수", description = "기간별 앱 신규 가입자 수 및 가입 경로별 수 정보 입니다.")
    public ResponseEntity<NewUserInterface> getNewUserCountAndJoinPath(
            @RequestParam(value = "selected_days")  Integer selectedDays
    ){
        return ResponseEntity.ok(userInfoService.getNewUserCountAndJoinPath(selectedDays));
    }

    @GetMapping("revisit")
    @Operation(summary = "기간별 재방문 유저 수", description = "기간별 재방문 유저 수 정보 입니다.")
    public ResponseEntity<ReVisitInterface> getReVisitUser(
            @RequestParam(value = "selected_days")  Integer selectedDays
    ){
        return ResponseEntity.ok(userInfoService.getReVisitUser(selectedDays));
    }
}
