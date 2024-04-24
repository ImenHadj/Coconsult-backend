package com.bezkoder.springjwt.Service;


import com.bezkoder.springjwt.Service.interfaces.IServiceContratEmpl;
import com.bezkoder.springjwt.Service.interfaces.IServiceSalaire;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
@AllArgsConstructor
public class ServiceSalaire implements IServiceSalaire {

    private SalaireEmplRepo salaireEmployeeService;
    EmployeeRepo employeeRepo;
    CongeRepo congeRepo;
    AbsenceRepo absenceRepo;
    IServiceContratEmpl iServiceContratEmpl;


    @Override
    public ResponseEntity<?> addSalaire(SalaireEmployee salaire, Long employeeId) {

        Employee employee = employeeRepo.findById(employeeId).get();
        if (isSalaryAlreadyAddedForCurrentMonth(employee)) {


            Set<ContratEmployee> Contracts = employee.getContratEmployees();
            for (ContratEmployee ce : Contracts) {
                if (!ce.getIsArchive()) {
                    ContratEmployee ContraEmp = ce;
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
            salaire.setDate(LocalDate.now());
            salaireEmployeeService.save(salaire);
            return ResponseEntity.ok( salaire.getId_salaire());
        }else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid Salary request. Please check your inputs.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    private boolean isSalaryAlreadyAddedForCurrentMonth(Employee employee) {
        YearMonth currentMonth = YearMonth.now();
        LocalDate startDate = currentMonth.atDay(1);
        LocalDate endDate = currentMonth.atEndOfMonth();
        List<SalaireEmployee> salaries = salaireEmployeeService.findByEmployeAndDateBetween(employee, startDate, endDate);
        return salaries.isEmpty();
    }
    private Float calculateTotalSalaire(SalaireEmployee salaire, Employee employee) {
        Set<ContratEmployee> Contracts = employee.getContratEmployees();
        ContratEmployee ContraEmp = null;
        for (ContratEmployee ce : Contracts) {
            if (!ce.getIsArchive()) {
                ContraEmp = ce;
            }
        }
        Float totalSalaire = ContraEmp.getSalaire_base() + salaire.getPrime() + ContraEmp.getMontant_heures_supplementaires() * salaire.getHeures_supplementaires();
        Float DeductionConge = calculateTotalHoursOfCongeesInCurrentMonth(employee.getId_employe()) * ContraEmp.getMontant_Conge_Absence();
        return totalSalaire - DeductionConge;
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

//    public List<SalaireEmployee> findBySalaireBaseGreaterThan(Float minSalaire) {
//        return salaireEmployeeService.findByIsArchiveAndSalaireBaseGreaterThan(false,minSalaire);
//    }

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

    public ResponseEntity<?> generateMonthlySalaryReport(int year, int month) {
        YearMonth selectedMonth = YearMonth.of(year, month);
        LocalDate startDate = selectedMonth.atDay(1);
        LocalDate endDate = selectedMonth.atEndOfMonth();

        List<SalaireEmployee> salaries = salaireEmployeeService.findByDateBetween(startDate, endDate);
        if (salaries.isEmpty()) {
            return ResponseEntity.ok("No salary records found for the specified month.");
        }

        Map<String, Map<String, Object>> report = new HashMap<>();

        for (SalaireEmployee salary : salaries) {
            Employee employee = salary.getEmploye();

            String employeeName = String.valueOf(employee.getPosteEmployee()); // Use employee name as the key
            if (!report.containsKey(employeeName)) {
                Map<String, Object> employeeDetails = new HashMap<>();
                employeeDetails.put("Position", employee.getPosteEmployee());
                employeeDetails.put("TotalSalary", 0.0);
                employeeDetails.put("Salary Details", new HashMap<>());
                report.put(employeeName, employeeDetails);
            }
            Map<String, Object> employeeDetails = report.get(employeeName);
            double totalSalary = (double) employeeDetails.get("TotalSalary");
            totalSalary += salary.getTotal_salaire();
            employeeDetails.put("TotalSalary", totalSalary);

            Map<String, Double> components = (Map<String, Double>) employeeDetails.get("Salary Details");
            components.put("TotalSalaire", components.getOrDefault("BaseSalary", 0.0) + salary.getTotal_salaire());
            components.put("Bonuses", components.getOrDefault("Bonuses", 0.0) + salary.getPrime());
            components.put("Supplement Hour", components.getOrDefault("Supplement Hour", 0.0) + salary.getHeures_supplementaires());
        }

        return ResponseEntity.ok(report);
    }

    @Scheduled(cron = "0 0 0 1 1 ?")
    public ResponseEntity<?> incrementSalaryForEmployees() {
        float incrementAmount =0;
        float currentSalary=0;
        List<Employee> employees = employeeRepo.findAll();
        for (Employee employee : employees) {
            for (ContratEmployee contratEmployee : employee.getContratEmployees()) {
                if (!contratEmployee.getIsArchive()) {
                    currentSalary += contratEmployee.getSalaire_base();
                    incrementAmount = currentSalary * (contratEmployee.getPourcentage() / 100);
                }
            }

            float newSalary = currentSalary + incrementAmount;

            updateEmployeeSalary(employee.getId_employe(), newSalary);
        }
        return ResponseEntity.ok("Salary incremented for all employees.");
    }
    private void updateEmployeeSalary(Long employeeId, float newSalary) {
        Employee employee = employeeRepo.findById(employeeId).orElse(null);

        for (ContratEmployee contratEmployee : employee.getContratEmployees()) {
            if (!contratEmployee.getIsArchive()) {
                contratEmployee.setSalaire_base(newSalary);
                iServiceContratEmpl.updateContratEmployee(contratEmployee,employeeId);
            }
        }
    }

    public ResponseEntity<?> generateSalaryStatistics(int year, int month) {
        YearMonth selectedMonth = YearMonth.of(year, month);
        LocalDate startDate = selectedMonth.atDay(1);
        LocalDate endDate = selectedMonth.atEndOfMonth();

        List<SalaireEmployee> salaries = salaireEmployeeService.findByDateBetween(startDate, endDate);

        double totalSalaries = salaries.stream().mapToDouble(SalaireEmployee::getTotal_salaire).sum();
        double averageSalary = totalSalaries / salaries.size();
        double maxSalary = salaries.stream().mapToDouble(SalaireEmployee::getTotal_salaire).max().orElse(0);
        double minSalary = salaries.stream().mapToDouble(SalaireEmployee::getTotal_salaire).min().orElse(0);

        Map<String, Object> statistics = new HashMap<>();
        statistics.put("TotalSalaries", totalSalaries);
        statistics.put("AverageSalary", averageSalary);
        statistics.put("MaxSalary", maxSalary);
        statistics.put("MinSalary", minSalary);

        return ResponseEntity.ok(statistics);
    }

    public ResponseEntity<?> getTotalSalariesEvolution() {
        Map<String, Double> salaryEvolution = new LinkedHashMap<>(); // Use LinkedHashMap to maintain insertion order

        LocalDate oldestSalaryDate = salaireEmployeeService.findOldestSalaryDate();

        LocalDate currentDate = LocalDate.now();

        for (LocalDate current = oldestSalaryDate; !current.isAfter(currentDate); current = current.plusMonths(1)) {
            LocalDate startDate = current.withDayOfMonth(1);
            LocalDate endDate = current.withDayOfMonth(current.lengthOfMonth());

            List<SalaireEmployee> salaries = salaireEmployeeService.findByDateBetween(startDate, endDate);

            double totalSalary = salaries.stream().mapToDouble(SalaireEmployee::getTotal_salaire).sum();

            salaryEvolution.put(current.toString(), totalSalary);
        }

        return ResponseEntity.ok(salaryEvolution);
    }


}
