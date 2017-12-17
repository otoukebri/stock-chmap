package tn.zelda.projects.spring.stockservice.model;


import java.util.Objects;

/**
 * Dto class used to transfer user input
 * through layers
 */
public class StockModel {

    private long id;

    private String code;

    private String name;

    private Double currentPrice;

    public StockModel() {

    }

    public StockModel(long id, String name, String code, Double currentPrice) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.currentPrice = currentPrice;

    }

    public StockModel(String name, String code, Double currentPrice) {
        this.name = name;
        this.code = code;
        this.currentPrice = currentPrice;
    }

    public String getName() {
        return name;
    }


    public Double getCurrentPrice() {
        return currentPrice;
    }


    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockModel)) return false;
        StockModel stockModel = (StockModel) o;
        return getCode().equalsIgnoreCase(stockModel.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }

}
