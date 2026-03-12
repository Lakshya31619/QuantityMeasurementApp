package com.equality.service;

import com.equality.dto.QuantityDTO;

public interface IQuantityMeasurementService {

    QuantityDTO convert(QuantityDTO source, String targetUnit);

    QuantityDTO add(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO divide(QuantityDTO q1, QuantityDTO q2);

    boolean compare(QuantityDTO q1, QuantityDTO q2);
}