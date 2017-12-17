package tn.zelda.projects.spring.stockservice.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.zelda.projects.spring.stockservice.dao.StockRepository;
import tn.zelda.projects.spring.stockservice.dao.entities.StockEntity;
import tn.zelda.projects.spring.stockservice.model.StockModel;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockBusinessImpl implements StockBusiness {

    private static final Logger logger = LoggerFactory.getLogger(StockBusinessImpl.class);

    @Autowired
    private StockRepository stockRepository;


    public List<StockModel> getAllStocks() {
        List<StockEntity> p = stockRepository.findAll();
        if (p != null && p.size() > 0)
            return StockFactory.toStockModelList(p);
        else
            return new ArrayList<>();
    }

    public StockModel getStock(long id) {
        return StockFactory.toStockModel(stockRepository.find(id));
    }

    public StockModel updateStock(Long id, Double newPrice) {
        return StockFactory.toStockModel(stockRepository.update(id, newPrice));
    }

    public StockModel saveStock(StockModel newStockModel) {
        return StockFactory.toStockModel(stockRepository.create(newStockModel));
    }

    public Boolean exist(StockModel newStockModel) {
        return stockRepository.exists(newStockModel);
    }


}
