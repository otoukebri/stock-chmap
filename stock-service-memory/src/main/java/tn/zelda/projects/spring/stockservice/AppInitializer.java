package tn.zelda.projects.spring.stockservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.zelda.projects.spring.stockservice.dao.StockRepository;
import tn.zelda.projects.spring.stockservice.model.StockModel;

@Service
public class AppInitializer {
    @Autowired
    StockRepository stockRepository;

    public int init() {
        stockRepository.create(new StockModel(1, "A", "AC", 100.0));
        stockRepository.create(new StockModel(2, "B", "BC", 200.0));
        stockRepository.create(new StockModel(3, "C", "CC", 300.0));
        stockRepository.create(new StockModel(4, "D", "DC", 400.0));
        stockRepository.create(new StockModel(4, "D", "EC", 500.0));
        return stockRepository.findAll() != null ?  stockRepository.findAll().size() : 0;
    }

}
