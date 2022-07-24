package com.example.konko.Utils;

import com.example.konko.User.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtility {

    private static final int JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateAuthToken(User user){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateAuthToken(claims,user.toString());
    }

    private Boolean isTokenExpired(String authToken){
        final Date expiration = getExpirationDateFromAuthToken(authToken);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromAuthToken(String authToken){
        return getClaimFromAuthToken(authToken, Claims::getExpiration);
    }

    private <T> T getClaimFromAuthToken(String authToken, Function<Claims,T>claimsResolver){
        final Claims claims = getAllClaimsFromAAuthToken(authToken);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromAAuthToken(String authToken){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken).getBody();
    }

    //retrieve username from jwt token
    public String getEmailFromToken(String token) {
        return getClaimFromAuthToken(token, Claims::getSubject);
    }

   //While creating the token -
//   1. Define claims of the token, like issuer,expiration subject and id
//    2.sign the jwt using HS512 algorithm and secret key.
    private String doGenerateAuthToken(Map<String,Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512,secretKey).compact();
    }

    public Boolean validateAuthToken(String authToken, User user){
        final String email = getEmailFromToken(authToken);
        return (email.equals(user.getEmail()) && !isTokenExpired(authToken));
    }

}

