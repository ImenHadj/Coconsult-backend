package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.Candidat;
import com.bezkoder.springjwt.models.DetailRecrutement;
import com.bezkoder.springjwt.models.Recrutement;
import com.bezkoder.springjwt.models.StatutCandidat;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IRhService {

    void Majstatut( );

    List<Recrutement> retrieveAllRecrutements();
    Recrutement addrecrutement ( Recrutement recrutement);
    Recrutement updateRecrutement ( Recrutement recrutement);
    void  RemoveRecrut (Long idRec);
    List<Recrutement> findByPoste(String poste);
    void creerCandidatEtARecrutement( Candidat candidat, Long idRecrutement);
    void Nbrdepostulants(Candidat c, Long idRec);

    Recrutement getRecrutementById( Long idRec);
    List<Candidat> retrieveAllC();
    Candidat addCand (Candidat candidat);

    Candidat getCandByidCandidat ( long idCandidat );
    Candidat updateCand ( Candidat candidat);
    void  removeCand (Long idCandidat);



    void evaluerCandidat(Long idCandidat, Long idRec, Integer score);

    void accepterOuRefuser(Long idCandidat, StatutCandidat nouveauStatut);

    List<Candidat> getCandidatsAcceptes();

    DetailRecrutement proposerDateEntretien(Long idCandidat, DetailRecrutement detailRecrt);
    List<LocalDate> getAllRendezVousDates();

    List<Object[]> getRendezVousDetails();

    Map<String, Integer> getNombreCandidatsParPoste();

    Map<String, Integer> getCandidatsAcceptesParPosteParExperience();
}