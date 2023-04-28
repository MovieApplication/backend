package com.movie.movieapi.domain;

import com.movie.movieapi.dto.UserInsertRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("user")
public class User implements Serializable {

    private static final long serialVersionUID = -1L;
    @Id
    private String _id;
    private String userId;

    public User(UserInsertRequestDto userInsertRequestDto){
        this.userId = userInsertRequestDto.getUserId();
    }
}
