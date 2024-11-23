package com.cluster.tasktrackingtool.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class AddTaskRequest {
  @NotNull
  private Long projectId;

  @NotNull
  private Long userId;

  @NotEmpty
  private String title;

  @NotEmpty
  private String description;

  @NotEmpty
  private String status;

  private LocalDate dueDate;
  private Duration estimatedDuration;
}
