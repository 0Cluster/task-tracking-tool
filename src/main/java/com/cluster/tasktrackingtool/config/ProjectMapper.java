package com.cluster.tasktrackingtool.config;

import com.cluster.tasktrackingtool.dto.ProjectDTO;
import com.cluster.tasktrackingtool.dto.TaskDTO;
import com.cluster.tasktrackingtool.models.Project;
import com.cluster.tasktrackingtool.models.Task;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

  // Map Project to ProjectDTO
  @Mapping(target = "organizationId", source = "organization.id") // Map organization ID instead of the full
  @Mapping(target = "tasks", source = "tasks") // Map tasks
  ProjectDTO projectToProjectDTO(Project project);

  // Map Task to TaskDTO (if not already mapped)
  Set<TaskDTO> tasksToTaskDTOs(Set<Task> tasks);
}
