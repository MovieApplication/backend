package com.movie.movieapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document("refreshToken")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken implements Serializable {
    @Id
    private String _id;
    private String refreshToken;
    private User user;

    public void setRefreshToken(String refreshToken,User user){
        this.refreshToken = refreshToken;
        this.user = user;
    }

}
