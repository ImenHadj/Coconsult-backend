package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.IReclamationService;
import com.bezkoder.springjwt.models.Priority;
import com.bezkoder.springjwt.models.Reclamation;
import com.bezkoder.springjwt.models.ReclamationStatus;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/reclamation")
public class ReclamationController {

    IReclamationService reclamationService ;

    // http://localhost:8089/reclamation/retrieve-all-reclamations
    @GetMapping("/retrieve-all-reclamations")
    @ResponseBody
    public List<Reclamation> getReclamations() {
        List<Reclamation> reclamationList = reclamationService.retrieveAllReclamations();
        return reclamationList;
    }


    @GetMapping("/retrieve-reclamation/{idRec}")
    @ResponseBody
    public Reclamation retrieveReclamation(@PathVariable("idRec") Long idRec) {
        return reclamationService.retrieveReclamation(idRec);
    }


    @PostMapping("/add-reclamation")
    @ResponseBody
    public Reclamation addReclamation(@RequestBody Reclamation r) {
        r.setPriorité(Priority.LOW);
        r.setStatusReclamation(ReclamationStatus.PENDING);
        Date date = new Date();
        r.setReclamationDate(date);
        Reclamation reclamation= reclamationService.addReclamation(r);
        return reclamation;
    }


    @PutMapping("/update-reclamation")
    @ResponseBody
    public Reclamation updateReclamation(@RequestBody Reclamation r) {
        Reclamation reclamation= reclamationService.updateReclamation(r);
        return reclamation;
    }

    @DeleteMapping("/removeReclamation/{idRec}")
    @ResponseBody
    public void removeReclamation(@PathVariable("idRec") Long idRec) {
        reclamationService.removeReclamation(idRec);
    }

}
