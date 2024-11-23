package com.cluster.tasktrackingtool.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cluster.tasktrackingtool.dto.LoginResponse;
import com.cluster.tasktrackingtool.dto.MessageResponse;
import com.cluster.tasktrackingtool.dto.SigninRequest;
import com.cluster.tasktrackingtool.dto.SignupRequest;
import com.cluster.tasktrackingtool.dto.UserDTO;
import com.cluster.tasktrackingtool.models.User;
import com.cluster.tasktrackingtool.repositories.UserRepository;
import com.cluster.tasktrackingtool.services.AuthService;

/**
 * AuthController
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) {
    if (userRepository.existsByUsername(signupRequest.getUsername())) {
      return ResponseEntity.status(400).body(new MessageResponse("username already exists"));
    }

    if (userRepository.existsByEmail(signupRequest.getEmail())) {
      return ResponseEntity.status(400).body(new MessageResponse("email already exists"));
    }
    UserDTO newUser = authService.registerUser(signupRequest);
    return ResponseEntity.ok(newUser);
  }

  @PostMapping("/signin")
  public ResponseEntity<?> signIn(@RequestBody SigninRequest signinRequest) {
    LoginResponse response = authService.authenticateUser(signinRequest.getUsername(), signinRequest.getPassword());
    return ResponseEntity.ok(response);
  }
}
