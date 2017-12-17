package tn.zelda.projects.spring.stock.service.business;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.zelda.projects.spring.stockservice.business.StockBusinessImpl;
import tn.zelda.projects.spring.stockservice.dao.StockRepositoryImpl;
import tn.zelda.projects.spring.stockservice.dao.entities.StockEntity;
import tn.zelda.projects.spring.stockservice.model.StockModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class StockServiceTest {

    @Mock
    private StockRepositoryImpl stockRepository;

    @InjectMocks
    private StockBusinessImpl stockBusiness;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllStocksTest(){
        when(stockRepository.findAll()).thenReturn(new ArrayList<>());
        assertThat( "StockService getAllStocks empty list Test",   stockBusiness.getAllStocks().size(), is(0));
        List<StockEntity> stocks = Arrays.asList(
                new StockEntity(1L, "Adidas","AD07B", 100.0 , new Date()),
                new StockEntity(2L, "Nike","NK07R", 100.0, new Date()),
                new StockEntity(3L, "Puma","PM07R", 130.23, new Date()));
        when(stockRepository.findAll()).thenReturn(stocks);
        assertThat( "StockService getAllStocks not empty list Test",   stockBusiness.getAllStocks().size(), is(3));
    }

    @Test
    public void getStockTest() {
        when(stockRepository.find(0L)).thenReturn(new StockEntity(0L,"AD07B", "Adidas", 100.0 , new Date()));
        assertThat( "StockService find Test",   stockBusiness.getStock(0).getId(), is(0L));
        when(stockRepository.find(0L)).thenReturn(null);
        assertNull( "StockService find  not exists element Test", stockBusiness.getStock(5L));
    }

    @Test
    public void exitsStockTest() {
        when(stockRepository.exists(new StockModel("Adidas","AD07B", 100.0))).thenReturn(Boolean.TRUE);
        assertThat( "StockService exits Test",
                stockBusiness.exist(new StockModel("Adidas","AD07B", 100.0)), is(Boolean.TRUE));
    }

    @Test
    public void saveStockTest() {
        when(stockRepository.create(new StockModel("Adidas","AD07B", 100.0)))
                .thenReturn(new StockEntity(0L, "Adidas","AD07B", 100.0 , new Date()));
        assertThat( "StockService saveStock Test", stockBusiness.saveStock(new StockModel("Adidas","AD07B", 100.0)).getName(), is("Adidas"));
    }

//    @Test
//    public void updateStock(){
//        when(stockRepository.find(0L))
//                .thenReturn(new StockEntity(0L,"Adidas","AD07B", 100.0, new Date()));
//        when(stockRepository.update(0L, 120.0))
//                .thenReturn(new StockEntity(0L, "Adidas","AD07B", 120.0 , new Date()));
//        assertThat( "StockService updateStock Test", stockBusiness.updateStock( 0L, 120.0).getCurrentPrice(), is(120.0));
//    }

}
