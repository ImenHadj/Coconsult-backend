package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.IStockService;
import com.bezkoder.springjwt.models.Stock;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/stock")
public class StockController {
    IStockService stockService ;

    // http://localhost:8089/coConsult/stock/retrieve-all-stocks
    @GetMapping("/retrieve-all-stocks")
    @ResponseBody
    public List<Stock> getStocks() {
        List<Stock> stockList = stockService.retrieveAllStocks();
        return stockList;
    }


    @GetMapping("/retrieve-stock/{idStock}")
    @ResponseBody
    public Stock retrieveStock(@PathVariable("idStock") Long idStock) {
        return stockService.retrieveStock(idStock);
    }


    @PostMapping("/add-stock")
    @ResponseBody
    public Stock addStock(@RequestBody Stock s) {

        Date purchaseDate = new Date();
        s.setPurchaseDate(purchaseDate);

        // Generate a random number of days between 5 and 20 to add to purchaseDate for entryDate
        Random random = new Random();
        int randomDays = random.nextInt(16) + 5; // Random number between 5 and 20
        Calendar entryDateCal = Calendar.getInstance();
        entryDateCal.setTime(purchaseDate);
        entryDateCal.add(Calendar.DAY_OF_YEAR, randomDays);
        s.setEntryDate(entryDateCal.getTime());

        // Generate a random number of years between 3 and 10 to add to purchaseDate for expirationDate
        int randomYears = random.nextInt(8) + 5; // Random number between 3 and 10
        Calendar expirationDateCal = Calendar.getInstance();
        expirationDateCal.setTime(purchaseDate);
        expirationDateCal.add(Calendar.YEAR, randomYears);
        s.setExpirationDate(expirationDateCal.getTime());

        // Generate a random number between 0 and 30 for pourcentageDefauts
        int pourcentageDefauts = random.nextInt(31); // Random number between 0 and 30
        s.setPourcentageDefauts(pourcentageDefauts);

        // Generate a random quality from the provided choices
        String[] qualityChoices = {"excellent", "good", "average", "below average", "mediocre"};
        String quality = qualityChoices[random.nextInt(qualityChoices.length)]; // Randomly select one from the array
        s.setQuality(quality);
        s.setReplenishmentAlert(false);
        Stock stock= stockService.addStock(s);
        return stock;
    }


    @PutMapping("/update-stock")
    @ResponseBody
    public Stock updateStock(@RequestBody Stock s) {
        Stock stock= stockService.updateStock(s);
        return stock;
    }

    @DeleteMapping("/removeStock/{idStock}")
    @ResponseBody
    public void removeStock(@PathVariable("idStock") Long idStock) {
        stockService.removeStock(idStock);
    }

    @PutMapping("/affecter-resourceAstock/{stockId}/{resourceId}")
    @ResponseBody
    public Stock assignResourceToStock(@PathVariable("stockId") Long stockId, @PathVariable("resourceId") Long resourceId) {

        Stock stock = stockService.affecterRessourceAStock(resourceId, stockId);
        return stock ;
    }

    @GetMapping("/quality-trend")
    public List<Object[]> getQualityTrend() {
        return stockService.getQualityTrend();
    }

}

