package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CandidatRepo extends JpaRepository<Candidat, Long> {
    Candidat getCandByidCandidat (Long idCandidat);


    @Query("SELECT c FROM Candidat c WHERE c.StatutCandidat = 'SELECTIONNE'")
    List<Candidat> findAllSelectionnes();


    @Query("SELECT c.email FROM Candidat c WHERE c.idCandidat = :candidatId")
    List<String> findEmailsByIdCandidat(Long candidatId);


    @Query("SELECT c FROM Candidat c WHERE c.StatutCandidat = 'EN_ATTENTE' AND c.Datepostule <= :dateLimite")
    List<Candidat> findAllSelectionnesPlusDeDeuxJours(LocalDate dateLimite);

}