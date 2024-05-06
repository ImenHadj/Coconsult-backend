package com.bezkoder.springjwt.controllers;


import com.bezkoder.springjwt.Service.interfaces.IServiceEmployee;
import com.bezkoder.springjwt.models.*;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
    IServiceEmployee iServiceEmployee;

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> findByPosteEmployeeStartingWith(
            @RequestParam String startingLetter) {
        List<Employee> matchingConges = iServiceEmployee.findByPosteEmployeeStartingWith(startingLetter);
        return ResponseEntity.ok(matchingConges);
    }
    @GetMapping("/moyennedeperf")
    public ResponseEntity<?> getAvailablePercentage() {
        return  iServiceEmployee.calculateAvailablePercentage();

    }
    @GetMapping("/calculateNbreEmpl")
    public ResponseEntity<Double> calculateNbreEmpl() {
        return  iServiceEmployee.calculateNbreEmpl();

    }
    @PostMapping("/addEmployee/{id}/{idT}")
    public void addEmployeeEtAffectDepartement(@RequestBody Employee p,@PathVariable("id") Long id,@PathVariable("idT") Long idT){
        iServiceEmployee.addEmployeeEtAffectDepartement(p,id,idT);
    }
    @PostMapping("/assignEmToDep/{e}/{d}")
    public void assignEmToDep(@PathVariable("e") Long e,@PathVariable("d") Long d){
        iServiceEmployee.assignEmToDep(e,d);
    }

    @PutMapping("/updateEmployee/{e}/{pp}/{T}")
    public ResponseEntity<Long> updateEmployee(@PathVariable("e") Long e, @RequestBody Employee p,@PathVariable("pp") Long pp,@PathVariable("T") Long T) {
        return iServiceEmployee.updateEmployee(e,p,pp,T);
    }

    @DeleteMapping("/DeleteEmployee/{p}")
    public void delete(@PathVariable("p") Long p) {
        iServiceEmployee.deleteEmployee(p);
    }

    @GetMapping("/retrieveAll")
    public List<Employee> retrieveAll(){
        return iServiceEmployee.retrieveAll();
    }

    @GetMapping("/getEmployee/{p}")
        public Employee getEmployee(@PathVariable("p") Long p){
        return iServiceEmployee.getEmployee(p);
    }
    @GetMapping("/getUser/{p}")
    public User getUser(@PathVariable("p") Long p){
        return iServiceEmployee.getUser(p);
    }

    @GetMapping("/findAllByPosteEmployee/{posteEmployee}")
    public List<Employee> findAllByPosteEmployee(@PathVariable("posteEmployee") PosteEmployee posteEmployee){
        return iServiceEmployee.findAllByPosteEmployee(posteEmployee);
    }

//    @PutMapping("/{employeeId}/assign-team/{teamId}")
//    public void assignTeamToEmployee(@PathVariable Long employeeId, @PathVariable Long teamId) {
//        iServiceEmployee.assignTeamToEmployee(employeeId, teamId);
//    }



}
