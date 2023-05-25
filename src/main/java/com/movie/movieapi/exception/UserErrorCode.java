package com.movie.movieapi.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    INACTIVE_USER(HttpStatus.FORBIDDEN, "없는 유저입니다."),
    EXIST_USER(HttpStatus.FORBIDDEN, "이미 있는 유저입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
