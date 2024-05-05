package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Consultant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultantRepository extends JpaRepository<Consultant,Long> {

}
