package tn.zelda.projects.spring.stockservice.dao;

import org.springframework.stereotype.Repository;
import tn.zelda.projects.spring.stockservice.dao.entities.StockEntity;
import tn.zelda.projects.spring.stockservice.model.StockModel;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class StockRepositoryImpl implements StockRepository {

    private Map<Long, StockEntity> stocks = new ConcurrentHashMap<>(100);
    private AtomicLong index = new AtomicLong();

    public List<StockEntity> findAll() {
        return stocks.values().stream().collect(Collectors.toList());
    }

    public StockEntity find(Long id) {
        return stocks.get(id);
    }

    public StockEntity update(Long id, StockModel stockModel) {
        StockEntity stockEntity = find(id);
        if (stockEntity == null)
            return null;
        else {
            stockEntity.setName(stockModel.getName());
            stockEntity.setCurrentPrice(stockModel.getCurrentPrice());
            stocks.put(id, stockEntity);
            return stockEntity;
        }
    }

    public StockEntity create(StockModel stockModel) {
        long id = index.getAndIncrement();
        StockEntity stock = new StockEntity();
        stock.setId(id);
        stock.setLastUpdate(new Date());
        stock.setName(stockModel.getName());
        stock.setCurrentPrice(stockModel.getCurrentPrice());
        stocks.put(id, stock);
        return stock;
    }

    public Boolean exists(StockModel stockModel) {
        return stocks.containsValue(new StockEntity(stockModel.getName()));
    }
}
