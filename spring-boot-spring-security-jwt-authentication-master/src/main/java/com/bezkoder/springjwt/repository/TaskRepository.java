package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByProjectProjectid(Long projectId);
}
