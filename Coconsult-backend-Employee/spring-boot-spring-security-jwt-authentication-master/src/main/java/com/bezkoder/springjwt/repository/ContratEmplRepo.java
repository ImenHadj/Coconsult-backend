package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.models.ContratEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ContratEmplRepo extends JpaRepository<ContratEmployee,Long> {
    @Query("SELECT COUNT(e) FROM ContratEmployee e WHERE e.isArchive = false AND e.date_debut BETWEEN :startDate AND :endDate")
    Integer countByIsArchiveIsFalseAndDateDebutBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
     /*
    	@Query("Select "
			+ "DISTINCT p from Patient p "
			+ "join p.medecins med "
			+ "join med.clinique cli "
			+ "where cli=:clinique")
    public List<Patient> getAllPatientByClinique(@Param("clinique") Clinique clinique);

	@Query("SELECT count(*) FROM Patient p join p.medecins m WHERE m.poste=:poste")
	public Long getNombrePatientsExaminesParMedecinActionaire(@Param("poste")Poste poste) ;

     @Query("SELECT c FROM Chaine c " +
"            	"join c.programmes p "  +
"               "join p.utilisateurs u \ " +
"               "WHERE SIZE(p.utilisateurs) > 0 " +
"               "GROUP BY c \ " +
"               "ORDER BY COUNT(p) DESC")
			public void ordonnerChaines();

     */
    //    List<Reservation> findByAnneeUniversitaireBetween(Date dateDebut , Date dateFin);
    //    long countByTypeCAndBlocIdBloc(TypeChambre Typec, long idBloc);
    /* @Query("SELECT p.chaine, count(p) AS cp FROM Programme p " +
            "INNER JOIN p.utilisateurs u " +
            "WHERE u.usrId IS NOT NULL " +
            "GROUP BY p.chaine ORDER BY cp DESC")
    List<Object[]> listerchaines();*/
    /* @Query("SELECT distinct (p) FROM Programme p " +
            "INNER JOIN p.utilisateurs u " +
            "WHERE u.profession = :p ")
    List<Programme> listerProgrammesInteressants(@Param("p") Profession p);*/
}
