package com.equality.controller;

import com.equality.dto.QuantityDTO;
import com.equality.service.IQuantityMeasurementService;

public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    public void performAddition(QuantityDTO q1, QuantityDTO q2) {
        QuantityDTO result = service.add(q1, q2);
        System.out.println("Addition Result: " + result.getValue() + " " + result.getUnit());
    }

    public void performConversion(QuantityDTO q, String targetUnit) {
        QuantityDTO result = service.convert(q, targetUnit);
        System.out.println("Conversion Result: " + result.getValue() + " " + result.getUnit());
    }

    public void performComparison(QuantityDTO q1, QuantityDTO q2) {
        boolean equal = service.compare(q1, q2);
        System.out.println("Are Equal: " + equal);
    }
}