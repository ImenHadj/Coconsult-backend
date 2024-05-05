package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.Service.interfaces.IServiceContratEmpl;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class ServiceContratEmpl implements IServiceContratEmpl {
    ContratEmplRepo contratEmplRepo;
    EmployeeRepo employeeRepo;

    @Override
    public ResponseEntity<?> addContratEmployee(ContratEmployee contrat, Long id) {
        Employee emp = employeeRepo.findById(id).get();
        Set<ContratEmployee> anis = emp.getContratEmployees();
        ContratEmployee OldContrat = null;
        if (anis.size() > 0) {
            if (ContratEmployeeIsValid(contrat, emp)) {
                for (ContratEmployee se : anis) {
                    se.setIsArchive(true);
                }
                contrat.setIsArchive(false);
                contrat.setEmpl(emp);
                contratEmplRepo.save(contrat);
                return ResponseEntity.ok(contrat.getId_contrat_e());

            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Invalid Contrat request. Please check your inputs.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
        }
        if (contrat.getDate_debut().isAfter(contrat.getDate_fin())) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid Contrat request. Please check your inputs.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        contrat.setEmpl(emp);
        contratEmplRepo.save(contrat);
        return ResponseEntity.ok(contrat.getId_contrat_e());

    }

    public boolean ContratEmployeeIsValid(ContratEmployee contrat, Employee em) {
        Set<ContratEmployee> oldContracts = em.getContratEmployees();
        int nb = 0;
        for (ContratEmployee ce : oldContracts) {
            if (ce.getTypeCE().equals(ContratEmployeeType.CIVP)) {
                nb += 1;
            }
        }

        if (contrat.getDate_debut().isAfter(contrat.getDate_fin()) ||
                nb >= 1 && contrat.getTypeCE().equals(ContratEmployeeType.CIVP)
        ) {
            return false;
        }
        return true;
    }

    @Override
    public ResponseEntity<?> updateContratEmployee(ContratEmployee Updatedcontrat, Long id) {
        ContratEmployee contratEmployee = contratEmplRepo.findById(id).get();
        Employee emp = contratEmployee.getEmpl();

        if (ContratEmployeeIsValid(Updatedcontrat, emp)) {
            ContratEmployee updatedEmployee = ContratEmployee.builder()
                    .rib(Updatedcontrat.getRib())
                    .date_debut(Updatedcontrat.getDate_debut())
                    .date_fin(Updatedcontrat.getDate_fin())
                    .duree_hebdomadaire(Updatedcontrat.getDuree_hebdomadaire())
                    .salaire_base(Updatedcontrat.getSalaire_base())
                    .montant_heures_supplementaires(Updatedcontrat.getMontant_heures_supplementaires())
                    .numeroSecuriteSociale(Updatedcontrat.getNumeroSecuriteSociale())
                    .duree_hebdomadaire(Updatedcontrat.getDuree_hebdomadaire())
                    .montant_Conge_Absence(Updatedcontrat.getMontant_Conge_Absence())
                    .typeCE(Updatedcontrat.getTypeCE())
                    .isArchive(Updatedcontrat.getIsArchive())
                    .build();

            contratEmplRepo.save(updatedEmployee);
            return ResponseEntity.ok(updatedEmployee.getId_contrat_e());
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid Contrat request. Please check your inputs.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


    @Override
    public void deleteContratEmployee(Long id) {
        contratEmplRepo.deleteById(id);
    }

    @Override
    public ContratEmployee getContratEmployee(Long id) {
        return contratEmplRepo.findById(id).get();
    }

    @Override
    public List<ContratEmployee> retrieveAll() {
        return contratEmplRepo.findAll();
    }

    @Scheduled(cron = "0 0 1 ? * *")
//    @Scheduled(fixedDelay = 10000)

    public void retrieveAndUpdateStatusContrat() {
        List<ContratEmployee> contrat = retrieveAll();
        LocalDate localDate = LocalDate.now();
        for (int d = 0; d < contrat.size(); d++) {
            ContratEmployee S = contrat.get(d);
            long aa = ChronoUnit.DAYS.between(localDate, S.getDate_fin());
            if (aa <= 0) {
                log.info("Contrat expiré. Contract ID: {}. Timestamp: {}", S.getId_contrat_e(), LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
                S.setIsArchive(true);
                contratEmplRepo.save(S);
            }
        }
    }

    @Override
    public Integer countByIsArchiveIsFalseAndDate_debutBetween(Date startDate, Date endDate) {
        return contratEmplRepo.countByIsArchiveIsFalseAndDateDebutBetween(startDate, endDate);
    }

    public void export(HttpServletResponse response, ContratEmployee contrat) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        // Adding contract details
        Paragraph paragraphTitle = new Paragraph("Contract Details", fontTitle);
        paragraphTitle.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraphTitle);

        Font fontData = FontFactory.getFont(FontFactory.HELVETICA);
        fontData.setSize(12);
        Paragraph paragraphData = new Paragraph();
        paragraphData.setFont(fontData);
        paragraphData.add("Rib: " + contrat.getRib() + "\n");
        paragraphData.add("Social Security Number: " + contrat.getNumeroSecuriteSociale() + "\n");
        paragraphData.add("Start Date: " + contrat.getDate_debut() + "\n");
        paragraphData.add("End Date: " + contrat.getDate_fin() + "\n");
        paragraphData.add("Contract Type: " + contrat.getTypeCE() + "\n");
        paragraphData.add("Weekly Duration: " + contrat.getDuree_hebdomadaire() + " hours\n");
        paragraphData.add("Base Salary: " + contrat.getSalaire_base() + " TD\n");
        paragraphData.add("Price of Hour: " + contrat.getMontant_heures_supplementaires() + " TD\n");
        paragraphData.add("Is Archived: " + contrat.getIsArchive() + "\n");
        document.add(paragraphData);
        document.close();
    }
}
