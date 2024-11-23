package com.cluster.tasktrackingtool.config;

import com.cluster.tasktrackingtool.dto.TaskDTO;
import com.cluster.tasktrackingtool.dto.UserDTO;
import com.cluster.tasktrackingtool.models.Task;
import com.cluster.tasktrackingtool.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

  // Map Task to TaskDTO
  @Mapping(target = "assignedTo", source = "assignedTo") // Map User to UserDTO for assignedTo field
  @Mapping(target = "projectId", source = "project.id") // Map project ID instead of the full project object
  @Mapping(target = "parentTaskId", source = "parentTask.id") // Map parent task ID
  TaskDTO taskToTaskDTO(Task task);

  // Map User to UserDTO
  UserDTO userToUserDTO(User user);
}
