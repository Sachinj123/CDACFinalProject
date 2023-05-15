package com.raktkosh.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.raktkosh.services.UserDetailsServiceImpl;
import com.raktkosh.utils.JWTUtils;

/**
 * AuthTokenFilter intercepts every request, then tries to 
 * extract and parse JWT token from Authentication Header.
 * If successful, then it validate the token and if valid
 * it fetch user details from database and populate the
 * principal held by SecurityContext. 
 */

public class JWTAuthTokenFilter extends OncePerRequestFilter {
  
  @Autowired
  private JWTUtils jwtUtils;
  
  @Autowired
  private UserDetailsServiceImpl userDetailService;
  
  private static final Logger logger = LoggerFactory.getLogger(JWTAuthTokenFilter.class);

  /**
   * Tries to filter and validate the JWT token and populate
   * principal with user details, if token in valid.
   * @param request
   * @param response
   * @param filterChain
   * @return void
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String token = parseJWT(request);
      if (token != null && jwtUtils.isValidToken(token)) {
        String username = jwtUtils.getUsernameFromToken(token);
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    catch (Exception ex) {
      logger.error("Cannot set user authentication: {}", ex);
    }
    filterChain.doFilter(request, response);
  }
  
  /**
   * Parse request header and tries to extract JWT token.
   * @param request
   * @return JWT Token
   */
  private String parseJWT (HttpServletRequest request) {
    String authorization = request.getHeader("Authorization");
    
    if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
      return authorization.substring(7, authorization.length());
    }
    return null;
  }

}
