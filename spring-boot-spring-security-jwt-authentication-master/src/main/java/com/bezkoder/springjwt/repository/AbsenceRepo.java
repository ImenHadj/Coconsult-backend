package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AbsenceRepo extends JpaRepository<Absence,Long> {
    List<Absence> findByDate(LocalDate date);
    List<Absence> findByMotifStartingWith(String commentaireStartingLetter);


    @Query(value = "select * from absence order by date desc", nativeQuery = true)
    public List<Absence> getAbsenceDueToDate();

    @Query(value = "SELECT * FROM absence WHERE MONTH(date) = MONTH(CURRENT_DATE()) AND YEAR(date) = YEAR(CURRENT_DATE())", nativeQuery = true)
    List<Absence> findAbsencesForCurrentMonth();

    @Query("SELECT a FROM Absence a WHERE " +
            "a.emp.id_employe = :employeeId AND " +
            "a.validee = true AND " +
            "((a.date BETWEEN :start AND :end))")
    List<Absence> findByDateBetweenAndEmployeeId(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("employeeId") Long employeeId);

}
