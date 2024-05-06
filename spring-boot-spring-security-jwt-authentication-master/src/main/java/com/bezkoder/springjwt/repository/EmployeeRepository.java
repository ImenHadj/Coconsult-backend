package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.models.Contract;
import com.bezkoder.springjwt.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {


    @Query("SELECT e FROM Employee e JOIN e.teams t WHERE t.team_id = :teamId")
    List<Employee> getEmployeesByTeamId(@Param("teamId") Long teamId);


}
