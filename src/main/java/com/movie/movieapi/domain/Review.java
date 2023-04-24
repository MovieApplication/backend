package com.movie.movieapi.domain;

import com.movie.movieapi.dto.ReviewInsertRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document("review")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review implements Serializable {
    private User user;
    private String movieId;
    private String content;

    public Review(ReviewInsertRequestDto reviewInsertRequestDto,User user){
        this.user = user;
        this.movieId = reviewInsertRequestDto.getMovieId();
        this.content = reviewInsertRequestDto.getContent();
    }
}
