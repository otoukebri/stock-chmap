package tn.zelda.projects.spring.stockservice.dao;

import org.junit.Before;
import org.junit.Test;
import tn.zelda.projects.spring.stockservice.dao.entities.StockEntity;
import tn.zelda.projects.spring.stockservice.model.StockModel;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;


public class StockRepositoryTest {

    private StockRepositoryImpl repository;

    @Before
    public void init(){
        repository = new StockRepositoryImpl();
        repository.create(new StockModel("Adidas","AD07B", 100.0));
        repository.create(new StockModel("Nike","NK07R", 120.9));
        repository.create(new StockModel("Puma","PM07R", 130.23));
    }

    @Test
    public void findAllTest() {
        assertThat( "StockRepository findAll Test", repository.findAll().size(), is(3));
    }

    @Test
    public void findTest() {
        assertThat( "StockRepository find exists element Test",repository.find(0L).getId(), is(0L));
        assertNull( "StockRepository find  not exists element Test",repository.find(5L));
    }


    @Test
    public void existsTest() {
        assertThat("StockRepository exists Test",  repository.exists(new StockModel("Nike","NK07R", 120.9)), is(true));
        assertThat("StockRepository not exists Test",  repository.exists(new StockModel("TMious","TM", 120.9)), is(false));
    }

    @Test
    public void createTest() {
        StockEntity stockEntity = repository.create(new StockModel("Puma","PM07R", 130.23));
        assertNotNull("StockRepository create Test, returned StockEntity not null", stockEntity);
        assertThat( "StockRepository create id Test", stockEntity.getId(), is(3L));
        assertThat("StockRepository create Test, size after create greater to before add operation", repository.findAll().size(), is(4));
    }

    @Test
    public void updateTest() {
        StockEntity stockEntity = repository.update(0L, 199.23);
        assertNotNull("StockRepository update Test, returned StockEntity not null", stockEntity);
        assertThat( "StockRepository update name Test", stockEntity.getCurrentPrice(), is(199.23));
    }

}
