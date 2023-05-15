package com.movie.movieapi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.movie.movieapi.domain.common.BaseTimeEntity;
import com.movie.movieapi.dto.ReviewInsertRequestDto;
import com.movie.movieapi.dto.ReviewUpdateRequestDto;
import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;


@Getter
@Document("review")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseTimeEntity implements Serializable {
    private static final long serialVersionUID = -1L;

    @Id
    private String _id;
    private User user;
    private Long movieId;
    private String content;
    private boolean delYn;


    public Review(ReviewInsertRequestDto reviewInsertRequestDto,User user){
        this.user = user;
        this.movieId = reviewInsertRequestDto.getMovieId();
        this.content = reviewInsertRequestDto.getContent();
    }

    public void updateReview(ReviewUpdateRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

    public void deleteReview() {
        this.delYn = true;
    }
}
