package com.movie.movieapi.controller;

import com.movie.movieapi.dto.MovieDetailResponseDto;
import com.movie.movieapi.dto.MovieResponseDto;
import com.movie.movieapi.dto.SearchDto;
import com.movie.movieapi.service.MovieService;
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

    /**
     * 실시간 인기 순위 영화 리스트 목록 조회
     */
    @GetMapping("/popular")
    public ResponseEntity<MovieResponseDto> selectPopularMovies(){
        return ResponseEntity.ok(movieService.selectPopularMovies());
    }

    /**
     * 평점 높은순으로 영화 목록 조회
     */
    @GetMapping("/vote-average")
    public ResponseEntity<MovieResponseDto> selectVoteAverageMovies(){
        return ResponseEntity.ok(movieService.selectVoteAverageMovies());
    }
    /**
     * 최신에 개봉한순으로 목록 조회(현재날짜기준)
     */
    @GetMapping("/release-date")
    public ResponseEntity<MovieResponseDto> selectReleaseDateMovies(){
        return ResponseEntity.ok(movieService.selectReleaseDateMovies());
    }

    /**
     * 년도별 영화 목록 조회
     * @param searchDto
     * @return
     */
    @GetMapping("/year")
    public ResponseEntity<MovieResponseDto> selectYearMovies(@ModelAttribute SearchDto searchDto){
        return ResponseEntity.ok(movieService.selectYearMovies(searchDto));
    }

    /**
     * 영화 세부 정보 조회
     */
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDetailResponseDto> selectMovie(@PathVariable("movieId")Long movieId){
        return ResponseEntity.ok(movieService.selectMovie(movieId));
    }
    /**
     * 해당 영화와 유사한 영화 목록조회
     */
    @GetMapping("/{movieId}/similar")
    public ResponseEntity<MovieResponseDto> selectSimilarMovie(@PathVariable("movieId")Long movieId){
        return ResponseEntity.ok(movieService.selectSimilarMovie(movieId));
    }
}
