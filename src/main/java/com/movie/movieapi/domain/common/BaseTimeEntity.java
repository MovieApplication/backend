package com.movie.movieapi.domain.common;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import java.util.Date;

@Data
public class BaseTimeEntity {
    @CreatedDate
    private Date regDatetime;
    @LastModifiedDate
    private Date modDatetime;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String modBy;


}
