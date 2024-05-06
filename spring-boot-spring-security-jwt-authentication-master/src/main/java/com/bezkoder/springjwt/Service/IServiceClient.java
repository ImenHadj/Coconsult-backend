package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.notifdto.depassagefacture;
import com.bezkoder.springjwt.notifdto.paymentpercentage;
import com.bezkoder.springjwt.notifdto.rempcalendrier;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface IServiceClient {
    Long ajouterClient(Client client);

    @Transactional
    Client modifierClient(Long id, Client updatedClient);

    List<Client> getAllClients();

    Client getClient(Long id);

    void removeClient(Long id);


    List<Client> getAllClientsSortedByAmount();

    List<Client> getClientsWithDueFactures(int daysThreshold);

    /*fcnt avance1*/


    /*fcnt avance1 pour le calendrier*/
    List<rempcalendrier> fncavnace1(int daysThreshold);

    List<depassagefacture> notifdepassage();

    Long ajouterContrat(Contract c, Long idclient);

    void removeContrat(Long id);

    Contract getContract(Long id);

    List<Contract> getContractbyclient(Long id);

    Contract modifierContrat(Long id, Contract updatedContrat);

    /*facture*/

    Long ajouterFacture(Facture f, Long idclient, Long idcontart);

    void removefacture(Long id);

    Facture getfacture(Long idFacture);

    List<Facture> getAllfactures(Long idclient, Long idcontrat);

    /*paiment*/
    Long ajouterPaiment(Long idfacture, Paiment paiment);


    // Scheduled task to run every day at midnight
    //@Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "0/10 * * * * *")
    List<Contract> sendContractExpirationReminders();

    //notif log
    List<contratNotif> getnotiflog(Long idcontrat);

    //send reminder
    void sendContractReminders(Long id);

    List<paymentpercentage> percentage();

    List<Client> getallclientsbyproductowner();

    List<Project> getprojbyclient(Long id);

    //send reminder

}
