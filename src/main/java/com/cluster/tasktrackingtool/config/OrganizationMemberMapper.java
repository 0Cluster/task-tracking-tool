package com.cluster.tasktrackingtool.config;

import com.cluster.tasktrackingtool.dto.AddMemberRequest;
import com.cluster.tasktrackingtool.dto.OrganizationMemberDTO;
import com.cluster.tasktrackingtool.models.Organization;
import com.cluster.tasktrackingtool.models.OrganizationMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrganizationMemberMapper {

  @Mapping(target = "userId", source = "user.id") // Map user ID instead of the full user object
  @Mapping(target = "organizationId", source = "organization.id") // Map organization ID instead of the full
  OrganizationMemberDTO organizationMemberToOrganizationMemberDTO(OrganizationMember organizationMember);

}
