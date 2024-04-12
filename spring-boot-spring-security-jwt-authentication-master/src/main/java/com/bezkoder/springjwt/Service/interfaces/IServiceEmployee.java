package com.bezkoder.springjwt.Service.interfaces;

import com.bezkoder.springjwt.models.Employee;
import com.bezkoder.springjwt.models.PosteEmployee;
import com.bezkoder.springjwt.models.User;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IServiceEmployee {
    public ResponseEntity<?> calculateAvailablePercentage();
    public ResponseEntity<Double> calculateNbreEmpl();
    public void addEmployeeEtAffectDepartement(Employee employee,Long id);
    public ResponseEntity<Long> updateEmployee(Long id, Employee updatedEmployee,Long p);
    public void deleteEmployee(Long id);
    public Employee getEmployee(Long id);
    public User getUser(Long id);
    List<Employee> retrieveAll();
    public Employee assignEmToEquipe(Long idE,Long idEquipe);
    public List<Employee> findAllByPosteEmployee(PosteEmployee posteEmployee);
    public Employee assignEmToDep(Long idE,Long idDep);
    public List<Employee> findByPosteEmployeeStartingWith(String StartingLetter);

}
