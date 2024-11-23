package com.cluster.tasktrackingtool.dto;

import java.util.List;

import com.cluster.tasktrackingtool.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * LoginResponse
 */
@Data
@AllArgsConstructor
public class LoginResponse {
  private UserDTO userDTO;
  private String jwtToken;
}
