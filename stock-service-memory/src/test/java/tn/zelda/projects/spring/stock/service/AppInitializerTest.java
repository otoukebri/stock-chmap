package tn.zelda.projects.spring.stock.service;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.zelda.projects.spring.stockservice.AppInitializer;
import tn.zelda.projects.spring.stockservice.dao.StockRepositoryImpl;
import tn.zelda.projects.spring.stockservice.dao.entities.StockEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


public class AppInitializerTest {

    @Mock
    private StockRepositoryImpl stockRepository;

    @InjectMocks
    private AppInitializer appInitializer;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public  void initTest(){
        List<StockEntity> stocks = Arrays.asList(
                new StockEntity(1L, "Adidas","AD07B", 100.0 , new Date()),
                new StockEntity(2L, "Nike","NK07R", 100.0, new Date()),
                new StockEntity(3L, "Puma","PM07R", 130.23, new Date()));
        when(stockRepository.findAll())
                .thenReturn(stocks);

        assertThat( "StockService saveStock Test", appInitializer.init(), is(3));
    }
}
