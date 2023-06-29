package com.movie.movieapi.scheduler;

import com.movie.movieapi.domain.Visitor;
import com.movie.movieapi.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class VisitorScheduler {

    private final RedisTemplate<String, String> redisTemplate;

    private final VisitorRepository visitorRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateVisitorData() {
        log.info("자정임으로 스케줄러를 동작합니다.");
        Set<String> keys = redisTemplate.keys("*_*");

        for (String key : keys) {
            String[] parts = key.split("_");
            String userIp = parts[0];
            LocalDate date = LocalDate.parse(parts[1]);

            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String userAgent = valueOperations.get(key);

            if(!visitorRepository.existsByUserIpAndDate(userIp, date)){
                Visitor visitor = Visitor.builder()
                        .userAgent(userAgent)
                        .userIp(userIp)
                        .date(date)
                        .build();

                visitorRepository.save(visitor);
            }
            redisTemplate.delete(key);
        }
    }
}
