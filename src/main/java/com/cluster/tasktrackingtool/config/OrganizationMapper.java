package com.cluster.tasktrackingtool.config;

import com.cluster.tasktrackingtool.dto.OrganizationDTO;
import com.cluster.tasktrackingtool.dto.OrganizationMemberDTO;
import com.cluster.tasktrackingtool.dto.ProjectDTO;
import com.cluster.tasktrackingtool.dto.UserDTO;
import com.cluster.tasktrackingtool.models.Organization;
import com.cluster.tasktrackingtool.models.OrganizationMember;
import com.cluster.tasktrackingtool.models.Project;
import com.cluster.tasktrackingtool.models.User;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

  // Map Organization to OrganizationDTO
  @Mapping(target = "createdBy", source = "createdBy") // Map createdBy to UserDTO
  OrganizationDTO organizationToOrganizationDTO(Organization organization);

  // Map Project to ProjectDTO
  Set<ProjectDTO> projectsToProjectDTOs(Set<Project> projects);

  // Map OrganizationMember to OrganizationMemberDTO
  Set<OrganizationMemberDTO> membersToOrganizationMemberDTOs(Set<OrganizationMember> members);
}
