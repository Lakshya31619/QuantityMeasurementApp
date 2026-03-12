package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.entity.QuantityDTO;

public interface IQuantityMeasurementService {

    boolean compare(QuantityDTO q1, QuantityDTO q2);

    double add(QuantityDTO q1, QuantityDTO q2);

    double convert(QuantityDTO q, String unit);
}