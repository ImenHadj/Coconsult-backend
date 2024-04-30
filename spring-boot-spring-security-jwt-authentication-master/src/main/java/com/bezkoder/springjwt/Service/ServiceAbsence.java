package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.Service.interfaces.IServiceAbsence;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class ServiceAbsence implements IServiceAbsence {

    AbsenceRepo absenceRepo;
    EmployeeRepo employeeRepo;
    UserRepository userRepository;

    @Override
    public ResponseEntity<Long> addAbsence(Absence absence, Long id) {
        Employee emp = employeeRepo.findById(id).get();
        absence.setEmp(emp);
        absenceRepo.save(absence);
        return ResponseEntity.ok( absence.getId_absence());

    }

    public List<Absence> searchAbsenceByStartingLetters(String StartingLetter) {
//        return congeRepo.findByCommentaireStartingWithOrJustificationStartingWith(StartingLetter, StartingLetter);

        return absenceRepo.findByMotifStartingWith(StartingLetter);

    }
    public ResponseEntity<?> a3tiniEsm(Absence absence){
        Long id = absence.getEmp().getUserId();
        User user = userRepository.findById(id).orElse(null); // Use findById().orElse(null) to handle the case where the user may not exist
        if (user != null) {
            return ResponseEntity.ok(user.getUsername());
        } else {
            return ResponseEntity.notFound().build(); // If the user is not found, return a not found response
        }
    }

    @Override
    public void updateAbsence(Long id, Absence updatedAbsence) {

        Absence existingAbsence = absenceRepo.findById(id).get();

        existingAbsence.setMotif(updatedAbsence.getMotif());
//        existingAbsence.setJustification(updatedAbsence.getJustification());
        existingAbsence.setDate(updatedAbsence.getDate());
        existingAbsence.setValidee(updatedAbsence.isValidee());

        absenceRepo.save(existingAbsence);
    }

    @Override
    public void deleteAbsence(Long id) {
    absenceRepo.deleteById(id);
    }

    @Override
    public Set<Absence> getAbsencesByEmp(Long id) {
        Employee emp = employeeRepo.findById(id).get();
        return emp.getAbsences();
    }
    @Override
    public Absence getAbsence(Long id) {
        return absenceRepo.findById(id).get();
    }


    public List<Absence> retrieveAbsencesForToday() {
        LocalDate currentDate = LocalDate.now();  // Current local date
        return absenceRepo.findByDate(currentDate);
    }
    public List<Absence> retrieveAll() {
        return absenceRepo.findAll();
    }

}
