package com.cluster.tasktrackingtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cluster.tasktrackingtool.config.UserMapper;
import com.cluster.tasktrackingtool.dto.LoginResponse;
import com.cluster.tasktrackingtool.dto.SignupRequest;
import com.cluster.tasktrackingtool.dto.UserDTO;
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

  @Autowired
  private UserMapper userMapper;

  public UserDTO registerUser(SignupRequest signupRequest) {
    User user = userMapper.signupRequestToUser(signupRequest);
    user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
    userRepository.save(user);
    UserDTO userDto = userMapper.userToUserDto(user);
    return userDto;
  }

  public LoginResponse authenticateUser(String username, String password) {
    Authentication authentication = authenticationProvider.authenticate(
        new UsernamePasswordAuthenticationToken(username, password));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String jwtToken = jwtUtil.generateTokenFromUsername(userDetails);
    UserDTO userDto = userMapper.userToUserDto(userRepository.findByUsername(userDetails.getUsername()));
    return new LoginResponse(userDto, jwtToken);

  }
}
