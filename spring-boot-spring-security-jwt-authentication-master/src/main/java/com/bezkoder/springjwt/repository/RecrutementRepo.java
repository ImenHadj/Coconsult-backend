package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Recrutement;
import com.bezkoder.springjwt.models.StatutRecrut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecrutementRepo  extends JpaRepository<Recrutement, Long> {


    public List<Recrutement> findByPoste(String poste);

    @Query("SELECT r FROM Recrutement r WHERE r.statutRecrut = :statut")
    List<Recrutement> findAllByStatutRecrutement(@Param("statut") StatutRecrut statut);

    @Query("SELECT r.poste, COUNT(c.idCandidat) FROM Recrutement r JOIN r.candidats c GROUP BY r.poste")
    List<Object[]> findNombreCandidatsParPoste();




    @Query("SELECT r.poste, COUNT(c.idCandidat) " +
            "FROM Recrutement r JOIN r.candidats c " +
            "WHERE c.StatutCandidat = com.bezkoder.springjwt.models.StatutCandidat.SELECTIONNE " +
            "AND c.experience = com.bezkoder.springjwt.models.Experience.PRO " +
            "GROUP BY r.poste")
    List<Object[]> getCandidatsAcceptesParPosteParExperience();

/*
    @Query("SELECT r.poste, c.experience, COUNT(c.idCandidat) " +
            "FROM Recrutement r JOIN r.candidats c " +
            "WHERE c.StatutCandidat =com.bezkoder.springjwt.models.StatutCandidat.SELECTIONNE " +
            "AND c.experience IN ( com.bezkoder.springjwt.models.Experience.DEBUTANT, " +
            " com.bezkoder.springjwt.models.Experience.JUNIOR, " +
            " com.bezkoder.springjwt.models.Experience.PRO, " +
            " com.bezkoder.springjwt.models.Experience.EXPERT) " +
            "GROUP BY r.poste, c.experience")
    List<Object[]> getCandidatsAcceptesParPosteParExperience();
*/

}
