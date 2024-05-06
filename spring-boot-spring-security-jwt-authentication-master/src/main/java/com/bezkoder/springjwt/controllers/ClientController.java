package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.IServiceClient;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.notifdto.depassagefacture;
import com.bezkoder.springjwt.notifdto.paymentpercentage;
import com.bezkoder.springjwt.notifdto.rempcalendrier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*@CrossOrigin(origins = "*")*/
@RestController
@RequestMapping("/Clients")
public class ClientController {

    @Autowired
    IServiceClient iServiceClient;

    @PostMapping("/addclient")
    @ResponseBody
    public Long addclient(@RequestBody Client client){
        return iServiceClient.ajouterClient(client);
    }

    @GetMapping("/getall")
    public List<Client> getall(){
        return iServiceClient.getAllClients();
    }

    @GetMapping("/getClient/{idc}")
    @ResponseBody
    public Client getClient(@PathVariable("idc")Long id){
        return iServiceClient.getClient(id);
    }

    @PutMapping("/updateclient/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        return iServiceClient.modifierClient(id, updatedClient);
    }
    @GetMapping("/filtrage")
    public List<Client> filtred(){
        return iServiceClient.getAllClientsSortedByAmount();
    }
    @GetMapping("/duedate/{nombre}")
    public List<Client> duedate(@PathVariable int nombre){
        return iServiceClient.getClientsWithDueFactures(nombre);
    }

    @GetMapping("/calendrier/{nombre}")
    //nombre = 7 ou 31 pour moins
    public List<rempcalendrier> getcallender(@PathVariable int nombre){
        return iServiceClient.fncavnace1(nombre);
    }
    @DeleteMapping("/{id}")
    public void removeClient(@PathVariable Long id) {
            iServiceClient.removeClient(id);
    }

    /*contrat*/
    @PostMapping("/addcontrat/{clientId}")
    public Long addContract(@RequestBody Contract contract, @PathVariable Long clientId) {
         iServiceClient.ajouterContrat(contract, clientId);
        return contract.getIdContract();
    }
    @DeleteMapping("/contrat/{id}")
    public void removeContrat(@PathVariable Long id) {
        iServiceClient.removeContrat(id);
    }

    @GetMapping("/getcontart/{idc}")
    @ResponseBody
    public Contract getcontart(@PathVariable("idc")Long id){
        return iServiceClient.getContract(id);
    }

    @GetMapping("/getcontartbyidclient/{idc}")
    @ResponseBody
    public List<Contract> getContractbyclient(@PathVariable("idc")Long id){
        return iServiceClient.getContractbyclient(id);
    }
    @PutMapping("/updatecontrat/{id}")
    public Contract updateContrat(@PathVariable Long id, @RequestBody Contract updatedContrat) {
        return iServiceClient.modifierContrat(id, updatedContrat);
    }
/*factures*/
@PostMapping("/addfacture/{clientId}/{idcontrat}")
public Long addfacture(@RequestBody Facture f, @PathVariable Long clientId,@PathVariable Long idcontrat) {
    iServiceClient.ajouterFacture(f,clientId,idcontrat);
    return f.getIdFacture();
}
    @DeleteMapping("/facture/{id}")
    public void removefacture(@PathVariable Long id) {
        iServiceClient.removefacture(id);
    }
    @GetMapping("/facture/getall/{idclient}/{idcontrat}")
    public List<Facture> getallfactures(@PathVariable Long idclient,@PathVariable Long idcontrat){
        return iServiceClient.getAllfactures(idclient,idcontrat);
    }

    @GetMapping("/facture/getbyid/{idFacture}")
    public Facture getfacture(@PathVariable Long idFacture){
        return iServiceClient.getfacture(idFacture);
    }
    /****paiement*/
    @PostMapping("/addpaiment/{idcontrat}")
    public Long addpaiment(@RequestBody Paiment p, @PathVariable Long idcontrat) {
        iServiceClient.ajouterPaiment(idcontrat,p);
        return p.getIdpaiment();}
    /*notif*/
    @GetMapping("/facture/notif")
    public List<depassagefacture>  notifdepassage(){
        return iServiceClient.notifdepassage();
    }

    @GetMapping("/contrawet")
    public List<Contract>  listaqribyoufew(){
        return iServiceClient.sendContractExpirationReminders();
    }

    @GetMapping("/lognotif/{id}")
    public List<contratNotif>  lognotification(@PathVariable Long id){
        return iServiceClient.getnotiflog(id);
    }

    @PostMapping("/pickremiender/{id}")
    public ResponseEntity<String>  contratselectemail(@PathVariable Long id){
      iServiceClient.sendContractReminders(id);
        return ResponseEntity.status(HttpStatus.OK).body("Contract reminders sent successfully.");
    }
    @GetMapping("/percentage")
        public List<paymentpercentage> percentage(){
          return  iServiceClient.percentage();
    }
    @GetMapping("/clientsbyprod")
    public List<Client> clientsbyprod(){
        return iServiceClient.getallclientsbyproductowner();
    }
    @GetMapping("/projclient/{id}")
    public List<Project>  projbyidclient(@PathVariable Long id){
        return iServiceClient.getprojbyclient(id);
    }

}
