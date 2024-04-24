package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.IFournisseurService;
import com.bezkoder.springjwt.models.Fournisseur;
import com.bezkoder.springjwt.repository.FournisseurRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/fournisseur")
public class FournisseurController {

    IFournisseurService fournisseurService ;
    FournisseurRepository fournisseurRepository ;

    // http://localhost:8089/fournisseur/retrieve-all-fournisseurs
    @GetMapping("/retrieve-all-fournisseurs")
    @ResponseBody
    public List<Fournisseur> getFournisseurs() {
        List<Fournisseur> fournisseurList = fournisseurService.retrieveAllFournisseurs();
        return fournisseurList;
    }


    @GetMapping("/retrieve-fournisseur/{idF}")
    @ResponseBody
    public Fournisseur retrieveFournisseur(@PathVariable("idF") Long idF) {
        return fournisseurService.retrieveFournisseur(idF);
    }


    @PostMapping("/add-fournisseur")
    @ResponseBody
    public Fournisseur addFournisseur(@RequestBody Fournisseur r) {
        Fournisseur fournisseur= fournisseurService.addFournisseur(r);
        return fournisseur;
    }


    @PutMapping("/update-fournisseur")
    @ResponseBody
    public Fournisseur updateFournisseur(@RequestBody Fournisseur r) {
        Fournisseur fournisseur= fournisseurService.updateFournisseur(r);
        return fournisseur;
    }

    @DeleteMapping("/removeFournisseur/{idF}")
    @ResponseBody
    public void removeFournisseur(@PathVariable("idF") Long idF) {
        fournisseurService.removeFournisseur(idF);
    }

    @PutMapping("/calculer-score/{fournisseurId}")
    public Fournisseur calculerScoreFournisseur(@PathVariable("fournisseurId") Long fournisseurId) {
        Optional<Fournisseur> optionalFournisseur = fournisseurRepository.findById(fournisseurId);
        if (optionalFournisseur.isPresent()) {
            Fournisseur fournisseur = optionalFournisseur.get();
            int score = fournisseurService.calculerScoreFournisseur(fournisseur);
            fournisseur.setScore(score);
            fournisseurRepository.save(fournisseur);
            return fournisseur;
        } else {

            throw new RuntimeException("Fournisseur non trouvé avec l'ID : " + fournisseurId);
        }
    }

    @GetMapping("/top-three-fournisseurs")
    public List<Fournisseur> getTopThreeFournisseursWithStocks() {
        return fournisseurService.getTopThreeFournisseursWithStocks();
    }

    @GetMapping("/{fournisseurID}")
    public int getNombreStocksFournisseur(@PathVariable("fournisseurID") Long fournisseurID) {
        return fournisseurService.getNombreStocksFournisseur(fournisseurID);
    }

}
