package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.RhService;
import com.bezkoder.springjwt.models.DetailRecrutement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/DetailsRect")
public class DetaisRecrutementControlleur {
    RhService rhService;

    @PostMapping("/add/{idCandidat}")
    public DetailRecrutement proposerDateEntretien(@PathVariable("idCandidat") Long idCandidat, @RequestBody  DetailRecrutement detailRecrt) {
        return rhService.proposerDateEntretien(idCandidat, detailRecrt);
    }
    /*
        @GetMapping("/rendezvous")
        public List<DetailRecrutement> getRendezVousByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEntretien) {
            return rhService.getRendezVousByDate(dateEntretien);
        }
    */
    @GetMapping("/rendezvous")
    public List<LocalDate> getRendezVousDates() {
        return rhService.getAllRendezVousDates();
    }

    @GetMapping("/rdv")
    public List<Object[]> getRendezVousDetails() {
        return rhService.getRendezVousDetails();
    }



}
