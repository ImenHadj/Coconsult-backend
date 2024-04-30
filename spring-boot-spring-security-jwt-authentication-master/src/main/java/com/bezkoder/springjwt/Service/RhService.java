package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.CandidatRepo;
import com.bezkoder.springjwt.repository.DetailsRecRepo;
import com.bezkoder.springjwt.repository.RecrutementRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class RhService  implements  IRhService {
    RecrutementRepo recrutementRepo;
    CandidatRepo candidatRepo;
    DetailsRecRepo detailsRecRepo;



    @Override
    public List<Recrutement> retrieveAllRecrutements() {
        return recrutementRepo.findAll();
    }

    @Override
    public Recrutement addrecrutement(Recrutement rec) {
        return recrutementRepo.save(rec);
    }


    @Override
    public Recrutement updateRecrutement(Recrutement recrutement) {
        return recrutementRepo.save(recrutement);
    }


    @Override
    public void RemoveRecrut(Long idRec) {
        recrutementRepo.deleteById(idRec);
    }

    @Override
    public List<Recrutement> findByPoste(String poste) {
        return recrutementRepo.findByPoste(poste);
    }

    @Override
    public void creerCandidatEtARecrutement(Candidat candidat, Long idRecrutement) {
        Recrutement r = recrutementRepo.findById(idRecrutement).get();
        candidat.setRecrutementC(r);
        candidatRepo.save(candidat);
    }


    public void mettreAJourNbrDuPoste(Long idCandidat, Recrutement recrutement) {
        Candidat candidat = candidatRepo.findById(idCandidat).get();
        if (candidat.getStatutCandidat() == StatutCandidat.SELECTIONNE) {
            if (recrutement.getPostesVacants() > 0) {
                recrutement.setPostesVacants(recrutement.getPostesVacants() - 1);
            } else {
                log.info("les places sont tous occupes");
            }
        }
        candidat.setRecrutementC(recrutement);
        candidatRepo.save(candidat);
        recrutementRepo.save(recrutement);
    }


    @Override
    public Recrutement getRecrutementById(Long idRec) {
        return recrutementRepo.findById(idRec).orElse(null);
    }

    @Override
    public List<Candidat> retrieveAllC() {
        return candidatRepo.findAll();
    }

    @Override
    public Candidat addCand(Candidat candidat) {
        return candidatRepo.save(candidat);
    }


    @Override
    public Candidat getCandByidCandidat(long idCandidat) {
        return candidatRepo.getCandByidCandidat(idCandidat);
    }

    @Override
    public Candidat updateCand(Candidat candidat) {
        return candidatRepo.save(candidat);
    }

    @Override
    public void removeCand(Long idCandidat) {
        candidatRepo.deleteById(idCandidat);
    }

    public void Nbrdepostulants(Candidat c, Long idRec) {
        Recrutement recrutement = recrutementRepo.findById(idRec).get();
        if (recrutement != null) {
            if (recrutement.getStatutRecrut() == StatutRecrut.CLOTURE) {
                throw new RuntimeException("DESOLER MAIS CE RECRUTEMENT ET DEJA CLOTURE");
            }

            boolean candidatExiste = recrutement.getCandidats().stream()
                    .anyMatch(candidat -> candidat.getEmail().equals(c.getEmail()));

            if (!candidatExiste) {
                c.setRecrutementC(recrutement);
                recrutement.setNbrdepostulants(recrutement.getNbrdepostulants() + 1);

                recrutementRepo.save(recrutement);
                candidatRepo.save(c);
            } else {
                throw new RuntimeException("Ce candidat a déjà postulé à ce recrutement.");
            }
        } else {
            throw new RuntimeException("Recrutement avec l'ID " + idRec + " non trouvé");
        }
    }


    @Override
    public void Majstatut() {

        List<Recrutement> recrutements = recrutementRepo.findAllByStatutRecrutement(StatutRecrut.OUVERTE);
        LocalDateTime now = LocalDateTime.now();
        for (Recrutement recrutement : recrutements) {
            LocalDate dateCloture = recrutement.getDateCloture();
            if (dateCloture != null && dateCloture.isBefore(LocalDate.now())) {
                recrutement.setStatutRecrut(StatutRecrut.CLOTURE);
            }
        }
        recrutementRepo.saveAll(recrutements);
    }


    public void evaluerCandidat(Long idCandidat, Long idRec, Integer score) {
        int SEUIL_ACCEPTATION = 60;
        Candidat candidat = candidatRepo.findById(idCandidat).get();
        Recrutement r = recrutementRepo.findById(idRec).get();
        if (candidat != null && r != null) {
            int postesVacants = r.getPostesVacants();
            if (postesVacants == 0) {
                System.out.println("Il n'y a plus de places disponibles pour ce recrutement.");
            } else {
                if (score != null) {
                    candidat.setScore(score);
                    if (score >= SEUIL_ACCEPTATION) {
                        candidat.setStatutCandidat(StatutCandidat.SELECTIONNE);
                        r.setPostesVacants(r.getPostesVacants() - 1);
                    } else {
                        candidat.setStatutCandidat(StatutCandidat.REFUSE);
                    }
                } else {
                    candidat.setStatutCandidat(StatutCandidat.REFUSE);
                }
                candidatRepo.save(candidat);
                recrutementRepo.save(r);
            }

        }
    }

    public void accepterOuRefuser(Long idCandidat, StatutCandidat nouveauStatut) {
        Candidat candidat = candidatRepo.findById(idCandidat).get();

        candidat.setStatutCandidat(nouveauStatut);

        candidatRepo.save(candidat);
    }

    @Override
    public List<Candidat> getCandidatsAcceptes() {
        return candidatRepo.findAllSelectionnes();
    }


    public DetailRecrutement proposerDateEntretien(Long idCandidat, DetailRecrutement detailRecrt) {
        Candidat candidat = candidatRepo.findById(idCandidat).get();
        candidat.setDetailRecrutement(detailRecrt);
        detailRecrt.setCandidat(candidat);
        return detailsRecRepo.save(detailRecrt);
    }


    public List<LocalDate> getAllRendezVousDates() {
        List<DetailRecrutement> rendezVousList = detailsRecRepo.findAll();
        List<LocalDate> rendezVousDates = new ArrayList<>();
        for (DetailRecrutement rendezVousItem : rendezVousList) {
            rendezVousDates.add(rendezVousItem.getDateEntretien());
        }
        return rendezVousDates;
    }

    @Transactional
    public List<Object[]> getRendezVousDetails() {
        return detailsRecRepo.getRendezVousDetails();
    }


    public List<String> getEmailsByCandidatId(Long candidatId) {
        return candidatRepo.findEmailsByIdCandidat(candidatId);
    }

    public Map<String, Integer> getNombreCandidatsParPoste() {
        List<Object[]> results = recrutementRepo.findNombreCandidatsParPoste();
        Map<String, Integer> nombreCandidatsParPoste = new HashMap<>();
        int totalCandidats = 0;
        int posteMaxCandidats = 0;
        int posteMinCandidats = Integer.MAX_VALUE;
        String posteMax = "";
        String posteMin = "";
        for (Object[] result : results) {
            String poste = (String) result[0];
            int nombreCandidats = ((Long) result[1]).intValue();
            nombreCandidatsParPoste.put(poste, nombreCandidats);
            totalCandidats += nombreCandidats;
            if (nombreCandidats > posteMaxCandidats) {
                posteMaxCandidats = nombreCandidats;
                posteMax = poste;
            }
            if (nombreCandidats < posteMinCandidats) {
                posteMinCandidats = nombreCandidats;
                posteMin = poste;
            }
        }
        double moyenneCandidatsParPoste = totalCandidats / (double) nombreCandidatsParPoste.size();

        // Afficher les statistiques
        System.out.println("Total de candidats : " + totalCandidats);
        System.out.println("Poste avec le plus de candidats : " + posteMax + " (Nombre : " + posteMaxCandidats + ")");
        System.out.println("Poste avec le moins de candidats : " + posteMin + " (Nombre : " + posteMinCandidats + ")");
        System.out.println("Moyenne de candidats par poste : " + moyenneCandidatsParPoste);

        return nombreCandidatsParPoste;
    }
    public Map<String, Integer> getCandidatsAcceptesParPosteParExperience() {
        List<Object[]> results = recrutementRepo.getCandidatsAcceptesParPosteParExperience();

        Map<String, Integer> candidatsAcceptesParPosteParExperiencePro = new HashMap<>();

        for (Object[] result : results) {
            String poste = (String) result[0];
            Integer nombreCandidats = ((Long) result[1]).intValue();

            candidatsAcceptesParPosteParExperiencePro.put(poste, nombreCandidats);
        }
        return candidatsAcceptesParPosteParExperiencePro;
    }



//    public DetailRecrutement proposerDateEntretien(Long idCandidat, DetailRecrutement detailRecrt) {
//        Candidat candidat = candidatRepo.findById(idCandidat).get();
//        candidat.setDetailRecrutement(detailRecrt);
//        detailRecrt.setCandidat(candidat);
//
//        // Enregistrer le détail de recrutement
//        detailRecrt = detailsRecRepo.save(detailRecrt);
//        candidatRepo.save(candidat);
//
//        // Récupérer le numéro de téléphone, la date d'entretien, nom et prénom
//        String numeroCandidat = candidat.getTelephone();
//        LocalDate dateEntretien = detailRecrt.getDateEntretien();
//        String nomCandidat = candidat.getNom();
//        String prenomCandidat = candidat.getPrenom();
//
//        // Initialiser Twilio
//        String accountSid = "AC3f533a6deb2b1e1a731cb8304b5f613d";
//        String authToken = "429783ec8e700d7e898e16129f3d19b1";
//        Twilio.init(accountSid, authToken);
//
//        // Numéro Twilio pour envoyer le SMS
//        String fromNumber = "+13344014268";
//
//        // Créer un message avec la date d'entretien, nom et prénom du candidat
//        String body = "Bonjour " + prenomCandidat + " " + nomCandidat +
//                "Votre entretien est prévu pour le " + dateEntretien + ".";
//
//        // Envoyer le SMS
//        Message message = Message.creator(
//                new PhoneNumber(numeroCandidat),
//                new PhoneNumber(fromNumber),
//                body
//        ).create();
//
//        System.out.println("Message envoyé : " + message.getSid());
//
//        // Retourner le détail de recrutement mis à jour
//        return detailRecrt;
//    }
//    public void sendSms(String to, String from, String body) {
//        Message message = Message.creator(
//                new PhoneNumber(to),
//                new PhoneNumber(from),
//                body
//        ).create();
//
//    }

}
