package com.raktkosh.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.raktkosh.pojos.User;

public class UserDetailsImpl implements UserDetails {

  private static final long serialVersionUID = 2L;
  private Long id;
  private String name;
  private String username;
  private String email;
  private boolean activated;
  private String token;
  
  
  @JsonIgnore
  private String password;
  

  
  private Collection<? extends GrantedAuthority> authorities;
    
  public UserDetailsImpl(Long id, String name, String username, String email, boolean activated, String password,
      Collection<? extends GrantedAuthority> authorities) {
    super();
    this.id = id;
    this.name = name;
    this.username = username;
    this.email = email;
    this.activated = activated;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(User user) {
    Collection<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(user.getRole().toString()));
    
    
    return new UserDetailsImpl(
      user.getId(),
      user.getName(),
      user.getUsername(),
      user.getEmail(),
      user.isActivated(),
      user.getPassword(),
      authorities
    );
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.activated;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    UserDetailsImpl user = (UserDetailsImpl) obj;
    return Objects.equals(user.id, this.id);
  }
}
