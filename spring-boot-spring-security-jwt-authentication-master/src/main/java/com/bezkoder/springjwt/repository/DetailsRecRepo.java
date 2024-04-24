package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.DetailRecrutement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DetailsRecRepo extends JpaRepository<DetailRecrutement,Long > {

    List<DetailRecrutement> findByDateEntretien(LocalDate dateEntretien);

    @Query("SELECT d.dateEntretien, c.nom, c.prenom, r.typeRecrutement " +
            "FROM DetailRecrutement d " +
            "JOIN d.candidat c " +
            "JOIN c.recrutementC r")
    List<Object[]> getRendezVousDetails();
}