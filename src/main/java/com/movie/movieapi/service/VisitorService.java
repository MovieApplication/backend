package com.movie.movieapi.service;

import com.movie.movieapi.dto.DailyVisitorResponseDto;
import com.movie.movieapi.dto.TotalVisitorResponseDto;
import com.movie.movieapi.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class VisitorService {

    private final RedisTemplate<String, String> redisTemplate;
    private final VisitorRepository visitorRepository;

    public DailyVisitorResponseDto getDailyVisitorCount() {
        DailyVisitorResponseDto dailyVisitorResponseDto = new DailyVisitorResponseDto();
        Set<String> keys = redisTemplate.keys("*_*");
        //일일 사용자 스텍
        if(keys != null){
            dailyVisitorResponseDto.setCount(keys.size());
            return dailyVisitorResponseDto;
        }
        //일일 사용자 초기화.
        dailyVisitorResponseDto.setCount(0);
        return dailyVisitorResponseDto;
    }

    public TotalVisitorResponseDto getTotalVisitorCount() {
        TotalVisitorResponseDto totalVisitorResponseDto = new TotalVisitorResponseDto();
        totalVisitorResponseDto.setCount(visitorRepository.count());
        return totalVisitorResponseDto;
    }
}

