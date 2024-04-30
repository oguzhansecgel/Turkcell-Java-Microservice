package com.turkcell.authserver.core.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.expiration}")
    private long EXPIRATION;
    @Value("${jwt.secret}") // yaml dosyasından değer okuma
    private String SECRET_KEY;
    public String generateToken(String username, Map<String,Object> extraClaims)
    {
        String token = Jwts
                .builder() // token üretmek için
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis())) // token başlangıç süresi
                .expiration(new Date(System.currentTimeMillis()+ EXPIRATION)) // token bitiş süresi
                .claims(extraClaims)
                .signWith(getSigninKey())
                .compact();

        return token;
    }

    public Boolean validateToken(String token)
    {
        return getTokenClaims(token).getExpiration().after(new Date());
    }

    private Claims getTokenClaims(String token)
    {
        // parser ise çözümlemek decode için kullanılır
       return  Jwts.parser().verifyWith((SecretKey) getSigninKey()).build().parseSignedClaims(token).getPayload();

    }
    public String extractUsername(String token)
    {
        return getTokenClaims(token).getSubject();
    }
    public List<String> extractRoles(String token)
    {
        return getTokenClaims(token).get("roles",List.class);
    }
    private Key getSigninKey()
    {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
