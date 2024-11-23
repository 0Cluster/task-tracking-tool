package com.cluster.tasktrackingtool.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

@Data
public class CreateProjectRequest {
  @NotNull
  private Long organizationId;

  @NotEmpty
  private String projectName;

  @NotEmpty
  private String projectDescription;
}
