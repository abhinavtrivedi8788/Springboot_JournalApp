package net.engineeringdigest.journalApp.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtility {

    @Value("${jwt.api.key}")
    private String SECRET_KEY ;


    public String generateToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("email",user.getAuthorities().stream().findFirst().get().getAuthority());
        return createToken(claims,user.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 2)) // Expiration in next 2 minutes
                .signWith(createSignature())
                .compact();
    }

    private SecretKey createSignature() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }


    // This method is used to extract the username from the token,
    // which can be used for authentication and authorization purposes.
    public String extractedUsernameFromToken(String jwtToken) {
        return getClaimsFromToken(jwtToken).getSubject();
    }

    // This method is used to extract the claims from the token,
    // which can be used to retrieve additional information about the user or the token itself.

    public Claims getClaimsFromToken(String jwtToken) {
        return Jwts.parser()
                .verifyWith(createSignature())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }


    public boolean validateToken(String jwtToken) {
       return  isTokenExpired(jwtToken);
    }

    // This method is used to validate the token
    // by checking its expiration and ensuring that it is properly signed.
    private boolean isTokenExpired(String jwtToken) {
        try {
            Claims claims = getClaimsFromToken(jwtToken);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
