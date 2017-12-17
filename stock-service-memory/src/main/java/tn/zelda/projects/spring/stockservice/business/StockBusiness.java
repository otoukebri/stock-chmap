package tn.zelda.projects.spring.stockservice.business;

import tn.zelda.projects.spring.stockservice.model.StockModel;

import java.util.List;

public interface StockBusiness {

    public List<StockModel> getAllStocks();

    public StockModel getStock(long id);

    public StockModel updateStock(Long id, Double newPrice);

    public StockModel saveStock(StockModel newStockModel);

    public Boolean exist(StockModel newStockModel);

}
