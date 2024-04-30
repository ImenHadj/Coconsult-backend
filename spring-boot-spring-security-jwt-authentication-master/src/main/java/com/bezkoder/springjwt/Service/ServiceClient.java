package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.Client;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.notifdto.MonthlyPaymentDTO;
import com.bezkoder.springjwt.repository.*;
import com.bezkoder.springjwt.notifdto.depassagefacture;
import com.bezkoder.springjwt.notifdto.paymentpercentage;
import com.bezkoder.springjwt.notifdto.rempcalendrier;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@EnableScheduling
public class ServiceClient implements IServiceClient{

    @Autowired
    ClientRep clientRep;
    @Autowired
    ContartRep contartRep;
    @Autowired
    FactureRep factureRep;
    @Autowired
    PaimentRep paimentRep;
    @Autowired
    Notifcontratrep notifcontratrep;


    @Autowired
    private JavaMailSender emailSender;
    @Override
    public Long ajouterClient(Client c){
        c.setAmount(BigDecimal.valueOf(0));
        return clientRep.save(c).getIdClient();}

    @Override
    public Client modifierClient(Long id, Client updatedClient) {
        Client existingClient = clientRep.findById(id).orElse(null);

        if (existingClient != null) {
            if (updatedClient.getNom() != null) {
                existingClient.setNom(updatedClient.getNom());
            }
            if (updatedClient.getPrenom() != null) {
                existingClient.setPrenom(updatedClient.getPrenom());
            }
            if (updatedClient.getEmail() != null) {
                existingClient.setEmail(updatedClient.getEmail());
            }
            if (updatedClient.getPhone() != null) {
                existingClient.setPhone(updatedClient.getPhone());
            }
            if (updatedClient.getCompanyAddress() != null) {
                existingClient.setCompanyAddress(updatedClient.getCompanyAddress());
            }
            clientRep.save(existingClient);

            return existingClient;
        } else {

            return null;
        }
    }



    @Override
    public List<Client> getAllClients() {
        return clientRep.findAll();
    }
    @Override
    public Client getClient(Long id){
        Client client = clientRep.findById(id).orElse(null);
        return client;
    }
    @Override
    public void removeClient(Long id) {
        Client client = clientRep.findById(id).orElse(null);
        clientRep.deleteById(id);

    }
    @Override
    public List<Client> getAllClientsSortedByAmount() {
        return clientRep.findAllByOrderByAmountAsc();
    }
    @Override
    public List<Client> getClientsWithDueFactures(int daysThreshold) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime dueDateThreshold = today.plusDays(daysThreshold);
        return clientRep.findClientsWithDueFactures(today, dueDateThreshold);
    }
    /*fcnt avance1 pour le calendrier*/
    public List<rempcalendrier> fncavnace1(int daysThreshold) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime dueDateThreshold = today.plusDays(daysThreshold);
        List<rempcalendrier> rempcalendrier = clientRep.remplircalendrier(today, dueDateThreshold);
        return rempcalendrier;
    }
@Override
    public List<depassagefacture> notifdepassage(){
        LocalDateTime today = LocalDateTime.now();
        List<depassagefacture> depassagefactures = factureRep.notification(today);
        return  depassagefactures;
    }


    /*contrat*/

    @Override
    public Long ajouterContrat(Contract c, Long idclient){
        Client client = clientRep.findById(idclient).orElse(null);
        c.setClient(client);
        BigDecimal somme = client.getAmount();
        client.setAmount( c.getPayment_terms().add(somme));
        clientRep.save(client);
        return contartRep.save(c).getIdContract();}

    @Override
    public void removeContrat(Long id) {
            Contract c = contartRep.findById(id).orElse(null);
        contartRep.deleteById(id);

    }
    @Override
    public Contract getContract(Long id){
        Contract contract = contartRep.findById(id).orElse(null);
        return contract;
    }

    @Override
    public List<Contract> getContractbyclient(Long id){
    return contartRep.getContractbyclient(id);
    }
    @Override
    public Contract modifierContrat(Long id, Contract updatedContrat) {
        Contract existingContrat = contartRep.findById(id).orElse(null);

        if (existingContrat != null) {
            if (updatedContrat.getContractDate() != null) {
                existingContrat.setContractDate(updatedContrat.getContractDate());
            }
            if (updatedContrat.getStartDate() != null) {
                existingContrat.setStartDate(updatedContrat.getStartDate());
            }
            if (updatedContrat.getEndDate() != null) {
                existingContrat.setEndDate(updatedContrat.getEndDate());
            }
            if (updatedContrat.getItem_description() != null) {
                existingContrat.setItem_description(updatedContrat.getItem_description() );
            }
            if (updatedContrat.getContract_status() != null) {
                existingContrat.setContract_status(updatedContrat.getContract_status());
            }
            if (updatedContrat.getTypeContrat() != null) {
                existingContrat.setTypeContrat(updatedContrat.getTypeContrat());
            }
            if (updatedContrat.getVersion() != 0) {
                existingContrat.setVersion(updatedContrat.getVersion());
            }
            if (updatedContrat.getPayment_terms() !=null) {
                existingContrat.setPayment_terms(updatedContrat.getPayment_terms());
            }

            contartRep.save(existingContrat);

            return existingContrat;
        } else {

            return null;
        }
    }
/*facture*/
@Override
public Long ajouterFacture(Facture f, Long idclient, Long idcontart){
    Client client = clientRep.findById(idclient).orElse(null);
    Contract c = contartRep.findById(idcontart).orElse(null);
    f.setClient(client);
    f.setContract(c);
    c.getFactures().add(f);
    contartRep.save(c);
    f.setPaid_amount(BigDecimal.valueOf(0.0));
    f.setPayment_status("Outstanding");
    return factureRep.save(f).getIdFacture();}
    @Override
    public void removefacture(Long id) {
        Facture c = factureRep.findById(id).orElse(null);
        factureRep.deleteById(id);

    }

    @Override
    public Facture getfacture(Long idFacture) {

    return factureRep.findById(idFacture).orElse(null);
    }

    @Override
    public List<Facture> getAllfactures(Long idclient, Long idcontrat) {

        return factureRep.findAllFactures(idclient,idcontrat);
    }

    /*paiment*/
    @Override
    public Long ajouterPaiment(Long idfacture, Paiment paiment){
        Facture c = factureRep.findById(idfacture).orElse(null);
        Client client =c.getClient();
            c.setPaid_amount(c.getPaid_amount().add(paiment.getAmount()));
        if (c.getPaid_amount().compareTo(c.getTotal_amount()) >= 0) {
            c.setPayment_status("Paid");
        }
        else{
            c.setPayment_status("Partially paid");
        }
        BigDecimal totaledeclient = client.getAmount();
        client.setAmount(totaledeclient.subtract(paiment.getAmount()));
        paiment.setClient(client);
        paiment.setFacture(c);
        return paimentRep.save(paiment).getIdpaiment();
    }
/****************/

public void sendContractExpirationRemindersScheduled() {
    sendContractExpirationReminders();
}
@Override
@Transactional
@Scheduled(cron = "0 28 23 * * *")
public List<Contract> sendContractExpirationReminders() {
    LocalDateTime today = LocalDateTime.now();
    LocalDateTime dueDateThreshold = today.plusDays(30);

    List<Contract> expiringContracts = contartRep.findExpiringContracts(dueDateThreshold);
    for (Contract contract : expiringContracts) {
        String zEmail = contract.getClient().getEmail();
        String name =contract.getClient().getNom()+" "+contract.getClient().getPrenom();
        System.out.println("This is an informational message." + zEmail);

        contratNotif newContratNotif = new contratNotif();
        newContratNotif.setMaildate(LocalDateTime.now());
        newContratNotif.setNotified(true);
        newContratNotif.setClientemail(zEmail);
        newContratNotif.setClientname(name);
        newContratNotif.setContract(contract);
        contract.getContratNotif().add(newContratNotif);
        sendContractExpirationReminderToClient(zEmail, contract);
        contartRep.save(contract);

    }
    return expiringContracts;
}


    public  void sendContractExpirationReminderToClient(String toEmail, Contract contract) {
        SimpleMailMessage message  = new SimpleMailMessage();
        message.setFrom("gramiaziz9@gamil.com");
        message.setSubject("Contract Expiration Reminder");
        message.setTo(toEmail);
        message.setText("Dear Client,"+ contract.getClient().getNom()+ " " + contract.getClient().getPrenom()+"\n\nYour contract with ID " + contract.getIdContract() +
                    " is expiring soon. Please take necessary actions.\n\nSincerely,\nYour Company");
        emailSender.send(message);

    }
//notif log
@Override
public List<contratNotif> getnotiflog(Long idcontrat) {
    Contract contract = contartRep.findById(idcontrat).orElse(null);
    return contract.getContratNotif();
}
//send reminder
    @Override
public void sendContractReminders(Long id) {
    Contract contract = contartRep.findById(id).orElse(null);

        String zEmail = contract.getClient().getEmail();
        String name =contract.getClient().getNom()+" "+contract.getClient().getPrenom();
        System.out.println("This is an informational message." + zEmail);

        contratNotif newContratNotif = new contratNotif();
        newContratNotif.setMaildate(LocalDateTime.now());
        newContratNotif.setNotified(true);
        newContratNotif.setClientemail(zEmail);
        newContratNotif.setClientname(name);
        newContratNotif.setContract(contract);
        contract.getContratNotif().add(newContratNotif);
        sendContractExpirationReminderToClient(zEmail, contract);
        contartRep.save(contract);

}
/*************/
@Override
public List<paymentpercentage> percentage(){
    List<paymentpercentage> percentage = paimentRep.getPaymentTypePercentages();
    return  percentage;
}

    @Override
    public List<MonthlyPaymentDTO> getTotalPaymentsByMonth() {
        return paimentRep.getTotalPaymentsByMonth();
    }

    @Override
    public List<Client> getallclientsbyproductowner(){
    return clientRep.findAll();
    }
@Override
    public List<Project> getprojbyclient(Long id){
    Client client = clientRep.findById(id).orElse(null);
    return client.getProjects();
}

}


