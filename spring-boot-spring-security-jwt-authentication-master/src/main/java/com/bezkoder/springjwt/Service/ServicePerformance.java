package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.Service.interfaces.ISerivcePerformance;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class ServicePerformance implements ISerivcePerformance {
    @Autowired
    private NoteRepo noteRepository;
    PerfermanceEmplRepo performanceEmployeeRepository;
    EmployeeRepo employeeRepo;

    public List<PerformanceEmployee> getAllPerformances() {
        return performanceEmployeeRepository.findAll();
    }

    public PerformanceEmployee getPerformanceById(Long id) {
        return performanceEmployeeRepository.findById(id).get();
    }

    public PerformanceEmployee savePerformance(PerformanceEmployee performance,Long id) {
        Employee employee = employeeRepo.findById(id).get();
        Set<Note> notes = employee.getNotes();


        float somme = 0.0f;
        for (Note n : notes){
            somme += n.getNote();
        }
        float moyenne = somme/notes.size();
        if (moyenne < 0 || moyenne > 100) {
                throw new IllegalArgumentException("The score must be between 0 and 100.");
        }else{
            performance.setCommentaire(calculatePerformanceGlobale(moyenne));
            performance.setMoyenne(moyenne);
        }
        return performanceEmployeeRepository.save(performance);
    }

    public void deletePerformance(Long id) {
        performanceEmployeeRepository.deleteById(id);
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
    public Map<critereNote, Double> getAverageByCriteria() {
        List<Object[]> averages = noteRepository.findAverageByCriteria();
        Map<critereNote, Double> result = new HashMap<>();
        for (Object[] average : averages) {
            result.put((critereNote) average[0], (Double) average[1]);
        }
        return result;
    }
}
