package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.interfaces.IServiceDepartement;
import com.bezkoder.springjwt.models.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/departement")
@CrossOrigin(origins = "http://localhost:4200")

public class DepartementController {
    IServiceDepartement iServiceDepartement;

    @GetMapping("/availablePercentage")
    public ResponseEntity<Double> getAvailablePercentage() {
        return  iServiceDepartement.calculateAvailablePercentage();

    }
    @GetMapping("/max")
    public ResponseEntity<Double> getAMax() {
        return  iServiceDepartement.calculateMax();

    }
    @GetMapping("/search")
    public ResponseEntity<List<Departement>> searchUsersByEmailStartingWithLetter(
            @RequestParam String startingLetter) {
        List<Departement> matchingUsers = iServiceDepartement.searchUsersByEmailStartingWithLetter(startingLetter);
        return ResponseEntity.ok(matchingUsers);
    }
    @PostMapping("/addDepartment")
    public long add(@RequestBody Departement p){
        return iServiceDepartement.addDepartment(p);
    }
    @PutMapping("/updateDepartment/{id}")
    public Departement updateDepartement(@PathVariable("id") Long id,@RequestBody Departement p) throws Exception {
        return iServiceDepartement.updateDepartment(id,p);
    }
    @PutMapping("/affecterEmplADep/{idD}")
    public void affecterEmplADep(@RequestBody Set<Employee> p,@PathVariable("idD") Long idD) {
         iServiceDepartement.affecterEmplADep(p,idD);
    }
    @DeleteMapping("/deleteDepartment/{p}")
    public void removeDepartment(@PathVariable("p") Long p) {
        iServiceDepartement.removeDepartment(p);
    }
    @GetMapping("/getDepartment/{id}")
    public Departement retrieveDepartment(@PathVariable("id") Long id){
        return iServiceDepartement.retrieveDepartment(id);
    }
    @GetMapping("/getAllDepartments")
    public List<Departement> retrieveAllDepartments(){
        return iServiceDepartement.retrieveAllDepartments();
    }

    @GetMapping("/retrieveEmployeesByDepartement/{id}")
    public Set<Employee> retrieveEmployeesByDepartement(@PathVariable("id") Long id){
        return iServiceDepartement.retrieveEmployeesByDepartement(id);
    }
}
