package com.tabia.projeto_tecnico.Security;

import com.tabia.projeto_tecnico.model.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    private String expiration = "30";

    private String signatureKey = "teste";

    public String generateToken( UserEntity user ){
        long expString = Long.valueOf(expiration);
        LocalDateTime dateTimeExpiration = LocalDateTime.now().plusDays(expString);
        Instant instant = dateTimeExpiration.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        return Jwts
                .builder()
                .setSubject(user.getUsername())
                .setExpiration(data)
                .signWith( SignatureAlgorithm.HS512, signatureKey )
                .compact();
    }

    private Claims getClaims(String token ) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(signatureKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValidation(String token){
        try{
            Claims claims = getClaims(token);
            Date expirationDate = claims.getExpiration();
            LocalDateTime data =
                    expirationDate.toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);
        }catch (Exception e){
            return false;
        }
    }

    public String getUserLogin(String token) throws ExpiredJwtException{
        return (String) getClaims(token).getSubject();
    }
}