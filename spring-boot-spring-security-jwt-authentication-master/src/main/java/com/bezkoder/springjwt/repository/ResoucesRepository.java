package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResoucesRepository  extends JpaRepository<Resources,Long> {


}
