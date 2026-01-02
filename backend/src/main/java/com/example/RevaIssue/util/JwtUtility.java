package com.example.RevaIssue.util;

import com.example.RevaIssue.enums.UserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Component
public class JwtUtility {

    private final String SECRET_KEY = generateSecretKey();

    private String generateSecretKey(){
        StringBuilder secret = new StringBuilder();
        Random rand = new Random();
        for(int x = 0; x < 32; x++){
            char character = (char) rand.nextInt(26);
            secret.append(character);
        }
        return secret.toString();
    }

    public String generateAccessToken(String username, UserRole role){
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15 minutes
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), Jwts.SIG.HS256)
                .compact();
    }

    public String extractUsername(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    // We're going to verify the user's admin role for specific privileges.
    public String extractRole(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }
}
