package com.movie.movieapi.controller;

import com.movie.movieapi.dto.UserInsertRequestDto;
import com.movie.movieapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController{

    private final UserService userService;

    @Operation(summary = "유저 등록", description = "유저를 등록합니다.")
    @PostMapping("/info")
    public ResponseEntity<?> insertUserInfo(@RequestBody UserInsertRequestDto userInsertRequestDto){
        userService.insertUserInfo(userInsertRequestDto);
        return ResponseEntity.ok().build();
    }
}
