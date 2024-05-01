package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.interfaces.IServiceConge;
import com.bezkoder.springjwt.models.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/Conge")
@CrossOrigin(origins = "http://localhost:4200")

public class congeController {
    IServiceConge iServiceConge;
    @GetMapping("/dashBoard")
    public Map<CongeType, Integer> calculerNombreCongesParType(){
        return iServiceConge.calculerNombreCongesParType();
    }
    @GetMapping("/getConges/{id}")
    public Set<Conge> getCongesByEmp(@PathVariable("id") Long id){
        return iServiceConge.getCongesByEmp(id);
    }
    @GetMapping("/filterByStatus")
    public ResponseEntity<List<Conge>> filterByStatus(
            @RequestParam CongeStatut startingLetter) {
        List<Conge> matchingConges = iServiceConge.filterByStatus(startingLetter);
        return ResponseEntity.ok(matchingConges);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Conge>> searchCongesStartingWithLetter(
            @RequestParam String startingLetter) {
        List<Conge> matchingConges = iServiceConge.searchCongesByStartingLetters(startingLetter);
        return ResponseEntity.ok(matchingConges);
    }
    @PostMapping("/saveConge/{id}")
    public ResponseEntity<?> saveCongeAffectAEmpl(@RequestBody Conge p, @PathVariable ("id") Long id){
        return iServiceConge.saveConge(p,id);
    }

    @PostMapping("/SendEmailConge/{id}")
    public ResponseEntity<?> SendEmailConge(@PathVariable ("id") Long id,@RequestBody Conge p){
        return iServiceConge.SendEmailConge(id,p);
    }


    @PutMapping("/updateConge/{id}")
    public ResponseEntity<?> updateConge(@PathVariable ("id") Long id,@RequestBody Conge p) throws Exception {
        return iServiceConge.updateConge(id,p);
    }
    @DeleteMapping("/deleteConge/{p}")
    public void delete(@PathVariable("p") Long p) {
        iServiceConge.deleteConge(p);
    }
    @GetMapping("/getConge/{id}")
    public Conge getConge(@PathVariable("id") Long id){
        return iServiceConge.getConge(id);
    }
    @GetMapping("/retrieveAll")
    public List<Conge> retrieveAll(){
        return iServiceConge.retrieveAll();
    }
}
