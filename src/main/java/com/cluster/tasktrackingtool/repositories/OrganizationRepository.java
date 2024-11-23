package com.cluster.tasktrackingtool.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cluster.tasktrackingtool.models.Organization;
import com.cluster.tasktrackingtool.models.User;

/**
 * OrganizationRepository
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
  Boolean existsByNameAndCreatedBy(String name, User user);

  @Query("SELECT o FROM Organization o WHERE o.createdBy.id = :userId OR :userId IN (SELECT m.user.id FROM o.members m)")
  List<Organization> findAllByMembersUserIdOrCreatedById(Long userId);

  Organization findByName(String name);
}
