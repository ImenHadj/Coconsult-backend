package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.interfaces.IServiceContratEmpl;
import com.bezkoder.springjwt.models.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/contratEmpl")
@CrossOrigin(origins = "http://localhost:4200")

public class ContratEmplController {
    IServiceContratEmpl iServiceContratEmpl;


    @PostMapping("/pdfgenerate")
    public void generatePDF(HttpServletResponse response,@RequestBody ContratEmployee contrat) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        iServiceContratEmpl.export(response,contrat);
    }
    @PostMapping("/saveContratEmployee/{id}")
    public ResponseEntity<?> addContratEmployee(@RequestBody ContratEmployee p, @PathVariable("id") Long id){
        return  iServiceContratEmpl.addContratEmployee(p,id);
    }

    @PutMapping("/updateContratEmployee/{id}")
    public  ResponseEntity<?> updateContratEmployee(@RequestBody ContratEmployee p, @PathVariable("id") Long id){
        return iServiceContratEmpl.updateContratEmployee(p,id);
    }

    @DeleteMapping("/deleteContratEmployee/{p}")
    public void deleteContratEmployee(@PathVariable("p") Long p) {
        iServiceContratEmpl.deleteContratEmployee(p);
    }
    @GetMapping("/getContratEmployee/{id}")
    public ContratEmployee getContratEmployee(@PathVariable("id") Long id){
        return iServiceContratEmpl.getContratEmployee(id);
    }
    @GetMapping("/retrieveAll")
    public List<ContratEmployee> retrieveAll(){
        return iServiceContratEmpl.retrieveAll();
    }

    @GetMapping("/countByIsArchiveIsFalseAndDate_debutBetween/{startDate}/{endDate}")
    public Integer countByIsArchiveIsFalseAndDate_debutBetween(@PathVariable("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
                                                               @PathVariable("endDate")@DateTimeFormat(pattern="yyyy-MM-dd") Date endDate
    ){
        return iServiceContratEmpl.countByIsArchiveIsFalseAndDate_debutBetween(startDate,endDate);
    }
}
