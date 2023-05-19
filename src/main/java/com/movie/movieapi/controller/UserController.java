package com.movie.movieapi.controller;

import com.movie.movieapi.domain.User;
import com.movie.movieapi.dto.TokenRequestDto;
import com.movie.movieapi.dto.UserInsertRequestDto;
import com.movie.movieapi.dto.UserLoginResponseDto;
import com.movie.movieapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController{

    private final UserService userService;


    @Operation(summary = "로그인 (토큰 획득)", description = "로그인을 하여 토큰을 획득합니다.")
    @PostMapping("/login/{kakaoId}")
    public ResponseEntity<?> login(@PathVariable("kakaoId")String kakaoId){
        return ResponseEntity.ok(userService.login(kakaoId));
    }
    @Operation(summary = "토큰 재발급", description = "토큰 유효시간이 지날경우 토큰을 재발급 합니다.")
    @PostMapping("/refresh-token")
    public ResponseEntity<UserLoginResponseDto> refreshToken(@RequestBody TokenRequestDto tokenRequestDto,
                                                             @AuthenticationPrincipal User user){
        return ResponseEntity.ok( userService.refreshToken(tokenRequestDto,user));
    }

    @Operation(summary = "유저 등록", description = "유저를 등록합니다.")
    @PostMapping("/info")
    public ResponseEntity<UserInsertRequestDto> insertUserInfo(@RequestBody UserInsertRequestDto userInsertRequestDto){
        userService.insertUserInfo(userInsertRequestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "유저 정보 있는지 체크", description = "유저 정보가 있는지 체크합니다.")
    @GetMapping("/info/{kakaoId}")
    public ResponseEntity<?> userInfoCheck(@PathVariable("kakaoId")String kakaoId){

        return ResponseEntity.ok(userService.userInfoCheck(kakaoId));
    }

}
