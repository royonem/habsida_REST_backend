package web.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {
    private final Key jwtSigningKey;

    public JwtProvider(Key jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }

    // creates a signed Jwt token that is sent to the client proving the user is authenticated or not
    public String generateToken(String username, Set<String> roleNames) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roleNames", roleNames)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(jwtSigningKey, SignatureAlgorithm.HS256)
                .compact();
    }
}