package com.movie.movieapi.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{


    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    TOKEN_EXPIRE(HttpStatus.FORBIDDEN,"로그인 시간이 유효하지 않습니다."),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.FORBIDDEN,"리프레시 토큰 정보가 존재하지않습니다."),
    NOT_DUPLICATION_REVIEW(HttpStatus.FORBIDDEN,"중복된 리뷰는 작성할 수 없습니다."),
    NOT_FOUND_REVIEW(HttpStatus.FORBIDDEN,"리뷰 정보가 없습니다."),
    NOT_MINE_REVIEW(HttpStatus.FORBIDDEN,"본인이 작성한 리뷰가 아닙니다."),

    // token
    INVALID_TOKEN(HttpStatus.FORBIDDEN, "유효하지 않은 사용자 정보입니다."),
    EXPIRED_TOKEN(HttpStatus.FORBIDDEN, "로그인 유효 시간이 만료 되었습니다."),
    EXPIRED_TOKEN_SNS(HttpStatus.FORBIDDEN, "너무 오랜 시간이 지나 유효 시간이 만료 되었습니다. 다시 시도하여 주세요."),
    AUTHENTICATION_FAILED(HttpStatus.FORBIDDEN, "인증에 실패하였습니다."),
    WRONG_TOKEN(HttpStatus.FORBIDDEN, "잘못된 사용자 정보입니다."),
    EMPTY_REFRESH_TOKEN(HttpStatus.FORBIDDEN, "로그인 정보가 존재하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

