package com.cluster.tasktrackingtool.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username", "email" }) })
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String username;
  private String email;
  private String password;

  private LocalDateTime createdAt = LocalDateTime.now();

  @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
  private Set<Organization> organizations = new HashSet<>();

  @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL)
  private Set<Task> tasks = new HashSet<>();
}
