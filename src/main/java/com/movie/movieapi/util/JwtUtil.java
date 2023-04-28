package com.movie.movieapi.util;

import com.movie.movieapi.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtUtil {

    @Value("${spring.jwt.secret-access-token}")
    private String SECRET_KEY;

    @Value("${spring.jwt.secret-refresh-token}")
    private String REFRESH_SECRET_KEY;

    private final RedisTemplate redisTemplate;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRefreshTokenUsername(String token) {
        return extractRefreshTokenClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Date extractRefreshTokenExpiration(String token) {
        return extractRefreshTokenClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public <T> T extractRefreshTokenClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractRefreshTokenAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Claims extractRefreshTokenAllClaims(String token) {
        return Jwts.parser().setSigningKey(REFRESH_SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Boolean isRefreshTokenExpired(String token) {
        return extractRefreshTokenExpiration(token).before(new Date());
    }


    public String generateToken(String loginEmail) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, loginEmail);
    }

    public Long getCurrent_id(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        System.out.println("Token2:::"+request.getHeader("Authorization"));
        return Long.parseLong(extractAllClaims(request.getHeader("Authorization").replace("Bearer ","")).get("_id").toString());
    }

    public String generateRefreshToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        return createRefreshToken(claims, userId);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))  //5분
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    private String createRefreshToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  //10시간
                .signWith(SignatureAlgorithm.HS256, REFRESH_SECRET_KEY).compact();
    }


    public Boolean validateToken(String token, String loginEmail) {
        final String username = extractUsername(token);
        return (username.equals(loginEmail) && !isTokenExpired(token));
    }

    public Boolean validateRefreshToken(String token, String loginEmail) {
        final String username = extractRefreshTokenUsername(token);
        return (username.equals(loginEmail) && !isRefreshTokenExpired(token));
    }
    public boolean validateRedisToken(String token) {
        try {
            ValueOperations<String, String> logoutValueOperations = redisTemplate.opsForValue();
            if (logoutValueOperations.get("accessToken") != null) {
                log.info("로그아웃된 토큰 입니다.");
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        log.info("로그아웃 실패 토큰 입니다.");
        return true;
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        /*HashMap<String, String> extraClaims = new HashMap<>();

        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userParam.getLogin(), null, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        extraClaims.put(USER_ID_KEY, userParam.getId().toString());
        extraClaims.put(MALL_ID_KEY, userParam.getMall().getId().toString());
        String accessToken = createToken(authenticationToken, false, extraClaims);
        String refreshToken = createRefreshToken(authenticationToken, extraClaims);*/

        claims.put("_id", user.get_id());
        return createToken(claims, user.getUserId());
    }

}
