package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.Service.interfaces.IServiceContratEmpl;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import com.lowagie.text.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
    UserRepository userRepository;

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
    public ResponseEntity<?> updateContratEmployee(ContratEmployee updatedContrat, Long id) {
        Optional<ContratEmployee> optionalContrat = contratEmplRepo.findById(id);

        if (optionalContrat.isPresent()) {
            ContratEmployee existingContrat = optionalContrat.get();
            Employee emp = existingContrat.getEmpl();

            if (ContratEmployeeIsValid(updatedContrat, emp)) {
                existingContrat.setRib(updatedContrat.getRib());
                existingContrat.setDate_debut(updatedContrat.getDate_debut());
                existingContrat.setDate_fin(updatedContrat.getDate_fin());
                existingContrat.setDuree_hebdomadaire(updatedContrat.getDuree_hebdomadaire());
                existingContrat.setSalaire_base(updatedContrat.getSalaire_base());
                existingContrat.setMontant_heures_supplementaires(updatedContrat.getMontant_heures_supplementaires());
                existingContrat.setNumeroSecuriteSociale(updatedContrat.getNumeroSecuriteSociale());
                existingContrat.setMontant_Conge_Absence(updatedContrat.getMontant_Conge_Absence());
                existingContrat.setTypeCE(updatedContrat.getTypeCE());
                existingContrat.setIsArchive(updatedContrat.getIsArchive());
                existingContrat.setPourcentage(updatedContrat.getPourcentage());

                ContratEmployee updatedContratEmployee = contratEmplRepo.save(existingContrat);
                return ResponseEntity.ok(updatedContratEmployee.getId_contrat_e());
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Invalid Contrat request. Please check your inputs.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Contrat not found with ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
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
public void export(HttpServletResponse response, ContratEmployee contrat,Long id) throws IOException, WriterException, DocumentException {
    Document document = new Document(PageSize.A4);
    ByteArrayOutputStream qrCodeStream = new ByteArrayOutputStream();

    PdfWriter.getInstance(document, response.getOutputStream());
    document.open();
    Employee employee = employeeRepo.findById(id).get();
    Long userId =employee.getUserId();
    User user = userRepository.findById(userId).get();

    String qrCodeData ="{\"UserName\":\"" + user.getUsername() + "{\"rib\":\"" + contrat.getRib() + "\",\"socialSecurityNumber\":\"" + contrat.getNumeroSecuriteSociale() + "\",\"startDate\":\"" + contrat.getDate_debut() + "\",\"endDate\":\"" + contrat.getDate_fin() + "\"}";

    generateQRCodeImage(qrCodeData, qrCodeStream);

    Image qrCodeImage = Image.getInstance(qrCodeStream.toByteArray());
    qrCodeImage.scaleAbsolute(100, 100); // Adjust size as needed
    document.add(qrCodeImage);

    Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
    fontTitle.setSize(18);
    Paragraph paragraphTitle = new Paragraph("Contract Details", fontTitle);
    paragraphTitle.setAlignment(Paragraph.ALIGN_CENTER);
    document.add(paragraphTitle);
    Font fontData = FontFactory.getFont(FontFactory.HELVETICA);
    fontData.setSize(12);
    Paragraph paragraphData = new Paragraph();
    paragraphData.setFont(fontData);
    paragraphData.add("Username: " + user.getUsername() + "\n");
    paragraphData.add("Rib: " + contrat.getRib() + "\n");
    paragraphData.add("Social Security Number: " + contrat.getNumeroSecuriteSociale() + "\n");
    paragraphData.add("Start Date: " + contrat.getDate_debut() + "\n");
    paragraphData.add("End Date: " + contrat.getDate_fin() + "\n");
    paragraphData.add("Contract Type: " + contrat.getTypeCE() + "\n");
    paragraphData.add("Weekly Duration: " + contrat.getDuree_hebdomadaire() + " hours\n");
    paragraphData.add("Base Salary: " + contrat.getSalaire_base() + " TD\n");
    paragraphData.add("Price of Hour: " + contrat.getMontant_heures_supplementaires() + " TD\n");
    paragraphData.add("Percentage: " + contrat.getPourcentage() + " TD\n");
    paragraphData.add("Is Archived: " + contrat.getIsArchive() + "\n");
    document.add(paragraphData);
    document.close();
}

    private void generateQRCodeImage(String text, OutputStream outputStream) throws WriterException, IOException {
        int width = 200;
        int height = 200;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        BufferedImage qrCodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                qrCodeImage.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }

        ImageIO.write(qrCodeImage, "png", outputStream);
    }
}
