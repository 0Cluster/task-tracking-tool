package com.cluster.tasktrackingtool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationMemberDTO {

  private Long id;
  private Long userId; // Reference to the user, instead of the full User object
  private Long organizationId; // Reference to the organization, instead of the full Organization object
  private String role; // Role in the organization, like 'Owner', 'Admin', or 'Member'
}
