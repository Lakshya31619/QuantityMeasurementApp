package com.app.quantitymeasurement.entity;

public class QuantityDTO {

    private Double value;
    private String unit;

    public QuantityDTO(Double value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    public Double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }
}