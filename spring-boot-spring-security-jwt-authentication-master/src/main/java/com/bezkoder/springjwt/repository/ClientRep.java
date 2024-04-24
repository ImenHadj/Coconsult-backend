package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.models.Client;
import com.bezkoder.springjwt.notifdto.rempcalendrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClientRep extends JpaRepository<Client,Long> {


    List<Client> findAllByOrderByAmountAsc();

    @Query("SELECT DISTINCT c FROM Client c " + "JOIN FETCH c.factures f " +
            "WHERE f.due_date BETWEEN :today AND :dueDateThreshold")
    List<Client> findClientsWithDueFactures(@Param("today") LocalDateTime today,
                                            @Param("dueDateThreshold") LocalDateTime dueDateThreshold);

    @Query("SELECT DISTINCT new com.bezkoder.springjwt.notifdto.rempcalendrier(c.nom, c.prenom, f.due_date) FROM Client c " +
            "JOIN c.factures f " +
            "WHERE f.due_date BETWEEN :today AND :dueDateThreshold")
    List<rempcalendrier> remplircalendrier(@Param("today") LocalDateTime today,
                                           @Param("dueDateThreshold") LocalDateTime dueDateThreshold);


}
