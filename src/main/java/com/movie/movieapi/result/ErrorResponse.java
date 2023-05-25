package com.movie.movieapi.result;

import com.movie.movieapi.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    @Schema(description = "성공 여부", example = "SUCCESS")
    private String status;
    @Schema(description = "처리 메시지", example = "정상처리")
    private String message;
    @Schema(description = "에러 메시지", example = "null")
    private String errorMessage;
    @Schema(description = "에러 코드", example = "null")
    private ErrorCode errorCode;

    public ErrorResponse(String status, String message, ErrorCode errorCode, String errorMessage){
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
