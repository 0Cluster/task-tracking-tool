package com.cluster.tasktrackingtool.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cluster.tasktrackingtool.models.Organization;
import com.cluster.tasktrackingtool.models.Project;

/**
 * ProjectRepository
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
  Boolean existsByNameAndOrganization(String name, Organization organization);

  List<Project> findByOrganizationId(Long organizationId);
}
