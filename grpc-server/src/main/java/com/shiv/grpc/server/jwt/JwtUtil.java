package com.shiv.grpc.server.jwt;

import com.shiv.grpc.server.global.GenericException;
import com.shiv.grpc.server.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.shiv.grpc.server.constants.Constants.ACCESS_TOKEN;
import static com.shiv.grpc.server.constants.Constants.JWT_ISSUER;
import static com.shiv.grpc.server.constants.Constants.NAME;
import static com.shiv.grpc.server.constants.Constants.REFRESH_TOKEN;
import static com.shiv.grpc.server.constants.Constants.TOKEN_TYPE;
import static com.shiv.grpc.server.constants.Constants.USER_ID;

@Service
@Log4j2
public class JwtUtil {

    private final UserService userService;

    public JwtUtil(UserService userService) {
        this.userService = userService;
    }

    @Value(value = "${chat.service.jwt-secret}")
    private String secretKey;

    private String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        try{
            return extractExpiration(token).before(new Date());
        }catch (ExpiredJwtException expiredJwtException){
            return true;
        }
    }

    private String createToken(Map<String, Object> claims, String subject, long time) {
        return Jwts.builder()
                .setId((String) claims.get(USER_ID))
                .setIssuer(JWT_ISSUER)
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Access token expiry 8 hr
     *
     * @param usernameOrEmail
     * @return
     * @throws GenericException
     */
    public String generateAccessToken(String usernameOrEmail) throws GenericException {
        var user = userService.getUser(usernameOrEmail);
        final Map<String, Object> claims = new HashMap<>();
        claims.put(TOKEN_TYPE, ACCESS_TOKEN);
        claims.put(USER_ID, user.getUuid());
        claims.put(NAME, user.getName());
        return createToken(claims, user.getUsername(), 8 * 60 * 60 * 1000);
    }

    /**
     * Refresh token expiry 8 days
     *
     * @param usernameOrEmail
     * @return
     * @throws GenericException
     */
    public String generateRefreshToken(String usernameOrEmail) throws GenericException {
        final var user = userService.getUser(usernameOrEmail);
        final Map<String, Object> claims = new HashMap<>();
        claims.put(TOKEN_TYPE, REFRESH_TOKEN);
        claims.put(USER_ID, user.getUuid());
        claims.put(NAME, user.getName());
        return createToken(claims, user.getUsername(), 8 * 24 * 60 * 60 * 1000);
    }

    public String getUsernameFromToken(String token) {
        if (Boolean.TRUE.equals(isTokenExpired(token))) {
            throw new GenericException(HttpStatus.FORBIDDEN.value(), "Token expired");
        }
        return extractUsername(token);
    }

}
