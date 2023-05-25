package com.movie.movieapi.filter;

import com.movie.movieapi.exception.RestApiException;
import com.movie.movieapi.result.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        } catch (RestApiException ex){
            setErrorResponse(HttpStatus.UNAUTHORIZED,response,ex);
        }
    }
    public void setErrorResponse(HttpStatus status, HttpServletResponse res, RestApiException ex) throws IOException {

        res.setStatus(status.value());
        res.setContentType("application/json; charset=UTF-8");

        ErrorResponse apiResponseMessage = new ErrorResponse();
        apiResponseMessage.setStatus("FAIL");
        apiResponseMessage.setMessage("실패");
        apiResponseMessage.setErrorCode(ex.getErrorCode());
        apiResponseMessage.setErrorMessage(ex.getMessage());
        JSONObject responseJson = new JSONObject(apiResponseMessage);
        res.getWriter().print(responseJson);
    }
}
