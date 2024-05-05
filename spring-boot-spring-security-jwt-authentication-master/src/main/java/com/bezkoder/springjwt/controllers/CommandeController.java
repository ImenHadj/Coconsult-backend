package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.ICommandeService;
import com.bezkoder.springjwt.models.Commande;
import com.bezkoder.springjwt.models.Fournisseur;
import com.bezkoder.springjwt.models.ResourcesCategorie;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/commande")
public class CommandeController {

    ICommandeService commandeService ;


    // http://localhost:8089/coConsult/stock/retrieve-all-commandes
    @GetMapping("/retrieve-all-commandes")
    @ResponseBody
    public List<Commande> getCommandes() {
        List<Commande> commandeList = commandeService.retrieveAllCommandes();
        return commandeList;
    }

    @GetMapping("/retrieve-fournisseurByCateg/{categorie}")
    @ResponseBody
    public List<Fournisseur> retrieveFournisseurByCategorie( @PathVariable("categorie") ResourcesCategorie resourcesCategorie){
        List<Fournisseur> fournisseurList = commandeService.retrieveFournisseurByCategorie(resourcesCategorie);
        return  fournisseurList ;

    }


    @GetMapping("/retrieve-commande/{idStock}")
    @ResponseBody
    public Commande retrieveCommande(@PathVariable("idStock") Long idStock) {
        return commandeService.retrieveCommande(idStock);
    }


    @PostMapping("/add-commande")
    @ResponseBody
    public Commande addCommande(@RequestBody Commande s) {
        Commande stock= commandeService.addCommande(s);
        return stock;
    }


    @PutMapping("/update-commande")
    @ResponseBody
    public Commande updateCommande(@RequestBody Commande s) {
        Commande stock= commandeService.updateCommande(s);
        return stock;
    }

    @DeleteMapping("/removeCommande/{commandeId}")
    @ResponseBody
    public void removeCommande(@PathVariable("commandeId") Long idStock) {
        commandeService.removeCommande(idStock);
    }


    @PostMapping("/arrivee/{commandeId}")
    @ResponseBody
    public void marquerCommandeCommeArrivee(@PathVariable Long commandeId) {
        commandeService.marquerCommandeCommeArrivee(commandeId);
    }

}
