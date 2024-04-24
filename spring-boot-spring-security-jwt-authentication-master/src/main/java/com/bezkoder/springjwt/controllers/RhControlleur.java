package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.CloudService;
import com.bezkoder.springjwt.Service.RhService;
import com.bezkoder.springjwt.models.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor

@CrossOrigin(origins = "*")
@RequestMapping("/recrutement")

public class RhControlleur {
    JavaMailSender emailSender;

    RhService rhService;
    CloudService cloudService;
    @GetMapping("/getAll")
    public List<Recrutement> retrieveAllRecrutements() {
        return rhService.retrieveAllRecrutements();}
    //http://localhost:8081/recrutement/addRecrut

    @PostMapping("/addRecrutement")
    // @PostMapping("/recrutement/addRecrutement")
    public Recrutement addrecrutement (@RequestBody Recrutement recrutement ) {
        return rhService.addrecrutement(recrutement);}

    @GetMapping("/getByid/{idRec}")
    public  Recrutement getRecrutementById(@PathVariable  ("idRec") long idRec){
        return rhService.getRecrutementById(idRec);
    }

    @PutMapping("/updateRecrutement")
    public Recrutement updateRecrutement (@RequestBody   Recrutement  recrutement   ) {
        return rhService.updateRecrutement(recrutement);}


    //http://localhost:8081/recrutement/deleteRec
    @DeleteMapping("/deleteRec/{idRec}")

    public void removeRecrutement(@PathVariable Long idRec) {
        rhService.RemoveRecrut(idRec); }

    //http://localhost:8081/recrutement/findByPoste/
    @GetMapping("/findByPoste/{poste}")
    public List<Recrutement> findByPoste(@PathVariable("poste") String poste) {
        return rhService.findByPoste(poste);
    }


    @PostMapping("candidats/{idRecrutement}")
    public void creerCandidatEtARecrutement(@RequestBody Candidat candidat, @PathVariable Long idRecrutement) {
        rhService.creerCandidatEtARecrutement(candidat, idRecrutement); }

    @PutMapping("aa/{idCandidat}")
    void  mettreAJourNbrDuPoste( @PathVariable Long idCandidat, @RequestBody Recrutement recrutement){
        rhService.mettreAJourNbrDuPoste(idCandidat, recrutement);
    }

    //affecter candidat a recrutement
    @PostMapping("/nbr/{idRec}")
    public void Nbrdepostulants ( @RequestBody Candidat c ,@PathVariable ("idRec") long idRec) {
        rhService.Nbrdepostulants( c, idRec) ;
    }




//************************candidat********************************

    @GetMapping("/getAllc")
    public List<Candidat> retrieveAllC () {
        return rhService.retrieveAllC();}
    @PostMapping("/addcand")
    public  Candidat addCand (@RequestBody Candidat candidat) {
        return  rhService.addCand(candidat);
    }


    @DeleteMapping("removeCand/{idCandidat}")
    public void  removeCand (@PathVariable ("idCandidat") long idCandidat){
        rhService.removeCand(idCandidat);}

    //http://localhost:8081/recrutement/getCandById
    @GetMapping("/getCandById/{idCandidat}")
    Candidat getCandById (@PathVariable("idCandidat")long idCandidat ){
        return  rhService.getCandByidCandidat(idCandidat);
    }
    @PutMapping("/updateCand")
    public Candidat updateCand (@PathVariable Candidat candidat ){
        return rhService.updateCand(candidat);
    }
    //http://localhost:8081/recrutement/score

    @PutMapping("/S/{idCandidat}/{idRec}")
    public void evaluerCandidat(@PathVariable("idCandidat") Long idCandidat, @PathVariable("idRec") Long idRec,  @RequestBody Integer score){
        rhService.evaluerCandidat(idCandidat, idRec,score );}


    //PUT http://localhost:8081/recrutement/accp/24?nouveauStatut=REFUSE
    @PutMapping("accp/{idCandidat}")
    public void accepterOuRefuser (@PathVariable ("idCandidat") long idCandidat, @RequestParam StatutCandidat nouveauStatut) {
        rhService.accepterOuRefuser(idCandidat,nouveauStatut);   }

    @GetMapping("/acceptes")
    public List<Candidat> getCandidatsAcceptes() {
        return rhService.getCandidatsAcceptes();
    }



    @PostMapping ("/add/{idCandidat}")
    public DetailRecrutement proposerDateEntretien(@PathVariable("idCandidat") Long idCandidat, @RequestBody  DetailRecrutement detailRecrt) {
        return rhService.proposerDateEntretien(idCandidat, detailRecrt);
    }



    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file1") MultipartFile file1,
                                         @RequestParam(value = "file2", required = false) MultipartFile file2,
                                         @RequestParam("idRecrutement") Long idRecrutement,
                                         @ModelAttribute Candidat candidat) throws IOException {
        Recrutement recrutement = rhService.getRecrutementById(idRecrutement);
        if (recrutement == null) {
            return new ResponseEntity<>("Recrutement non trouvé pour l'ID spécifié.", HttpStatus.NOT_FOUND);
        } candidat.setRecrutementC(recrutement);
        int score = 0;
        if (candidat.getNiveauDetude() == niveauDetude.MASTERE  ) {
            score += 20;
        } else if (candidat.getNiveauDetude() == niveauDetude.INGENIEUR ) {
            score += 30;
        } else if (candidat.getNiveauDetude() == niveauDetude.LICENCE) {
            score += 10;
        }
        if (candidat.getExperience() == Experience.DEBUTANT) {
            score += 20;
        } else if (candidat.getExperience() == Experience.JUNIOR) {
            score += 40;
        } else if (candidat.getExperience() == Experience.PRO) {
            score += 50;
        } else if (candidat.getExperience() == Experience.EXPERT) {
            score += 60;
        }
        candidat.setScore(score);
        if (score == 90) {
            candidat.setStatutCandidat(StatutCandidat.SELECTIONNE);
        }
        // Téléverser les fichiers
        Map result1 = cloudService.upload(file1);
        String fileUrl1 = (String) result1.get("url");
        candidat.setCvUrl(fileUrl1);

        String fileUrl2 = null;
        if (file2 != null) {
            Map result2 = cloudService.upload(file2);
            fileUrl2 = (String) result2.get("url");
            candidat.setLettreMotivationUrl(fileUrl2);
        }
        // Enregistrer le candidat avec l'ID du recrutement
        rhService.addCand(candidat);

        return new ResponseEntity<>("Fichiers téléversés avec succès ! ", HttpStatus.OK);
    }

    @GetMapping("/nombreCandParPoste")
    public ResponseEntity<Map<String, Integer>> getNombreCandidatsParPoste() {
        // Appeler la méthode du service pour obtenir les données
        Map<String, Integer> nombreCandidatsParPoste = rhService.getNombreCandidatsParPoste();

        return ResponseEntity.ok(nombreCandidatsParPoste);
    }

    @GetMapping("/candidatsAcceptesParPosteParExperiencPro")
    public ResponseEntity<Map<String, Integer>> getCandidatsAcceptesParPosteParExperience() {
        Map<String, Integer> candidatsAcceptesParPosteParExperiencePro = rhService.getCandidatsAcceptesParPosteParExperience();
        return ResponseEntity.ok(candidatsAcceptesParPosteParExperiencePro);
    }





}

