package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.Commande;
import com.bezkoder.springjwt.models.Fournisseur;
import com.bezkoder.springjwt.models.StatusCommande;
import com.bezkoder.springjwt.models.Stock;
import com.bezkoder.springjwt.repository.CommandeRepository;
import com.bezkoder.springjwt.repository.FournisseurRepository;
import com.bezkoder.springjwt.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@AllArgsConstructor
public class CommandeService implements ICommandeService {

    StockRepository stockRepository ;
    CommandeRepository commandeRepository;
    FournisseurRepository fournisseurRepository;

    @Override
    public List<Commande> retrieveAllCommandes() {
        return commandeRepository.findAll();
    }

    @Override
    public Commande addCommande(Commande s) {
        s.setStatusCommande(StatusCommande.PENDING);
        return commandeRepository.save(s);
    }

    @Override
    public Commande updateCommande(Commande s) {
        return commandeRepository.save(s);
    }

    @Override
    public Commande retrieveCommande(Long idCom) {
        return commandeRepository.findById(idCom).orElse(null);
    }

    @Override
    public void removeCommande(Long idCom) {
        log.debug("debugging");
        commandeRepository.deleteById(idCom);
    }


    public void marquerCommandeCommeArrivee(Long commandeId) {
        Commande commande = commandeRepository.findById(commandeId).orElse(null);
        Fournisseur fournisseur = fournisseurRepository.findById(commande.getFournissID()).get();
        if (commande != null) {

            commande.setStatusCommande(StatusCommande.valueOf("ARRIVED"));
            commandeRepository.save(commande);


            Stock stock = new Stock();

            stock.setQuantity(commande.getQuantity());
            stock.setLocation(commande.getLocation());
            stock.setReplenishmentAlert(false);
            stock.setCategorieStock(commande.getCategorieStock());

            Date purchaseDate = new Date();
            stock.setPurchaseDate(purchaseDate);


            Random random = new Random();
            int randomDays = random.nextInt(16) + 5;
            Calendar entryDateCal = Calendar.getInstance();
            entryDateCal.setTime(purchaseDate);
            entryDateCal.add(Calendar.DAY_OF_YEAR, randomDays);
            stock.setEntryDate(entryDateCal.getTime());


            int randomYears = random.nextInt(8) + 5;
            Calendar expirationDateCal = Calendar.getInstance();
            expirationDateCal.setTime(purchaseDate);
            expirationDateCal.add(Calendar.YEAR, randomYears);
            stock.setExpirationDate(expirationDateCal.getTime());


            int pourcentageDefauts = random.nextInt(31);
            stock.setPourcentageDefauts(pourcentageDefauts);


            String[] qualityChoices = {"excellent", "good", "average", "below average", "mediocre"};
            String quality = qualityChoices[random.nextInt(qualityChoices.length)];
            stock.setQuality(quality);


            commande.setStockC(stock);
            stock.setFournisseur(fournisseur);


            stockRepository.save(stock);



        }
    }

}
