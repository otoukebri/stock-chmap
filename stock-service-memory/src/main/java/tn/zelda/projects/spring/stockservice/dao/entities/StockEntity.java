package tn.zelda.projects.spring.stockservice.dao.entities;


import java.util.Date;
import java.util.Objects;


public class StockEntity implements Cloneable {

    private Long id;

    private String name;

    private String code;

    private Double currentPrice;

    private Date lastUpdate;

    public StockEntity() {

    }

    public StockEntity(String name) {
        this.name = name;
    }


    public StockEntity(Long id, String name, String code, Double currentPrice, Date lastUpdate) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.currentPrice = currentPrice;
        this.lastUpdate = lastUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
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
        if (!(o instanceof StockEntity)) return false;
        StockEntity stockEntity = (StockEntity) o;
        return  getCode().equals( stockEntity.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }

    @Override
    public StockEntity clone() {
        return new StockEntity(this.id, this.name,this.code, this.currentPrice, this.lastUpdate);
    }


}
