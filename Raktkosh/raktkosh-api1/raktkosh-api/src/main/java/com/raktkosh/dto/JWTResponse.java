package com.raktkosh.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.raktkosh.security.UserDetailsImpl;

public class JWTResponse extends UserDetailsImpl {
  
  private static final long serialVersionUID = 29L;
  private String jwtToken;

  public JWTResponse(Long id, String fullname, String username, String email, boolean activated, String password,
      Collection<? extends GrantedAuthority> authorities) {
    super(id, fullname, username, email, activated, password, authorities);
  }

  public String getJwtToken() {
    return jwtToken;
  }

  public void setJwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
  }

public static long getSerialversionuid() {
	return serialVersionUID;
}
}
