package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {
        return q1.getValue().equals(q2.getValue());
    }

    @Override
    public double add(QuantityDTO q1, QuantityDTO q2) {
        return q1.getValue() + q2.getValue();
    }

    @Override
    public double convert(QuantityDTO q, String unit) {
        if (q.getUnit().equals("FEET") && unit.equals("INCH")) {
            return q.getValue() * 12;
        }
        return q.getValue();
    }
}