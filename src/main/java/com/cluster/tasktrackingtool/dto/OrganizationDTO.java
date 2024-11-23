package com.cluster.tasktrackingtool.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDTO {

  private Long id;
  private String name;
  private String description;
  private LocalDateTime createdAt;
  private UserDTO createdBy;
  private Set<ProjectDTO> projects = new HashSet<>();
  private Set<OrganizationMemberDTO> members = new HashSet<>();
}
