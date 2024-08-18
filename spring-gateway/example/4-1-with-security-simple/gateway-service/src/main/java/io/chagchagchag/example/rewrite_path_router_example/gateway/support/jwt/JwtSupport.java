package io.chagchagchag.example.rewrite_path_router_example.gateway.support.jwt;

import io.chagchagchag.example.rewrite_path_router_example.gateway.support.jwt.factory.JwtDetailsFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtSupport {
  public String generateToken(Key key, JwtDetails jwtDetails){
    return Jwts.builder()
        .setSubject(String.format("User(email=%s)", jwtDetails.email()))
        .setExpiration(jwtDetails.expiration())
        .claim("email", jwtDetails.email())
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public JwtDetails degenerateToken(Key key, String token){
    JwtParser parser = Jwts.parserBuilder()
        .setSigningKey(key)
        .build();

    Jws<Claims> claimsJws = parser.parseClaimsJws(token);

    return JwtDetailsFactory.of(
        claimsJws.getBody().get("email", String.class),
        claimsJws.getBody().getExpiration()
    );
  }

  public Boolean checkIfExpired(JwtDetails jwtDetails){
    return jwtDetails.expiration().after(new Date());
  }

  public Key hmacShaKeyFor(String secret){
    return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  public String bearerTokenString(String jwt){
    return String.format("Bearer %s", jwt);
  }

  public Boolean checkContainsBearer(String header){
    if(header == null) return false;

    int len = "Bearer ".length();
    return header.substring(0, len).equalsIgnoreCase("Bearer");
  }
}
