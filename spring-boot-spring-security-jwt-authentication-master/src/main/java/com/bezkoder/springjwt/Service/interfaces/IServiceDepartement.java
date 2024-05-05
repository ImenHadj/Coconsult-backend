package com.bezkoder.springjwt.Service.interfaces;

import com.bezkoder.springjwt.models.Departement;
import com.bezkoder.springjwt.models.Employee;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface IServiceDepartement {
    public ResponseEntity<Double> calculateAvailablePercentage();
    public ResponseEntity<Double> calculateMax();
    long addDepartment(Departement departement);
    void removeDepartment (Long idDepartment);
    Departement retrieveDepartment(Long idDepartment) ;
    List<Departement> retrieveAllDepartments();

    Set<Employee> retrieveEmployeesByDepartement(Long idDepartment);
    Departement updateDepartment(Long id,Departement departement);
    public  ResponseEntity<?> affecterEmplADep(Set<Employee> ListEmpls,Long idDepartment);
    public List<Departement> searchUsersByEmailStartingWithLetter(String startingLetter);

}
