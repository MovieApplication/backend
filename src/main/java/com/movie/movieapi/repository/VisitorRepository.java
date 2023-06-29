package com.movie.movieapi.repository;

import com.movie.movieapi.domain.Visitor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface VisitorRepository extends MongoRepository<Visitor,String> {

    boolean existsByUserIpAndDate(String userIp, LocalDate date);
}
