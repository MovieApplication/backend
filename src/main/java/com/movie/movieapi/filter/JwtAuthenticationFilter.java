package com.movie.movieapi.filter;

import com.movie.movieapi.domain.User;
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
import org.webjars.NotFoundException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        String userId= null;
        String jwt = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            jwt = authorizationHeader.substring(7);
            try {
                userId = jwtUtil.extractUsername(jwt);
            }catch(IllegalArgumentException e){
                logger.error("error occured during getting username from token!", e);
                throw new NotFoundException("error occured during getting username from token!");
            }catch(ExpiredJwtException e){
                logger.warn("the token is expired and not valid anymore!", e);
                throw new NotFoundException("the token is expired and not valid anymore!");
            }catch(SignatureException e){
                logger.error("Authentication Failed. Username or Password not valid.");
                throw new NotFoundException("Authentication Failed. Username or Password not valid.");
            }catch(MalformedJwtException e){
                logger.error("the token is not valid!", e);
                throw new NotFoundException("the token is not valid!");
            }
        }else{
            logger.warn("couldn't find bearer string, will ignore the header");
        }


        if(userId != null){
            User user = userRepository.findByUserId(userId).get();
            if (jwtUtil.validateToken(jwt,userId)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null,null);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
        chain.doFilter(request,response);
    }
}
