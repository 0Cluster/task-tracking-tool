package com.cluster.tasktrackingtool.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "organization_members")
public class OrganizationMember {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  private LocalDateTime joinedAt = LocalDateTime.now();

  @ManyToOne
  @JoinColumn(name = "organization_id", nullable = false)
  private Organization organization;

  private String role; // Example: 'Owner', 'Admin', 'Member'
}
