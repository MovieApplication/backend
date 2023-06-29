package com.movie.movieapi.controller;

import com.movie.movieapi.dto.DailyVisitorResponseDto;
import com.movie.movieapi.dto.TotalVisitorResponseDto;
import com.movie.movieapi.service.VisitorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/api/v1/visitor")
@RequiredArgsConstructor
public class VisitorController {


    private final VisitorService visitorService;

    @Transactional(readOnly = true)
    @Operation(summary = "일일 방문자 수", description = "일일 방문자 수를 가져옵니다.")
    @GetMapping("/daily")
    public ResponseEntity<DailyVisitorResponseDto> dailyVisitor() {
        return ResponseEntity.ok(visitorService.getDailyVisitorCount());
    }

    @Transactional(readOnly = true)
    @Operation(summary = "총 방문자 수", description = "일일 총 수를 가져옵니다.")
    @GetMapping("/total")
    public ResponseEntity<TotalVisitorResponseDto> totalVisitor() {
        return ResponseEntity.ok(visitorService.getTotalVisitorCount());
    }


}
