package com.raktkosh.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.raktkosh.dao.UserRepository;
import com.raktkosh.pojos.User;
import com.raktkosh.security.UserDetailsImpl;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
  
  @Autowired
  private UserRepository userRepository;

  /**
   * Try to fetch user details from the database by username.
   * If found build and return UserDetailsImpl object from that
   * else throws UsernameNotFoundException.
   * @param username
   * @return userDetails
   */	
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
                  .orElseThrow(() -> new UsernameNotFoundException("User \""+ username +"\" doesn't exist."));
    return UserDetailsImpl.build(user);
  }

}
