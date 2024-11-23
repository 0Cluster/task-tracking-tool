package com.cluster.tasktrackingtool.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.cluster.tasktrackingtool.dto.*;
import com.cluster.tasktrackingtool.services.OrganizationService;

@RestController
@RequestMapping("/api/user/organization")
public class OrganizationController {

  private final OrganizationService organizationService;

  @Autowired
  public OrganizationController(OrganizationService organizationService) {
    this.organizationService = organizationService;
  }

  @PostMapping("/create")
  public ResponseEntity<?> createOrganization(@RequestBody OrganizationRequest organizationRequest) {
    try {
      OrganizationDTO organization = organizationService.createOrganization(organizationRequest);
      organizationService.addMemberToOrganization(organizationRequest.getUserId(), organization.getId(), "owner");
      return ResponseEntity.status(201).body(organization);
    } catch (Exception e) {
      return ResponseEntity.status(400).body(new MessageResponse(e.getMessage()));
    }
  }

  @PostMapping("/addMember")
  public ResponseEntity<?> addMember(@RequestBody AddMemberRequest addMemberRequest) {
    try {
      OrganizationMemberDTO member = organizationService.addMemberToOrganization(
          addMemberRequest.getUserId(),
          addMemberRequest.getOrganizationId(),
          addMemberRequest.getRole());
      return ResponseEntity.status(201).body(member);
    } catch (Exception e) {
      return ResponseEntity.status(400).body(new MessageResponse(e.getMessage()));
    }
  }

  @PostMapping("/createProject")
  public ResponseEntity<?> createProject(@RequestBody CreateProjectRequest createProjectRequest) {
    try {
      ProjectDTO project = organizationService.createProject(
          createProjectRequest.getOrganizationId(),
          createProjectRequest.getProjectName(),
          createProjectRequest.getProjectDescription());
      return ResponseEntity.status(201).body(project);
    } catch (Exception e) {
      return ResponseEntity.status(400).body(new MessageResponse(e.getMessage()));
    }
  }

  @PostMapping("/addTask")
  public ResponseEntity<?> addTask(@RequestBody AddTaskRequest addTaskRequest) {
    try {
      TaskDTO task = organizationService.newTask(
          addTaskRequest.getProjectId(),
          addTaskRequest.getUserId(),
          addTaskRequest.getTitle(),
          addTaskRequest.getDescription(),
          addTaskRequest.getStatus(),
          addTaskRequest.getDueDate(),
          addTaskRequest.getEstimatedDuration());
      return ResponseEntity.status(201).body(task);
    } catch (Exception e) {
      return ResponseEntity.status(400).body(new MessageResponse(e.getMessage()));
    }
  }

  @PostMapping("/addSubtask")
  public ResponseEntity<?> addSubtask(@RequestBody AddSubtaskRequest addSubtaskRequest) {
    try {
      TaskDTO subTask = organizationService.newSubtask(
          addSubtaskRequest.getTaskId(),
          addSubtaskRequest.getUserId(),
          addSubtaskRequest.getTitle(),
          addSubtaskRequest.getDescription(),
          addSubtaskRequest.getStatus(),
          addSubtaskRequest.getDueDate(),
          addSubtaskRequest.getEstimatedDuration());
      return ResponseEntity.status(201).body(subTask);
    } catch (Exception e) {
      return ResponseEntity.status(400).body(new MessageResponse(e.getMessage()));
    }
  }

  @GetMapping
  public ResponseEntity<List<OrganizationDTO>> getAllOrganizationsForUser(Authentication authentication) {
    try {
      String username = authentication.getName(); // Extract username from Authentication object
      List<OrganizationDTO> organizations = organizationService.getAllOrganizationsByUsername(username);
      return ResponseEntity.ok(organizations);
    } catch (Exception e) {
      return ResponseEntity.status(400).body(null);
    }
  }

  @GetMapping("/{organizationId}/projects")
  public ResponseEntity<List<ProjectDTO>> getProjectsForUser(Authentication authentication,
      @PathVariable Long organizationId) {
    try {
      String username = authentication.getName(); // Extract the username from the authentication object
      List<ProjectDTO> projects = organizationService.getProjectsForUser(username, organizationId);
      return ResponseEntity.ok(projects);
    } catch (Exception e) {
      return ResponseEntity.status(400).body(null);
    }
  }

  @GetMapping("/projects/{projectId}/tasks")
  public ResponseEntity<List<TaskDTO>> getTasksForUser(@PathVariable Long projectId, Authentication authentication) {
    try {
      String username = authentication.getName(); // Extract the username from the authentication object
      List<TaskDTO> tasks = organizationService.getTasksForUser(username, projectId);
      return ResponseEntity.ok(tasks);
    } catch (Exception e) {
      return ResponseEntity.status(400).body(null);
    }
  }

  @GetMapping("/tasks/{taskId}/subtasks")
  public ResponseEntity<List<TaskDTO>> getSubtasksForTask(@PathVariable Long taskId, Authentication authentication) {
    try {
      String username = authentication.getName(); // Extract the username from the authentication object
      List<TaskDTO> subtasks = organizationService.getSubtasksForUser(username, taskId);
      return ResponseEntity.ok(subtasks);
    } catch (Exception e) {
      return ResponseEntity.status(400).body(null);
    }
  }
}
