package com.cluster.tasktrackingtool.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

  private Long id;
  private String name;
  private String description;
  private Long organizationId;
}
