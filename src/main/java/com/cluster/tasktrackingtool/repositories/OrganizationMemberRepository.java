package com.cluster.tasktrackingtool.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cluster.tasktrackingtool.models.Organization;
import com.cluster.tasktrackingtool.models.OrganizationMember;
import com.cluster.tasktrackingtool.models.User;

/**
 * OrganizationMemberRepository
 */
@Repository
public interface OrganizationMemberRepository extends JpaRepository<OrganizationMember, Long> {
  Boolean existsByUserAndOrganization(User user, Organization organization);

  @Query("SELECT om.role FROM OrganizationMember om WHERE om.user.username = :username AND om.organization.id = :organizationId")
  String findRoleByUsernameAndOrganizationId(@Param("username") String username,
      @Param("organizationId") Long organizationId);

  OrganizationMember findByUserUsernameAndOrganizationId(String username, Long id);
}
