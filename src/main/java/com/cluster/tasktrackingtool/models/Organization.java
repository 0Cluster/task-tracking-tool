package com.cluster.tasktrackingtool.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "organizations")
public class Organization {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String description;

  private LocalDateTime createdAt = LocalDateTime.now();

  @ManyToOne
  @JoinColumn(name = "created_by", nullable = false)
  private User createdBy;

  @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
  private Set<Project> projects = new HashSet<>();

  @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
  private Set<OrganizationMember> members = new HashSet<>();
}
