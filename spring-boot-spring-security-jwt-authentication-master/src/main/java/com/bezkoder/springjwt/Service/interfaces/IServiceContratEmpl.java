package com.bezkoder.springjwt.Service.interfaces;

import com.bezkoder.springjwt.models.ContratEmployee;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface IServiceContratEmpl {
    public ResponseEntity<?> addContratEmployee(ContratEmployee contrat, Long id);
    public ResponseEntity<?> updateContratEmployee(ContratEmployee Updatedcontrat, Long id);
    public void deleteContratEmployee(Long id);
    public ContratEmployee getContratEmployee(Long id);
    List<ContratEmployee> retrieveAll();
    public Integer countByIsArchiveIsFalseAndDate_debutBetween(Date startDate, Date endDate);
//    public void generateContractPDF(ContratEmployee contrat) throws IOException;
    public void export(HttpServletResponse response, ContratEmployee contrat) throws IOException;

}
