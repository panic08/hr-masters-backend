package ru.panic.mainservice.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.panic.mainservice.security.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${security.secret}")
    private String secret;

    public String generateAccessToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();

        return createAccessToken(claims, userDetails.getUsername());
    }

    public String extractIdFromToken(String token){
        return extractClaim(token, Claims::getId);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(token.getBytes()).build().parseClaimsJws(token).getBody();
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }


    private String createAccessToken(Map<String, Object> claims, String subject){
        Date dateNow = new Date();
        Date expirationDate = new Date(dateNow.getTime() + 1000L * 60 * 60 * 24 * 30);

        byte[] signingKeyBytes = secret.getBytes();

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setSubject(String.valueOf(subject))
                .setIssuedAt(dateNow)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(signingKeyBytes), SignatureAlgorithm.HS256)
                .compact();
    }
}
