package com.movie.movieapi.domain.common;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime regDatetime;
    @LastModifiedDate
    private LocalDateTime modDatetime;
    @CreatedBy
    private String createBy;
    @LastModifiedBy
    private String modBy;



}
