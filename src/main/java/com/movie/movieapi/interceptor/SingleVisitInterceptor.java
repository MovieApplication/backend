package com.movie.movieapi.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@Component
@Slf4j
@RequiredArgsConstructor
public class SingleVisitInterceptor implements HandlerInterceptor {


    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String userIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String today = LocalDate.now().toString();
        String key = userIp + "_" + today;

        ValueOperations valueOperations = redisTemplate.opsForValue();

        if (!valueOperations.getOperations().hasKey(key)) {
            valueOperations.set(key, userAgent);
        }

        return true;
    }

}
