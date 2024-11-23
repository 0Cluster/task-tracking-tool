package com.cluster.tasktrackingtool.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cluster.tasktrackingtool.models.Project;
import com.cluster.tasktrackingtool.models.Task;
import com.cluster.tasktrackingtool.models.User;

/**
 * TaskRepository
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
  Boolean existsByTitleAndProject(String title, Project project);

  Boolean existsByTitleAndParentTask(String title, Task task);

  List<Task> findByParentTaskId(Long taskId);

  List<Task> findByProjectId(Long projectId);

  List<Task> findByAssignedToAndProjectId(User assignedTo, Long projectId);

  List<Task> findByAssignedToUsernameAndProjectOrganizationId(String username, Long organizationId);

  List<Task> findByAssignedToAndParentTaskId(User user, Long taskId);
}
