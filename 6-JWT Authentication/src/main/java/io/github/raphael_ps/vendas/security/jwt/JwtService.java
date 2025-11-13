package io.github.raphael_ps.vendas.security.jwt;

import io.github.raphael_ps.vendas.domain.entity.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    private final Date expiration;
    private final SecretKey secretKey;

    public JwtService(
            @Value("${security.jwt.expiration-hour}") String expiration,
            @Value("${security.jwt.secret-key}") String key
    ){
        long expirationLong = Long.parseLong(expiration);
        LocalDateTime expirationDateTime = LocalDateTime.now().plusHours(expirationLong);
        Instant expirationInstant = expirationDateTime.atZone(ZoneId.systemDefault()).toInstant();

        this.expiration = Date.from(expirationInstant);

        byte[] key64 = Decoders.BASE64.decode(key);
        this.secretKey = Keys.hmacShaKeyFor(key64);
    }

    public String generateToken(Account userAccount){
        return Jwts
                .builder()
                .subject(userAccount.getLogin())
                .expiration(this.expiration)
                .signWith(this.secretKey)
                .compact();
    }

    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .verifyWith(this.secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token){
        try {
            Claims claims = getClaims(token);
            Date expirationDate = claims.getExpiration();
            LocalDateTime expirationTime = expirationDate
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            return LocalDateTime.now().isBefore(expirationTime);

        } catch (Exception e) {
            return false;
        }
    }

    public String getUserLoggedIn(String token) throws ExpiredJwtException{
        return getClaims(token).getSubject();
    }

}
