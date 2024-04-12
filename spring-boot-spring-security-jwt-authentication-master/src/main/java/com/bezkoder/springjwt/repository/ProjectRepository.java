package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
}
