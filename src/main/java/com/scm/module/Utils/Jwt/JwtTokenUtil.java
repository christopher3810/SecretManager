package com.scm.module.Utils.Jwt;

import com.scm.module.Connection.VaultWebClient;
import com.scm.module.Model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {
    private String secret = "your_secret_key"; // Replace with a secure secret key
    private final long expiration = 3600L; // Token expiration time in seconds

    public JwtTokenUtil(VaultWebClient vaultWebClient) {
        // Replace the 'path_to_secret' with the actual path to the JWT secret in Vault
        String secretPath = "path_to_secret";
        vaultWebClient.getSecret(secretPath)
            .subscribe(response -> {
                secret = response;
            });
    }

    public String generateToken(UserEntity user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("id", user.getId().toString());
        Date now = new Date();
        Date validity = new Date(now.getTime() + expiration * 1000);
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public Long getIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Long.valueOf(claims.get("id").toString());
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

}
