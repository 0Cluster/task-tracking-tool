package com.cluster.tasktrackingtool.dto;

import com.cluster.tasktrackingtool.models.User;

import lombok.Data;

/**
 * OrganizationRequest
 */
@Data
public class OrganizationRequest {

  private String name;
  private String description;
  private Long userId;
}
