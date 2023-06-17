package org.example.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.impl.TextCodec;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${Jwt_Secret_Key}")
    private String jwtSecretKey;

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(TextCodec.BASE64.decode(jwtSecretKey))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String generateAccessToken(Map<String, Object> claims, String email) {
        Date expiration = Date.from(Instant.now().plusSeconds(60 * 60));
        return Jwts.builder()
                .setIssuer(AppUtils.ISSUER)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.decode(jwtSecretKey))
                .setSubject(email)
                .compact();
    }

    public String generateRefreshToken(String email) {
        Date refreshExpiration = Date.from(Instant.now().plusSeconds(3600 * 24));
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer(AppUtils.ISSUER)
                .setExpiration(refreshExpiration)
                .signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.decode(jwtSecretKey))
                .setSubject(email)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(TextCodec.BASE64.decode(jwtSecretKey))
                    .parseClaimsJws(token);
            return true; // Token is signed
        } catch (SignatureException e) {
            return false; //unsigned token
        } catch (JwtException e) {
            return false; // invalid token
        }
    }

}
