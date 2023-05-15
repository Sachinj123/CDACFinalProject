package com.raktkosh.utils;

import java.util.Date;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.raktkosh.security.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTUtils {
  private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);
  
  	
  @Value("${com.raktkosh.JWT_SECRET}")
  private String jwtSecret;
  
  @Value("${com.raktkosh.JWT_VALIDITY}")
  private int jwtValidity;
  
  @Value("${com.raktkosh.JWT_ISSUER}")
  private String jwtIssuer;
    
  public String generateJWTToken (Authentication authentication) {
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
    return Jwts.builder()
            .setSubject(userPrincipal.getUsername())
            // .setClaims(userPrincipal)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setIssuer(jwtIssuer)
            .setExpiration(new Date(System.currentTimeMillis() + jwtValidity))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
  }
  
  public Claims getAllClaims (String token) {
    return Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody();
  }
  
  private <T> T getClaim (String token, Function<Claims, T> resolver) {
    return resolver.apply(getAllClaims(token));
  }
  
  public String getUsernameFromToken (String token) {
    return getClaim(token, Claims::getSubject);
  }
  
  public Date getExpirationFromToken (String token) {
    return getClaim(token, Claims::getExpiration);
  }
  
  public boolean isValidToken (String token) {
    try {
      getAllClaims(token);
      return true;
    }
    catch (SignatureException ex) {
    	logger.error("Invalid JWT signature: {} ", ex.getMessage());
    }
    catch (MalformedJwtException ex) {
    	logger.error("Invalid JWT token: {} ", ex.getMessage());
    }
    catch (ExpiredJwtException ex) {
    	logger.error("JWT token is expired: {} ", ex.getMessage());
    }
    catch (UnsupportedJwtException ex) {
    	logger.error("JWT token is unsupported: {} ", ex.getMessage());
    }
    catch (IllegalArgumentException ex) {
    	logger.error("JWT claims string is empty: {} ", ex.getMessage());
    }
    return false;
  }
}
