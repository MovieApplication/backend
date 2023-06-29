package com.movie.movieapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("visitor")
public class Visitor {

    private static final long serialVersionUID = -1L;
    @Id
    private String _id;
    private String userIp;
    private String userAgent;
    private LocalDate date;
}
