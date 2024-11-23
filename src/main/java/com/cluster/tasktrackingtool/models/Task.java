package com.cluster.tasktrackingtool.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String description;

  private String status; // Example: 'Pending', 'In Progress', 'Completed'

  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "due_date")
  private java.time.LocalDate dueDate;

  @ManyToOne
  @JoinColumn(name = "project_id", nullable = false)
  private Project project;

  @ManyToOne
  @JoinColumn(name = "assigned_to")
  private User assignedTo;

  @ManyToOne
  @JoinColumn(name = "parent_task_id")
  private Task parentTask;

  @OneToMany(mappedBy = "parentTask", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Task> subtasks = new ArrayList<>();

  // New fields for tracking time
  @Column(name = "estimated_duration")
  private Duration estimatedDuration; // Estimated duration for the task

  @Column(name = "time_spent")
  private Duration timeSpent = Duration.ZERO; // Default value as 0

  /**
   * Method to add time to the `timeSpent` field.
   * 
   * @param additionalTime Time to add (e.g., Duration.ofHours(2))
   */
  public void addTimeSpent(Duration additionalTime) {
    this.timeSpent = this.timeSpent.plus(additionalTime);
  }
}
