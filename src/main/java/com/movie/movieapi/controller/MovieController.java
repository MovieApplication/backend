package com.movie.movieapi.controller;

import com.movie.movieapi.dto.MovieDetailResponseDto;
import com.movie.movieapi.dto.MovieResponseDto;
import com.movie.movieapi.dto.SearchDto;
import com.movie.movieapi.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/movie")
@RestController
public class MovieController {

    private final MovieService movieService;

    @Operation(summary = "현재 극장에 있는 영화 목록 조회",description = "현재 극장에 있는 영화 목록 조회를 합니다.")
    @GetMapping("")
    public ResponseEntity<MovieResponseDto> selectMoviesInTheater(@RequestParam(value = "page",required = false)Integer page){
        return ResponseEntity.ok(movieService.selectMoviesInTheater(page));
    }

    @Operation(summary = "실시간 인기 순위 영화 리스트 목록 조회", description = "실시간 인기 순위 영화 리스트 목록 조회를 합니다.")
    @GetMapping("/popular")
    public ResponseEntity<MovieResponseDto> selectPopularMovies(@RequestParam(value = "page",required = false)Integer page){
        return ResponseEntity.ok(movieService.selectPopularMovies(page));
    }
    @Operation(summary = "평점 높은순으로 영화 목록 조회", description = "평점 높은순으로 영화 목록 조회를 합니다.")
    @GetMapping("/vote-average")
    public ResponseEntity<MovieResponseDto> selectVoteAverageMovies(@RequestParam(value = "page",required = false)Integer page){
        return ResponseEntity.ok(movieService.selectVoteAverageMovies(page));
    }
    @Operation(summary = "최신에 개봉한순으로 목록 조회(현재날짜기준)", description = "최신에 개봉한순으로 목록 조회(현재날짜기준)를 합니다.")
    @GetMapping("/release-date")
    public ResponseEntity<MovieResponseDto> selectReleaseDateMovies(@RequestParam(value = "page",required = false)Integer page){
        return ResponseEntity.ok(movieService.selectReleaseDateMovies(page));
    }
    @Operation(summary = "년도별 영화 목록 조회", description = "년도별 영화 목록 조회를 합니다.")
    @GetMapping("/year")
    public ResponseEntity<MovieResponseDto> selectYearMovies(@ModelAttribute SearchDto searchDto){
        return ResponseEntity.ok(movieService.selectYearMovies(searchDto));
    }
    @Operation(summary = "영화 세부 정보 조회", description = "영화 세부 정보 조회를 합니다.")
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDetailResponseDto> selectMovie(@PathVariable("movieId")Long movieId){
        return ResponseEntity.ok(movieService.selectMovie(movieId));
    }
    @Operation(summary = "해당 영화와 유사한 영화 목록조회", description = "해당 영화와 유사한 영화 목록조회를 합니다.")
    @GetMapping("/similar/{movieId}")
    public ResponseEntity<MovieResponseDto> selectSimilarMovie(@PathVariable("movieId")Long movieId){
        return ResponseEntity.ok(movieService.selectSimilarMovie(movieId));
    }
}
