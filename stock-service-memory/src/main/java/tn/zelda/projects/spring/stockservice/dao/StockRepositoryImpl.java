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

/**
 *  Repository class that simulates in memory database
 *  using ConcurrentHashMap with default DEFAULT_CONCURRENCY_LEVEL=16
 *  also clone object used for immutability
 */

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

    public StockEntity update(Long id, Double newPrice) {
        StockEntity stockEntity = find(id);
        if (stockEntity == null)
            return null;
        else {
            StockEntity stockEntityClone = stockEntity.clone();
            stockEntityClone.setCurrentPrice(newPrice);
            stockEntityClone.setLastUpdate(new Date());
            stocks.replace(id, stockEntityClone);
            return stockEntityClone.clone();
        }
    }

    public StockEntity create(StockModel stockModel) {
        long id = index.getAndIncrement();
        StockEntity stock = new StockEntity(id, stockModel.getName(), stockModel.getCode(), stockModel.getCurrentPrice(), new Date());
        stocks.putIfAbsent(id, stock);
        return stock.clone();
    }

    public Boolean exists(StockModel stockModel) {
        return stocks.containsValue(new StockEntity(null, null, stockModel.getCode(), null, null));
    }
}
