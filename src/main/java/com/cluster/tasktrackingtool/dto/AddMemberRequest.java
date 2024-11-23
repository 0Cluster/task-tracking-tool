package com.cluster.tasktrackingtool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class AddMemberRequest {
  @NotNull
  private Long userId;

  @NotNull
  private Long organizationId;

  @NotEmpty
  private String role;
}
