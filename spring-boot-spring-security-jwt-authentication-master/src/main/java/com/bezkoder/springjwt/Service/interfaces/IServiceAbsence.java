package com.bezkoder.springjwt.Service.interfaces;

import com.bezkoder.springjwt.models.Absence;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface IServiceAbsence {
    public ResponseEntity<Long> addAbsence(Absence absence, Long id);
    public List<Absence> searchAbsenceByStartingLetters(String StartingLetter);
    public void updateAbsence(Long absence, Absence updatedAbsence);
    public void deleteAbsence(Long id);
    public Set<Absence> getAbsencesByEmp(Long id);
    public List<Absence> retrieveAbsencesForToday();
    public List<Absence> retrieveAll();
    public Absence getAbsence(Long id);

}
