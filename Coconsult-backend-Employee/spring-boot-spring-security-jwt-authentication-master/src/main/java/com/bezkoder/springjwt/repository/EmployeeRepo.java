package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee,Long> {
//    List<Employee> findByCommentaireStartingWithOrJustificationStartingWith(String commentaireStartingLetter, String justificationStartingLetter);
List<Employee> findByDepartementLibelleStartingWith(String posteEmployee);


//    Employee findByPosteEmployee(PosteEmployee posteEmployee);
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
