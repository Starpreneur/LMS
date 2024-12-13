package com.LMS.LibraryManagementSystem.security;

import com.LMS.LibraryManagementSystem.model.Users;
import com.LMS.LibraryManagementSystem.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Autowired
     UserRepository userRepository;

    private static final Key SECRET_KEY_VALUE = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long TOKEN_EXPIRATION_TIME = 3600000;

    public String generateToken(Users user) {
        Map<String,Object> claims = new HashMap<>();

        claims.put("userName",user.getName());
        claims.put("userRole",user.getUserType());
        claims.put("userId",user.getUserId());
        claims.put("emailId",user.getEmailId());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+TOKEN_EXPIRATION_TIME))
                .signWith(SECRET_KEY_VALUE)
                .compact();
    }

    public Claims validateToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY_VALUE)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
