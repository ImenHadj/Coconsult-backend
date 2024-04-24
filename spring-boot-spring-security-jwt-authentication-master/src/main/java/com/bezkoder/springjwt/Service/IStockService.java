package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.Stock;


import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IStockService {

    List<Stock> retrieveAllStocks();
    Stock addStock(Stock s);
    Stock updateStock(Stock s);
    Stock retrieveStock(Long idStock);
    void removeStock(Long idStock);

    public Stock affecterRessourceAStock(Long ressourceId, Long stockId);

    public List<Object[]> getQualityTrend();
}
