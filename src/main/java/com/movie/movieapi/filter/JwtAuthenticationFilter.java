package com.movie.movieapi.filter;

import com.movie.movieapi.domain.User;
import com.movie.movieapi.exception.CommonErrorCode;
import com.movie.movieapi.exception.RestApiException;
import com.movie.movieapi.repository.UserRepository;
import com.movie.movieapi.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String kakaoId = null;
        String jwt = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            jwt = authorizationHeader.substring(7);
            try {
                kakaoId = jwtUtil.extractUsername(jwt);
            }catch(IllegalArgumentException e){
                logger.error("error occured during getting username from token!", e);
                throw new RestApiException(CommonErrorCode.INVALID_TOKEN, CommonErrorCode.INVALID_TOKEN.getMessage());
            }catch(ExpiredJwtException e){
                logger.warn("the token is expired and not valid anymore!", e);
                throw new RestApiException(CommonErrorCode.EXPIRED_TOKEN, CommonErrorCode.EXPIRED_TOKEN.getMessage());
            }catch(SignatureException e){
                logger.error("Authentication Failed. Username or Password not valid.");
                throw new RestApiException(CommonErrorCode.AUTHENTICATION_FAILED, CommonErrorCode.AUTHENTICATION_FAILED.getMessage());
            }catch(MalformedJwtException e){
                logger.error("the token is not valid!", e);
                throw new RestApiException(CommonErrorCode.WRONG_TOKEN, CommonErrorCode.WRONG_TOKEN.getMessage());
            }
        }else{
            logger.warn("couldn't find bearer string, will ignore the header");
        }


        if(kakaoId != null){
            User user = userRepository.findByKakaoId(kakaoId).get();
            if (jwtUtil.validateToken(jwt,kakaoId)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null,null);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
        chain.doFilter(request,response);
    }
}
