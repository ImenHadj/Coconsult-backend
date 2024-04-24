package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.CommandeRepository;
import com.bezkoder.springjwt.repository.FournisseurRepository;
import com.bezkoder.springjwt.repository.ResoucesRepository;
import com.bezkoder.springjwt.repository.StockRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class StockService implements IStockService {
    StockRepository stockRepository ;
    ResoucesRepository resoucesRepository ;
    CommandeService commandeService ;
    FournisseurRepository fournisseurRepository ;
    CommandeRepository commandeRepository ;

    @Override
    public List<Stock> retrieveAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public Stock addStock(Stock s) {

        return stockRepository.save(s);
    }

    @Override
    public Stock updateStock(Stock s) {
        return stockRepository.save(s);
    }

    @Override
    public Stock retrieveStock(Long idStock) {
        return stockRepository.findById(idStock).orElse(null);
    }

    @Override
    public void removeStock(Long idStock) {
        log.debug("debugging");
        stockRepository.deleteById(idStock);
    }

    public Stock affecterRessourceAStock(Long ressourceId, Long stockId) {

        Resources resource = resoucesRepository.findById(ressourceId).orElse(null);
        Stock stock = stockRepository.findById(stockId).orElse(null);

        if (resource == null || stock == null) {
            throw new EntityNotFoundException("Could not find resource or stock");
        }

        if (resource.getStock() != null) {
            throw new IllegalStateException("Resource is already associated with another stock");
        }

        if (stock.getResource() != null) {
            throw new IllegalStateException("Stock is already associated with another resource");
        }

        if (resource.getCategorie() != stock.getCategorieStock()) {
            throw new IllegalArgumentException("Resource category does not match stock category");
        }
        
        stock.setResource(resource);
        stockRepository.save(stock);
        return stock;
    }


    @Scheduled(fixedRate =360000)
    public void checkStockAndGenerateCommands() {
        log.info("Scheduled task: Checking stock and generating commands");
        checkStockAndSetReplenishmentAlert();
        generateCommandsForLowStocksWithReplenishmentAlert();
    }

    public void checkStockAndSetReplenishmentAlert() {
        List<Stock> stocks = stockRepository.findStockByQuantityLessThan(5);
        for (Stock stock : stocks) {
            stock.setReplenishmentAlert(true);
            stockRepository.save(stock);
        }
    }

    public void generateCommandsForLowStocksWithReplenishmentAlert() {
        log.info("Generating commands for low stocks with replenishment alert");
        List<Stock> stocksWithReplenishmentAlert = stockRepository.findStockByReplenishmentAlert();
        log.info("Number of stocks with replenishment alert: {}", stocksWithReplenishmentAlert.size());
        for (Stock stock : stocksWithReplenishmentAlert) {
            log.info("Processing stock: {}", stock.getStockID());
            int quantityToAdd = determineQuantityToAdd(stock);
            log.info("Quantity to add for stock {}: {}", stock.getStockID(), quantityToAdd);
            Long fournisseurId = selectSupplierForCategory(stock.getCategorieStock());
            log.info("Selected supplier ID for stock {}: {}", stock.getStockID(), fournisseurId);
            if (fournisseurId != null) {
                Commande commande = createCommandeFromStock(stock, quantityToAdd, fournisseurId);
                commandeService.addCommande(commande);
                stock.setReplenishmentAlert(false);
                stockRepository.save(stock);
            } else {
                log.warn("No supplier found for stock: {}", stock.getStockID());
            }
        }
    }


    private int determineQuantityToAdd(Stock stock) {


        ResourcesCategorie categorie = stock.getCategorieStock();


        int quantityToAdd = 0;
        switch (categorie) {
            case FURNITURE:

                 if (stock.getResource().getName().equals("chair")) {
                    quantityToAdd = 30;
                } else if (stock.getResource().getName().equals("table")) {
                     quantityToAdd = 20;
                 }else if (stock.getResource().getName().equals("board")) {
                     quantityToAdd = 20;
                 }else if (stock.getResource().getName().equals("laptop")) {
                     quantityToAdd = 20;
                 }
                break;
            case ELECTRONICS:
                if (stock.getResource().getName().equals("mouse")) {
                    quantityToAdd = 40;
                } else if (stock.getResource().getName().equals("laptop")) {
                    quantityToAdd = 40;
                } else if (stock.getResource().getName().equals("screen")) {
                    quantityToAdd = 12;
                }else if (stock.getResource().getName().equals("tablet")) {
                    quantityToAdd = 10;
                }else if (stock.getResource().getName().equals("tv")) {
                    quantityToAdd = 15;
                }else if (stock.getResource().getName().equals("keyboards")) {
                    quantityToAdd = 30;
                }
                break;
            case VEHICULES:
                quantityToAdd = 3;
                break;
            default:
                quantityToAdd = 10;
                break;
        }

        return quantityToAdd;
    }

    private Long selectSupplierForCategory(ResourcesCategorie categorie) {

        List<Fournisseur> fournisseurs = fournisseurRepository.findFournisseurByTypeFournisseurOrderByScoreDesc(categorie);
        if (!fournisseurs.isEmpty()) {
            return fournisseurs.get(0).getFournisseurID();
        }
        else {
            return null;
        }
    }

    private Commande createCommandeFromStock(Stock stock, int quantity, Long fournisseurId) {
        log.info("Creating command from stock - Stock ID: {}, Quantity to add: {}, Fournisseur ID: {}", stock.getStockID(), quantity, fournisseurId);
        Commande commande = Commande.builder()
                .quantity(quantity)
                .location(stock.getLocation())
                .replenishmentAlert(stock.getReplenishmentAlert())
                .entryDate(stock.getEntryDate())
                .purchaseDate(stock.getPurchaseDate())
                .expirationDate(stock.getExpirationDate())
                .quality(stock.getQuality())
                .pourcentageDefauts(stock.getPourcentageDefauts())
                .categorieStock(stock.getCategorieStock())
                .fournissID(fournisseurId)
                .statusCommande(StatusCommande.PENDING)
                .build();
        log.info("Command created: {}", commande);
        return commande ;
    }

    @Override
    public List<Object[]> getQualityTrend() {
        Date startDate = stockRepository.findOldestEntryDate();
        Date endDate = stockRepository.findLatestEntryDate();
        return stockRepository.findQualityTrendByDateRangeOrderByDate(startDate, endDate);
    }
}


