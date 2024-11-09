package com.cluster.tasktrackingtool.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * LoginResponse
 */
@Data
@AllArgsConstructor
public class LoginResponse {

  private String username;
  private String jwtToken;
}
