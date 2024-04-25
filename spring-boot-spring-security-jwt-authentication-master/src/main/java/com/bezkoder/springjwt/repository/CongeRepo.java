package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.models.Conge;
import com.bezkoder.springjwt.models.PosteEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface CongeRepo extends JpaRepository<Conge,Long> {


    @Query("SELECT c FROM Conge c WHERE " +
            "c.employee.teams.team_id = :teamId AND " +
            "c.employee.PosteEmployee = :poste AND " +
            "((c.date_debut BETWEEN :start AND :end) OR " +
            "(c.date_fin BETWEEN :start AND :end) OR " +
            "(:start BETWEEN c.date_debut AND c.date_fin) OR " +
            "(:end BETWEEN c.date_debut AND c.date_fin))")
    List<Conge> findCongeInSamePeriodAndSameTeam(
            @Param("teamId") Long teamId,
            @Param("poste") PosteEmployee poste,
            @Param("start") Date start,
            @Param("end") Date end);
    @Query("SELECT c FROM Conge c WHERE " +
            "c.employee.id_employe = :employeeId AND " +
            "c.typeC = 'PAID' AND " +
            "c.statutC = 'VALIDATED' AND " +
            "((c.date_debut BETWEEN :start AND :end) OR " +
            "(c.date_fin BETWEEN :start AND :end) OR " +
            "(:start BETWEEN c.date_debut AND c.date_fin) OR " +
            "(:end BETWEEN c.date_debut AND c.date_fin))")
    List<Conge> findByDateBetweenAndEmployeeId(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("employeeId") Long employeeId);


//    List<Conge> findByCommentaireStartingWithOrJustificationStartingWith(String commentaireStartingLetter, String justificationStartingLetter);
   List<Conge> findByCommentaireStartingWith(String commentaireStartingLetter);
//List<Conge> findByCommentaireStartingWithOrTypeCStartingWith(String commentaireStartingLetter, String typeStartingLetter);

    List<Conge> findByStatutC(String statutC);
}
