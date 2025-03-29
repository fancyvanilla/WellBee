package tn.fst.team2.jee.wellbee.oauth.services;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tn.fst.team2.jee.wellbee.oauth.dto.UserDto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class JwtService {

    @Value("jwt.secret-key")
    private String secret_key;

    public String createJwtToken(UserDto user) {
        Map<String, Object> claims= new HashMap<>();
        claims.put("name",user.getName());
        claims.put("email", user.getEmail());
        claims.put("picture", user.getPicture());
        return Jwts.builder()
                .setSubject(user.getSub())
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();
    }


    public Boolean isTokenValid(String token){
        try {
            Jwts.parser()
                    .setSigningKey(secret_key)
                    .parseClaimsJws(token);
            return true;
        }
        catch(ExpiredJwtException e){
            System.out.println("Token expired");
        }
        catch(JwtException e){
            System.out.println("Invalid token");
        }
        return false;
    }

    public UserDto decodeToken(String token){
        try {
            Claims claims= Jwts.parser()
                    .setSigningKey(secret_key)
                    .parseClaimsJws(token)
                    .getBody();
            return new UserDto(
                    claims.getSubject(),
                    claims.get("name", String.class),
                    claims.get("email", String.class),
                    claims.get("picture", String.class)
            );
        }
        catch(ExpiredJwtException e){
            System.out.println("Token expired");
        }
        catch(JwtException e){
            System.out.println("Invalid token");
        }
        return null;
    }
}
