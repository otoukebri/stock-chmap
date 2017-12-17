package tn.zelda.projects.spring.stockservice.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tn.zelda.projects.spring.stockservice.business.StockBusinessImpl;
import tn.zelda.projects.spring.stockservice.model.StockModel;
import tn.zelda.projects.spring.stockservice.validator.StockModelValidator;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(value = StockController.class)
public class StockControllerTest {


    @MockBean
    private StockBusinessImpl stockBusiness;

    @MockBean
    private StockModelValidator stockModelValidator;


    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllStocksOkTest() throws Exception {
        List<StockModel> stocks = Arrays.asList(
                new StockModel(1L, "Adidas","AD07B", 100.0),
                new StockModel(2L, "Nike","NK07R", 100.0),
                new StockModel(3L, "Puma","PM07R", 130.23));

        when(stockBusiness.getAllStocks()).thenReturn(stocks);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/stocks")
                .accept(MediaType.APPLICATION_JSON_UTF8);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON_UTF8)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", is(3)));
    }

    @Test
    public void getStockOkTest() throws Exception {
        when(stockBusiness.getStock(1L)).thenReturn(new StockModel(1L, "Adidas","AD07B", 100.0));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/stocks/1")
                .accept(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON_UTF8)))
                .andExpect(jsonPath("$.name", is("Adidas")))
                .andExpect(jsonPath("$.currentPrice", is(100.0)));

    }

    @Test
    public void getStockNotFoundTest() throws Exception {
        when(stockBusiness.getStock(1L)).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/stocks/1000")
                .accept(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON_UTF8)));

    }


    @Test
    public void createStockOkTest() throws Exception {
        when(stockBusiness.exist(new StockModel("Reebok","RB07B", 120.0)))
                .thenReturn(Boolean.FALSE);


        when(stockBusiness.saveStock(new StockModel("Reebok","RB07B", 120.0)))
                .thenReturn(new StockModel(4L, "Reebok","RB07B", 120.0));



        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/stocks")
                .content("{\"name\": \"Reebok\",\"code\": \"RB07B\", \"currentPrice\": 120.0}")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.name", is("Reebok")))
                .andExpect(content().contentType((MediaType.APPLICATION_JSON_UTF8)));

    }

    @Test
    public void createStockKoTest() throws Exception {
        when(stockBusiness.exist(new StockModel("Reebok","RB07B", 120.0)))
                .thenReturn(Boolean.FALSE);


        when(stockBusiness.saveStock(new StockModel("Reebok","RB07B", 120.0)))
                .thenReturn(null);



        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/stocks")
                .content("{\"name\": \"Reebok\",\"code\": \"RB07B\", \"currentPrice\": 120.0}")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON_UTF8)));

    }

    @Test
    public void createStockAlreadyExistsTest() throws Exception {
        when(stockBusiness.exist(new StockModel("Reebok","RB07B", 120.0)))
                .thenReturn(Boolean.TRUE);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/stocks")
                .content("{\"name\": \"Reebok\",\"code\": \"RB07B\", \"currentPrice\": 120.0}")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isConflict())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON_UTF8)));

    }

    @Test
    public void updateStockOkTest() throws Exception {
        when(stockBusiness.updateStock(0L, 120.0))
                .thenReturn(new StockModel("X1", "X1", 120.0));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/stocks/0/120.0")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON_UTF8)));

    }

    @Test
    public void updateStockNotFoundTest() throws Exception {
        when(stockBusiness.exist(new StockModel("X1", "X1", 120.0)))
                .thenReturn(Boolean.FALSE);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/stocks/1000/100")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON_UTF8)));

    }

}
