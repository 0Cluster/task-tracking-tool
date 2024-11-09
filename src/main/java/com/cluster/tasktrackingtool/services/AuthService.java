package com.cluster.tasktrackingtool.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cluster.tasktrackingtool.dto.LoginResponse;
import com.cluster.tasktrackingtool.dto.SignupRequest;
import com.cluster.tasktrackingtool.models.User;
import com.cluster.tasktrackingtool.repositories.UserRepository;
import com.cluster.tasktrackingtool.security.JwtUtils;

/**
 * AuthService
 */
@Service
public class AuthService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private JwtUtils jwtUtil;

  public User registerUser(SignupRequest signupRequest) {
    User user = new User();
    user.setUsername(signupRequest.getUsername());
    user.setEmail(signupRequest.getEmail());
    user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

    return userRepository.save(user);
  }

  public LoginResponse authenticateUser(String username, String password) {
    Authentication authentication = authenticationProvider.authenticate(
        new UsernamePasswordAuthenticationToken(username, password));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String jwtToken = jwtUtil.generateTokenFromUsername(userDetails);
    // List<String> roles = userDetails.getAuthorities().stream()
    // .map(item -> item.getAuthority())
    // .collect(Collectors.toList());

    return new LoginResponse(userDetails.getUsername(), jwtToken);

  }
}
