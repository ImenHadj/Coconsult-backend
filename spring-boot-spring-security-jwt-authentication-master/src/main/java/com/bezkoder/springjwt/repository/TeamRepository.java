package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Team;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,Long> {
}
