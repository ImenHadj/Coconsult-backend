package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.Service.interfaces.IServiceEmployee;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class ServiceEmployee implements IServiceEmployee {
    EmployeeRepo employeeRepo;
    DepartementRepo departementRepo;
    TeamRepo teamRepo;
    UserRepository userRepo;
    CongeRepo congeRepo;
    ContratEmplRepo contratEmplRepo;
    NoteRepo noteRepo;
    AbsenceRepo absenceRepo;
    TeamRepository teamRepository;

    @Override
    public void addEmployeeEtAffectDepartement(Employee employee,Long id) {
        Departement departement = departementRepo.findById(id).get();

       int max = departement.getMaxSaturation();
       int saturation = departement.getNbreEmpl();
       if(max<=saturation ){

           log.info("vous atteint le max");
       }else{
           employee.setDepartement(departement);
           departement.setNbreEmpl(saturation+1);
           employeeRepo.save(employee);
           departementRepo.save(departement);
       }
    }

    @Override
    public ResponseEntity<Long> updateEmployee(Long id, Employee updatedEmployee, Long departementId, Long teamId) {
        // Récupérer l'employé à partir de l'ID
        Employee emp = employeeRepo.findById(id).orElse(null);
        if (emp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id);
        }

        // Récupérer le département à partir de l'ID
        Departement depart = departementRepo.findById(departementId).orElse(null);
        if (depart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(departementId);
        }

        // Vérifier la saturation du département
        int max = depart.getMaxSaturation();
        int saturation = depart.getNbreEmpl();
        if (max <= saturation) {
            log.error("Validation failed for Employee update. ID: {} - Department is saturated.", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(id);
        }

        // Récupérer l'équipe à partir de l'ID
        Team team = teamRepository.findById(teamId).orElse(null);
        if (team == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(teamId);
        }

        // Mettre à jour les attributs de l'employé
        emp.setDepartement(depart);
        emp.setPosteEmployee(updatedEmployee.getPosteEmployee());
        emp.setDate_embauche(updatedEmployee.getDate_embauche());
        emp.setTeams(team); // Affecter l'équipe à l'employé

        // Mettre à jour le nombre d'employés dans le département
        depart.setNbreEmpl(saturation + 1);

        // Sauvegarder les modifications
        employeeRepo.save(emp);
        departementRepo.save(depart);

        return ResponseEntity.ok(id);
    }


    @Override
    public void deleteEmployee(Long id) {
        Employee emp = employeeRepo.findById(id).get();
        Set<Conge> anis = emp.getConges();
        Set<ContratEmployee> noussa= emp.getContratEmployees();
        Set<Note> anas= emp.getNotes();
        Set<Absence> abs= emp.getAbsences();
        for(Absence cc : abs){
            cc.setEmp(null);
            absenceRepo.save(cc);
        }
        for(Conge cc : anis){
            cc.setEmployee(null);
            congeRepo.save(cc);
        }
        for(ContratEmployee cc : noussa){
            cc.setEmpl(null);
            contratEmplRepo.save(cc);
        }
        for(Note cc : anas){
            cc.setEmployeee(null);
            noteRepo.save(cc);
        }

        employeeRepo.deleteById(id);
    }

    @Override
    public Employee getEmployee(Long id) {
        return employeeRepo.findById(id).get();
    }

    @Override
    public User getUser(Long id) {
        return userRepo.findById(id).get();
    }

    @Override
    public List<Employee> retrieveAll() {
        return employeeRepo.findAll();
    }

    public List<Employee> findAllByPosteEmployee(PosteEmployee posteEmployee) {
        List<Employee> anis = new ArrayList<>();
        List<Employee> noussa = employeeRepo.findAll();
        for(Employee em : noussa){
            if(em.getPosteEmployee().equals(posteEmployee)){
                anis.add(em);
            }

        }
        return anis;
    }

    public Employee assignEmToDep(Long idE,Long idDep){
        Employee e = employeeRepo.findById(idE).orElse(null);
        Departement d = departementRepo.findById(idDep).orElse(null);
        e.setDepartement(d);
        return  employeeRepo.save(e);
    }
    public Employee assignEmToEquipe(Long idE,Long idEquipe){
        Employee e = employeeRepo.findById(idE).orElse(null);
//        Team eq  = teemRepo.findById(idEquipe).orElse(null);
//            e.setTeam(eq);
        return  employeeRepo.save(e);
    }

    @Scheduled(cron = "0 0 0 1 1 *")
    //    @Scheduled(fixedDelay = 10000)
    public void retrieveAndUpdateStatusNbreJourConge() {
        List<Employee> employees = retrieveAll();
        for (int d = 0; d < employees.size(); d++) {
            Employee S = employees.get(d);
            S.setNbrJourConge(26);
            employeeRepo.save(S);
        }
    }

    public ResponseEntity<Double> calculateNbreEmpl() {
        List<Employee> employees = employeeRepo.findAll();
        System.out.println("Total Max Saturation: " + employees.size());
        return ResponseEntity.ok((double) employees.size()); // Calculate percentage
    }

    public ResponseEntity<?> calculateAvailablePercentage() {
        List<Employee> employees = employeeRepo.findAll();
        double anis=0;
        double total = employees.size();
        for(Employee employee : employees){
            if(employee.getPerformanceEmployee()!=null){
                if(employee.getPerformanceEmployee().getMoyenne()>3){
                    anis+=1;
                }
            }else{
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "There's some employees have no notes");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);            }

        }
        System.out.println("Total Max Saturation: " + anis);
        return ResponseEntity.ok((double) anis); // Calculate percentage
    }
    public List<Employee> findByPosteEmployeeStartingWith(String StartingLetter) {
        return employeeRepo.findByDepartementLibelleStartingWith(StartingLetter);
    }


    public void assignTeamToEmployee(Long employeeId, Long teamId) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé avec l'ID: " + employeeId));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Équipe non trouvée avec l'ID: " + teamId));

        // Check if team has reached its maximum capacity
        int maxCapacity = (int) team.getNbteam();
        int currentCapacity = team.getEmployees().size();
        if (currentCapacity >= maxCapacity) {
            throw new RuntimeException("L'équipe a atteint sa capacité maximale.");
        }

        // Remove the employee from their current team, if any
        if (employee.getTeams() != null) {
            employee.getTeams().getEmployees().remove(employee);
        }

        // Assign the employee to the new team
        employee.setTeams(team);
        team.getEmployees().add(employee);

        employeeRepo.save(employee);
        teamRepository.save(team);
    }





    }
