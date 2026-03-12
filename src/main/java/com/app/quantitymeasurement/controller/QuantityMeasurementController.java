package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    public void performComparison(QuantityDTO q1, QuantityDTO q2) {
        boolean result = service.compare(q1, q2);
        System.out.println("Comparison result: " + result);
    }

    public void performAddition(QuantityDTO q1, QuantityDTO q2) {
        double result = service.add(q1, q2);
        System.out.println("Addition result: " + result);
    }

    public void performConversion(QuantityDTO q1, String unit) {
        double result = service.convert(q1, unit);
        System.out.println("Converted result: " + result);
    }
}