package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.Service.interfaces.ISerivceNote;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class ServiceNote implements ISerivceNote {

    NoteRepo noteRepo;
    EmployeeRepo employeeRepo;
    PerfermanceEmplRepo perfermanceEmplRepo;

    @Override
    public void addNote(Note note, Long id) {
        Employee employee = employeeRepo.findById(id).get();
        note.setEmployeee(employee);
        noteRepo.save(note);
    }
    public void addPerfermance(Long id){
        Employee employee = employeeRepo.findById(id).get();
        PerformanceEmployee performanceEmployee;
        if(employee.getPerformanceEmployee()!=null){
             performanceEmployee = employee.getPerformanceEmployee();
        }else
        {
             performanceEmployee= PerformanceEmployee.builder().build();
        }

        Set<Note> notes = employee.getNotes();
        float sum = 0.0f;

        for (Note n : notes) {
            sum += n.getNote();
        }
        float moyenne = 0.0f;

         moyenne = sum / notes.size();


        if (moyenne < 0 || moyenne > 100) {
            throw new IllegalArgumentException("The score must be between 0 and 100.");
        } else {
            performanceEmployee.setCommentaire(calculatePerformanceGlobale(moyenne));
            performanceEmployee.setMoyenne(moyenne);
        }

        perfermanceEmplRepo.save(performanceEmployee);
        employee.setPerformanceEmployee(performanceEmployee);
        employeeRepo.save(employee);
    }

    private String calculatePerformanceGlobale(Float note) {
        if (note < 2) {
            return "Bad";
        } else if (note >= 2 && note < 4) {
            return "Average";
        } else {
            return "Good";
        }
    }


    @Override
    public Note updateNote(Long id,Note updatedNote) {
        Note note = noteRepo.findById(id).get();
        note.setNote(updatedNote.getNote());
       return noteRepo.save(note);
    }

    @Override
    public void deleteNote(Long id) {
        noteRepo.deleteById(id);

    }

    @Override
    public Note getNote(Long id) {
        return noteRepo.findById(id).get();
    }

    @Override
    public List<Note> retrieveAll() {
        return noteRepo.findAll();
    }
}
