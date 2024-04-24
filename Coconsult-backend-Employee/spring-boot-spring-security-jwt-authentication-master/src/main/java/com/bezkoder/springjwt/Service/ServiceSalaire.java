package com.bezkoder.springjwt.Service;


import com.bezkoder.springjwt.Service.interfaces.IServiceSalaire;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
@AllArgsConstructor
public class ServiceSalaire implements IServiceSalaire {

    private SalaireEmplRepo salaireEmployeeService;
    EmployeeRepo employeeRepo;
    CongeRepo congeRepo;
    AbsenceRepo absenceRepo;

    @Override
    public void addSalaire(SalaireEmployee salaire, Long employeeId) {

        Employee employee = employeeRepo.findById(employeeId).get();
        Set<ContratEmployee> Contracts = employee.getContratEmployees();
        for(ContratEmployee ce : Contracts){
            if(ce.getIsArchive()){

            }
        }

        Float totalSalaire = calculateTotalSalaire(salaire, employee);
        salaire.setTotal_salaire(totalSalaire);

        List<SalaireEmployee> salaireEmployees = salaireEmployeeService.findAll();

        if (salaireEmployees != null) {
            for (SalaireEmployee se : salaireEmployees) {
                se.setIsArchive(true);
            }
        }
        salaire.setIsArchive(false);
        salaire.setEmploye(employee);
        salaireEmployeeService.save(salaire);
    }

    private Float calculateTotalSalaire(SalaireEmployee salaire, Employee employee) {
        Float totalSalaire = salaire.getSalaire_base() + salaire.getPrime() + salaire.getMontant_heures_supplementaires()*salaire.getHeures_supplementaires();
        Float DeductionConge =calculateTotalHoursOfCongeesInCurrentMonth(employee.getId_employe()) * salaire.getMontant_heures_supplementaires();
        return totalSalaire  - DeductionConge;
    }

    public int calculateTotalHoursOfCongeesInCurrentMonth(Long id) {

        LocalDate startDate = YearMonth.now().atDay(1);
        LocalDate endDate = YearMonth.now().atEndOfMonth();

        List<Conge> congeListinMonth = congeRepo.findByDateBetweenAndEmployeeId(startDate, endDate,id);
        List<Absence> AbsenceListinMonth = absenceRepo.findByDateBetweenAndEmployeeId(startDate, endDate,id);
        int totalDays = 0;

        for (Conge conge : congeListinMonth) {
            long durationMillis = conge.getDate_fin().getTime() - conge.getDate_debut().getTime();
            long durationDays = durationMillis / (1000 * 60 * 60 * 24); // Convert milliseconds to days
            totalDays += durationDays;
        }
        int duration = 8;
        return duration*((1+totalDays)+AbsenceListinMonth.size());
    }

    public int calculateDurationInHours(Date startDate, Date endDate) {
        long startMillis = startDate.getTime();
        long endMillis = endDate.getTime();
        long durationMillis = endMillis - startMillis;
        return (int) TimeUnit.MILLISECONDS.toHours(durationMillis);
    }



    @Override
    public SalaireEmployee updateSalaire(SalaireEmployee salaire,Long id) {
        Employee employee = employeeRepo.findById(id).get();

        Float totalSalaire = calculateTotalSalaire(salaire,employee);
        salaire.setTotal_salaire(totalSalaire);
        salaire.setIsArchive(false);
        return salaireEmployeeService.save(salaire);

    }

    @Override
    public void deleteSalaire(Long id) {
        salaireEmployeeService.deleteById(id);

    }

    @Override
    public SalaireEmployee getSalaire(Long id) {
        return salaireEmployeeService.findById(id).get();

    }

    @Override
    public List<SalaireEmployee> retrieveAll() {
       return salaireEmployeeService.findAll();
    }

    public List<SalaireEmployee> findBySalaireBaseGreaterThan(Float minSalaire) {
        return salaireEmployeeService.findByIsArchiveAndSalaireBaseGreaterThan(false,minSalaire);
    }

    //juste st7a9itha fel fct calculateAverageSalaryByPoste
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
    public Float calculateAverageSalaryByPoste(PosteEmployee posteEmployee) {
        List<Employee> employees = findAllByPosteEmployee(posteEmployee);
        float nb=0;
        for(Employee em : employees) {
            Set<SalaireEmployee> salaireEmployees = em.getSalaireEmployees();
            for (SalaireEmployee se : salaireEmployees) {
                if (se.getIsArchive() == false) {
                    nb += se.getTotal_salaire();
                }
            }

        }
        return nb / employees.size();
    }






}
