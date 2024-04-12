package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.Service.interfaces.IServiceSalaire;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/SalaireEmpl")
public class SalaireEmplController {
    IServiceSalaire iServiceSalaire;
    @PostMapping("/addpSalaireEmpl/{id}")
    public void addSalaire(@RequestBody SalaireEmployee p, @PathVariable ("id") Long id){
        iServiceSalaire.addSalaire(p,id);
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

    @GetMapping("/findBySalaireBaseGreaterThan/{minSalaire}")
    public List<SalaireEmployee> retrieveAll(@PathVariable("minSalaire") Float minSalaire){
        return iServiceSalaire.findBySalaireBaseGreaterThan(minSalaire);
    }
    @GetMapping("/calculateAverageSalaryByPoste/{posteEmployee}")
    public Float calculateAverageSalaryByPoste(@PathVariable("posteEmployee") PosteEmployee posteEmployee){
        return iServiceSalaire.calculateAverageSalaryByPoste(posteEmployee);
    }
}
