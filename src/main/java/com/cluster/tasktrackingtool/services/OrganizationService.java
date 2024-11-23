package com.cluster.tasktrackingtool.services;

import com.cluster.tasktrackingtool.config.OrganizationMapper;
import com.cluster.tasktrackingtool.config.OrganizationMemberMapper;
import com.cluster.tasktrackingtool.config.ProjectMapper;
import com.cluster.tasktrackingtool.config.TaskMapper;
import com.cluster.tasktrackingtool.dto.*;
import com.cluster.tasktrackingtool.models.Organization;
import com.cluster.tasktrackingtool.models.OrganizationMember;
import com.cluster.tasktrackingtool.models.Project;
import com.cluster.tasktrackingtool.models.Task;
import com.cluster.tasktrackingtool.models.User;
import com.cluster.tasktrackingtool.repositories.OrganizationMemberRepository;
import com.cluster.tasktrackingtool.repositories.OrganizationRepository;
import com.cluster.tasktrackingtool.repositories.ProjectRepository;
import com.cluster.tasktrackingtool.repositories.TaskRepository;
import com.cluster.tasktrackingtool.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrganizationService {

  private final OrganizationRepository organizationRepository;
  private final OrganizationMemberRepository organizationMemberRepository;
  private final UserRepository userRepository;
  private final ProjectRepository projectRepository;
  private final TaskRepository taskRepository;
  private final OrganizationMapper organizationMapper;
  private final OrganizationMemberMapper organizationMemberMapper;
  private final ProjectMapper projectMapper;
  private final TaskMapper taskMapper;

  @Autowired
  public OrganizationService(
      OrganizationRepository organizationRepository,
      OrganizationMemberRepository organizationMemberRepository,
      UserRepository userRepository,
      ProjectRepository projectRepository,
      TaskRepository taskRepository,
      OrganizationMapper organizationMapper,
      OrganizationMemberMapper organizationMemberMapper,
      ProjectMapper projectMapper,
      TaskMapper taskMapper) {
    this.organizationRepository = organizationRepository;
    this.organizationMemberRepository = organizationMemberRepository;
    this.userRepository = userRepository;
    this.projectRepository = projectRepository;
    this.taskRepository = taskRepository;
    this.organizationMapper = organizationMapper;
    this.organizationMemberMapper = organizationMemberMapper;
    this.projectMapper = projectMapper;
    this.taskMapper = taskMapper;
  }

  public OrganizationDTO createOrganization(OrganizationRequest organizationRequest) {
    User user = userRepository.findById(organizationRequest.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (organizationRepository.existsByNameAndCreatedBy(organizationRequest.getName(), user)) {
      throw new RuntimeException("Organization already exists");
    }

    Organization organization = new Organization();
    organization.setName(organizationRequest.getName());
    organization.setDescription(organizationRequest.getDescription());
    organization.setCreatedBy(user);

    return organizationMapper.organizationToOrganizationDTO(organizationRepository.save(organization));
  }

  public OrganizationMemberDTO addMemberToOrganization(Long userId, Long organizationId, String role) {
    Organization organization = organizationRepository.findById(organizationId)
        .orElseThrow(() -> new RuntimeException("Organization not found"));
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (organizationMemberRepository.existsByUserAndOrganization(user, organization)) {
      throw new RuntimeException("Member already in organization");
    }

    OrganizationMember organizationMember = new OrganizationMember();
    organizationMember.setUser(user);
    organizationMember.setOrganization(organization);
    organizationMember.setRole(role);

    return organizationMemberMapper.organizationMemberToOrganizationMemberDTO(
        organizationMemberRepository.save(organizationMember));
  }

  public ProjectDTO createProject(Long organizationId, String projectName, String projectDescription) {
    Organization organization = organizationRepository.findById(organizationId)
        .orElseThrow(() -> new RuntimeException("Organization not found"));

    if (projectRepository.existsByNameAndOrganization(projectName, organization)) {
      throw new RuntimeException("Project name already exists");
    }

    Project project = new Project();
    project.setName(projectName);
    project.setDescription(projectDescription);
    project.setOrganization(organization);

    return projectMapper.projectToProjectDTO(projectRepository.save(project));
  }

  public TaskDTO newTask(Long projectId, Long userId, String title, String description, String status,
      java.time.LocalDate dueDate, Duration estimatedDuration) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("Project not found"));
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (taskRepository.existsByTitleAndProject(title, project)) {
      throw new RuntimeException("Task already exists");
    }

    Task task = new Task();
    task.setTitle(title);
    task.setDescription(description);
    task.setStatus(status);
    task.setDueDate(dueDate);
    task.setProject(project);
    task.setAssignedTo(user);
    task.setParentTask(null);
    task.setEstimatedDuration(estimatedDuration);

    return taskMapper.taskToTaskDTO(taskRepository.save(task));
  }

  public TaskDTO newSubtask(Long taskId, Long userId, String title, String description, String status,
      java.time.LocalDate dueDate, Duration estimatedDuration) {
    Task parentTask = taskRepository.findById(taskId)
        .orElseThrow(() -> new RuntimeException("Parent task not found"));
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (taskRepository.existsByTitleAndParentTask(title, parentTask)) {
      throw new RuntimeException("Subtask already exists");
    }

    Task subtask = new Task();
    subtask.setTitle(title);
    subtask.setDescription(description);
    subtask.setStatus(status);
    subtask.setDueDate(dueDate);
    subtask.setProject(parentTask.getProject());
    subtask.setAssignedTo(user);
    subtask.setParentTask(parentTask);
    subtask.setEstimatedDuration(estimatedDuration);

    return taskMapper.taskToTaskDTO(taskRepository.save(subtask));
  }

  public List<OrganizationDTO> getAllOrganizationsByUsername(String username) {
    User user = userRepository.findByUsername(username);

    if (user == null) {
      throw new RuntimeException("User not found");
    }

    List<Organization> organizations = organizationRepository.findAllByMembersUserIdOrCreatedById(user.getId());

    return organizations.stream()
        .map(organizationMapper::organizationToOrganizationDTO)
        .toList();
  }

  public List<ProjectDTO> getProjectsForUser(String username, Long organizationId) {
    // Step 1: Check if the user is an owner or admin in the organization
    String userRole = organizationMemberRepository.findRoleByUsernameAndOrganizationId(username, organizationId);

    if (userRole == null) {
      throw new RuntimeException("User does not belong to this organization");
    }

    // Step 2: If the user is an owner or admin, return all projects
    if (userRole.equals("owner") || userRole.equals("admin")) {
      List<Project> allProjects = projectRepository.findByOrganizationId(organizationId);
      return allProjects.stream()
          .map(project -> projectMapper.projectToProjectDTO(project))
          .collect(Collectors.toList());
    }

    // Step 3: If the user is a member, return only the projects they are assigned
    // tasks to
    List<Task> tasks = taskRepository.findByAssignedToUsernameAndProjectOrganizationId(username, organizationId);
    Set<Long> projectIds = tasks.stream()
        .map(Task::getProject)
        .map(Project::getId)
        .collect(Collectors.toSet());

    List<Project> projects = projectRepository.findAllById(projectIds);
    return projects.stream()
        .map(project -> projectMapper.projectToProjectDTO(project))
        .collect(Collectors.toList());
  }

  public List<TaskDTO> getTasksForUser(String username, Long projectId) {
    // Step 1: Check if the user is an owner or admin in the organization
    String role = organizationMemberRepository
        .findRoleByUsernameAndOrganizationId(username,
            projectRepository.findById(projectId).get().getOrganization().getId());

    if (role == null) {
      throw new RuntimeException("User does not belong to this organization");
    }

    // Step 2: If the user is an owner or admin, return all tasks in the project
    if (role.equals("owner") || role.equals("admin")) {
      List<Task> allTasks = taskRepository.findByProjectId(projectId);
      return allTasks.stream()
          .map(task -> taskMapper.taskToTaskDTO(task))
          .collect(Collectors.toList());
    }

    // Step 3: If the user is a member, return only the tasks assigned to the user
    // in the project
    User user = userRepository.findByUsername(username);

    List<Task> userTasks = taskRepository.findByAssignedToAndProjectId(user, projectId);
    return userTasks.stream()
        .map(task -> taskMapper.taskToTaskDTO(task))
        .collect(Collectors.toList());
  }

  public List<TaskDTO> getSubtasksForUser(String username, Long taskId) {

    String role = organizationMemberRepository
        .findRoleByUsernameAndOrganizationId(username,
            projectRepository.findById(taskRepository.findById(taskId).get().getId()).get().getOrganization()
                .getId());
    if (role == null) {
      throw new RuntimeException("user does not belong to this organization");
    }
    if (role.equals("owner") || role.equals("admin")) {
      List<Task> subtasks = taskRepository.findByParentTaskId(taskId);
      return subtasks.stream()
          .map(taskMapper::taskToTaskDTO)
          .collect(Collectors.toList());
    }

    User user = userRepository.findByUsername(username);

    List<Task> userTasks = taskRepository.findByAssignedToAndParentTaskId(user, taskId);
    return userTasks.stream()
        .map(task -> taskMapper.taskToTaskDTO(task))
        .collect(Collectors.toList());
  }
}
