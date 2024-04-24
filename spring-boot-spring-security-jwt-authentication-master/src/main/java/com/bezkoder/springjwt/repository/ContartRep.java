package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContartRep extends JpaRepository<Contract,Long> {

    @Query("SELECT c FROM Contract c WHERE c.client.idClient =:id")
    List<Contract> getContractbyclient(@Param("id") Long id);
    @Query("Select c From Contract c WHERE c.endDate <= :dueDateThreshold")
    List<Contract> findExpiringContracts(@Param("dueDateThreshold") LocalDateTime dueDateThreshold);
}
