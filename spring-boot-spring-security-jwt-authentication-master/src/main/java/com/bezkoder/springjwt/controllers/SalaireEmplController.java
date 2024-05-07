package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.Service.interfaces.IServiceSalaire;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/SalaireEmpl")
public class SalaireEmplController {
    IServiceSalaire iServiceSalaire;
    @PostMapping("/addpSalaireEmpl/{id}")
    public ResponseEntity<?> addSalaire(@RequestBody SalaireEmployee p, @PathVariable ("id") Long id){
     return   iServiceSalaire.addSalaire(p,id);
    }
    @PutMapping("/updateSalaire/{id}")
    public SalaireEmployee updateSalaire(@RequestBody SalaireEmployee p, @PathVariable ("id") Long id) throws Exception {
        return iServiceSalaire.updateSalaire(p,id);
    }
    @DeleteMapping("/deleteSalaire/{p}")
    public void deleteSalaire(@PathVariable("p") Long p) {
        iServiceSalaire.deleteSalaire(p);
    }
    @GetMapping("/getSalaire/{id}")
    public SalaireEmployee getSalaire(@PathVariable("id") Long id){
        return iServiceSalaire.getSalaire(id);
    }
    @GetMapping("/retrieveAll")
    public List<SalaireEmployee> retrieveAll(){
        return iServiceSalaire.retrieveAll();
    }

//    @GetMapping("/findBySalaireBaseGreaterThan/{minSalaire}")
//    public List<SalaireEmployee> retrieveAll(@PathVariable("minSalaire") Float minSalaire){
//        return iServiceSalaire.findBySalaireBaseGreaterThan(minSalaire);
//    }
    @GetMapping("/calculateAverageSalaryByPoste/{posteEmployee}")
    public Float calculateAverageSalaryByPoste(@PathVariable("posteEmployee") PosteEmployee posteEmployee){
        return iServiceSalaire.calculateAverageSalaryByPoste(posteEmployee);
    }
    @GetMapping("/generateMonthlySalaryReport/{year}/{month}")
    public ResponseEntity<?> generateMonthlySalaryReport(@PathVariable("year") int year,
                                                         @PathVariable("month") int month   ){
        return iServiceSalaire.generateMonthlySalaryReport(year,month);
    }
    @GetMapping("/generateSalaryStatistics/{year}/{month}")
    public ResponseEntity<?> generateSalaryStatistics(@PathVariable("year") int year,
                                                      @PathVariable("month") int month   ){
        return iServiceSalaire.generateSalaryStatistics(year,month);
    }
    @GetMapping("/getTotalSalariesEvolution")
    public ResponseEntity<?> getTotalSalariesEvolution(){
        return iServiceSalaire.getTotalSalariesEvolution();
    }
}
