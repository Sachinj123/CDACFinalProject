package com.raktkosh.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * The first point of entry for Spring Security.
 * It is the entry point to check if a user is authenticated
 * and logs the person in or throws exception (unauthorized).
 */

@Component
public class JWTAuthEntrypoint implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(JWTAuthEntrypoint.class);
  
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    logger.error("Unauthorized error: {}", authException.getMessage());
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
  }

}
