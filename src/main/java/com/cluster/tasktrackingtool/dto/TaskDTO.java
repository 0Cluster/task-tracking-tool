package com.cluster.tasktrackingtool.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

  private Long id;
  private String title;
  private String description;
  private String status; // Example: 'Pending', 'In Progress', 'Completed'
  private LocalDate dueDate;
  private Long projectId; // We use projectId to reference the related project
  private UserDTO assignedTo; // UserDTO for assigned user
  private Long parentTaskId; // We can reference the parent task by its ID
  private Duration estimatedDuration;
  private Duration timeSpent;
}
